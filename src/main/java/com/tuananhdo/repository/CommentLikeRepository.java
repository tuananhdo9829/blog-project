package com.tuananhdo.repository;

import com.tuananhdo.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    @Query("SELECT c FROM CommentLike c WHERE c.comment.id = ?1 AND c.user.id= ?2")
    CommentLike findByCommentAndUser(Long commentId, Long userId);
}
