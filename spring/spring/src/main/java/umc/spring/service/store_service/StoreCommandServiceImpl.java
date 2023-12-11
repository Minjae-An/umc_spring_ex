package umc.spring.service.store_service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.MemberHandler;
import umc.spring.apiPayload.exception.handler.StoreHandler;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.ReviewImage;
import umc.spring.domain.Store;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.MissionRepository;
import umc.spring.repository.ReviewImageRepository;
import umc.spring.repository.ReviewRepository;
import umc.spring.repository.StoreRepository;
import umc.spring.web.dto.store.StoreRequestDTO.AddMissionRequestDTO;
import umc.spring.web.dto.store.StoreRequestDTO.AddReviewRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreCommandServiceImpl implements StoreCommandService {
    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;


    @Override
    public Mission addMission(AddMissionRequestDTO request, Long storeId) {
        Mission mission = MissionConverter.toMission(request);
        Store store = findById(storeId);
        mission.store(store);
        return missionRepository.save(mission);
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

        Member member = findMemberById(request.getMemberId());
        review.member(member);

        List<String> exampleImgUrls = List.of("exampleImgUrl1", "exampleImgUrl2", "exampleImgUrl3");
        List<ReviewImage> reviewImages = ReviewConverter.toReviewImages(review, exampleImgUrls);
        reviewImages.forEach(review::addReviewImage);
        reviewImageRepository.saveAll(reviewImages);

        return reviewRepository.save(review);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }


}
