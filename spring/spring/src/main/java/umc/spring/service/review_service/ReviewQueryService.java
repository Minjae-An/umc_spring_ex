package umc.spring.service.review_service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import umc.spring.domain.Member;
import umc.spring.domain.Review;

public interface ReviewQueryService {
    Review findById(Long reviewId);
    Page<Review> findAllByMember(Member member , PageRequest pageRequest);
}
