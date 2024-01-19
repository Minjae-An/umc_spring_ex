package umc.spring.service.store_service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.StoreHandler;
import umc.spring.aws.AmazonS3Manager;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.ReviewImage;
import umc.spring.domain.Store;
import umc.spring.domain.common.Uuid;
import umc.spring.repository.StoreRepository;
import umc.spring.repository.UuidRepository;
import umc.spring.service.member_service.MemberQueryService;
import umc.spring.service.mission_service.MissionCommandService;
import umc.spring.service.review_service.ReviewCommandService;
import umc.spring.service.review_service.ReviewQueryService;
import umc.spring.web.dto.store.StoreRequestDTO.AddMissionRequestDTO;
import umc.spring.web.dto.store.StoreRequestDTO.AddReviewRequestDTO;
import umc.spring.web.dto.store.StoreRequestDTO.ReviewDTO;
import umc.spring.web.dto.store.StoreResponseDTO.DeleteReviewDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreCommandServiceImpl implements StoreCommandService {
    private final StoreRepository storeRepository;
    private final UuidRepository uuidRepository;
    private final MissionCommandService missionCommandService;
    private final MemberQueryService memberQueryService;
    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;
    private final AmazonS3Manager s3Manager;

    @Override
    public Store save(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Mission addMission(AddMissionRequestDTO request, Long storeId) {
        Mission mission = MissionConverter.toMission(request);
        Store store = findById(storeId);
        mission.store(store);
        return missionCommandService.save(mission);
    }

    private Store findById(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreHandler(ErrorStatus.STORE_NOT_FOUND));
    }

    /*
        본래는 S3에 이미지를 업로드하고 접근 URL을 받아와 DB에 저장하는 로직이 포함되어야 하나
        하드 코딩된 데이터로 대체하였다.
     */
    @Override
    public Review addReview(AddReviewRequestDTO request, Long storeId) {
        Review review = ReviewConverter.toReview(request);
        Store store = findById(storeId);
        review.store(store);

        Member member = memberQueryService.findById(request.getMemberId());
        review.member(member);

        List<String> exampleImgUrls = List.of("exampleImgUrl1", "exampleImgUrl2", "exampleImgUrl3");
        List<ReviewImage> reviewImages = ReviewConverter.toReviewImages(review, exampleImgUrls);
        return reviewCommandService.save(review, reviewImages);
    }

    @Override
    public Review createReview(Long memberId, Long storeId, ReviewDTO request) {
        Member member = memberQueryService.findById(memberId);
        Store store = findById(storeId);
        Review review = StoreConverter.toReview(request, member, store);

        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());

        String pictureUrl = s3Manager.uploadFile(s3Manager.generateReviewKeyName(savedUuid),
                request.getReviewPicture());
        List<ReviewImage> reviewImages = List.of(ReviewConverter.toReviewImage(review, pictureUrl));
        return reviewCommandService.save(review, reviewImages);
    }

    @Override
    public DeleteReviewDTO deleteReview(Long reviewId) {
        Review review = reviewQueryService.findById(reviewId);
        review.getReviewImages().stream()
                .map(ReviewImage::getImageUrl)
                .forEach(s3Manager::deleteFile);
        reviewCommandService.deleteReview(reviewId);
        return ReviewConverter.toDeleteReviewDTO(review);
    }
}
