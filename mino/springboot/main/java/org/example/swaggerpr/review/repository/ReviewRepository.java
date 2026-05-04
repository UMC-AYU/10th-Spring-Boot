package org.example.swaggerpr.review.repository;

import org.example.swaggerpr.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
