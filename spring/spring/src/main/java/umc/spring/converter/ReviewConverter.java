package umc.spring.converter;

import static umc.spring.web.dto.store.StoreRequestDTO.AddReviewRequestDTO;
import static umc.spring.web.dto.store.StoreResponseDTO.AddReviewResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import umc.spring.domain.Review;
import umc.spring.domain.ReviewImage;
import umc.spring.web.dto.store.StoreResponseDTO.DeleteReviewDTO;
import umc.spring.web.dto.store.StoreResponseDTO.ReviewDetailDTO;

public class ReviewConverter {
    public static Review toReview(AddReviewRequestDTO request) {
        return Review.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .score(request.getScore())
                .build();
    }

    public static ReviewImage toReviewImage(Review review, String imageUrl) {
        return ReviewImage.builder()
                .imageUrl(imageUrl)
                .review(review)
                .build();
    }

    public static List<ReviewImage> toReviewImages(Review review, List<String> imgUrls) {
        return imgUrls.stream()
                .map(imgUrl -> ReviewImage.builder()
                        .imageUrl(imgUrl)
                        .review(review)
                        .build())
                .collect(Collectors.toList());
    }

    public static AddReviewResponseDTO toAddReviewResponseDTO(Review review) {
        return AddReviewResponseDTO.builder()
                .storeId(review.getStore().getId())
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewDetailDTO toReviewDetailDTO(Review review) {
        List<String> reviewImageUrls = review.getReviewImages().stream()
                .map(ReviewImage::getImageUrl)
                .toList();

        return ReviewDetailDTO.builder()
                .reviewId(review.getId())
                .wroteMemberId(review.getMember().getId())
                .title(review.getTitle())
                .reviewImageUrls(reviewImageUrls)
                .body(review.getBody())
                .score(review.getScore())
                .build();
    }

    public static DeleteReviewDTO toDeleteReviewDTO(Review review) {
        return DeleteReviewDTO.builder()
                .storeId(review.getStore().getId())
                .deletedAt(LocalDateTime.now())
                .build();
    }
}
