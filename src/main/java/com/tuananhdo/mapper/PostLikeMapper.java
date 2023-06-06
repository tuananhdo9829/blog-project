package com.tuananhdo.mapper;

import com.tuananhdo.dto.PostLikeDTO;
import com.tuananhdo.entity.PostLike;

public class PostLikeMapper {


    public static PostLikeDTO mapTopPostLikeDTO(PostLike postLike) {
        return PostLikeDTO.builder()
                .post(postLike.getPost())
                .user(postLike.getUser())
                .isLike(postLike.isLike())
                .build();
    }


    public static PostLike mapToPostLike(PostLikeDTO postLikeDTO) {
        return PostLike.builder()
                .post(postLikeDTO.getPost())
                .user(postLikeDTO.getUser())
                .isLike(postLikeDTO.isLike())
                .build();
    }
}
