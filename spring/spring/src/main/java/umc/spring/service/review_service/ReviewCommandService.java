package umc.spring.service.review_service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.ReviewHandler;
import umc.spring.domain.Review;
import umc.spring.domain.ReviewImage;
import umc.spring.repository.ReviewImageRepository;
import umc.spring.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;

    public Review save(Review review, List<ReviewImage> reviewImages){
        saveAll(review, reviewImages);
        return reviewRepository.save(review);
    }

    private void saveAll(Review review, List<ReviewImage> reviewImages){
        reviewImages.forEach(review::addReviewImage);
        reviewImageRepository.saveAll(reviewImages);
    }

    public void deleteReview(Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewHandler(ErrorStatus.REVIEW_NOT_FOUND));
        reviewImageRepository.deleteAll(review.getReviewImages());
        reviewRepository.delete(review);
    }
}
