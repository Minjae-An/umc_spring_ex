package umc.spring.converter;

import static umc.spring.web.dto.store.StoreRequestDTO.AddReviewRequestDTO;
import static umc.spring.web.dto.store.StoreResponseDTO.AddReviewResponseDTO;

import java.util.List;
import java.util.stream.Collectors;
import umc.spring.domain.Review;
import umc.spring.domain.ReviewImage;

public class ReviewConverter {
    public static Review toReview(AddReviewRequestDTO request) {
        return Review.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .score(request.getScore())
                .build();
    }

    public static ReviewImage toReviewImage(Review review, String imageUrl){
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
}
