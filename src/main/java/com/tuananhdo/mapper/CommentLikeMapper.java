package com.tuananhdo.mapper;

import com.tuananhdo.dto.CommentLikeDTO;
import com.tuananhdo.entity.CommentLike;

public class CommentLikeMapper {

    public static CommentLikeDTO mapTopCommentLikeDTO(CommentLike commentLike) {
        return CommentLikeDTO.builder()
                .comment(commentLike.getComment())
                .user(commentLike.getUser())
                .votes(commentLike.getVotes())
                .build();
    }


    public static CommentLike mapToPostLike(CommentLikeDTO commentLikeDTO) {
        return CommentLike.builder()
                .comment(commentLikeDTO.getComment())
                .user(commentLikeDTO.getUser())
                .votes(commentLikeDTO.getVotes())
                .build();
    }
}
