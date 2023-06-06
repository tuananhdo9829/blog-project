package com.tuananhdo.mapper;

import com.tuananhdo.dto.CommentDTO;
import com.tuananhdo.entity.Comment;

import java.util.stream.Collectors;

public class CommentMapper {

    //convert comment entity to comment dto
    public static CommentDTO mapToCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .content(comment.getContent())
                .votes(comment.getVotes())
                .createdOn(comment.getCreatedOn())
                .updatedOn(comment.getUpdatedOn())
                .comments(comment.getCommentLikes()
                        .stream().map(CommentLikeMapper::mapTopCommentLikeDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    //convert commentDTO to comment Entity
    public static Comment mapToComment(CommentDTO commentDTO) {
        return Comment.builder()
                .id(commentDTO.getId())
                .name(commentDTO.getName())
                .email(commentDTO.getEmail())
                .content(commentDTO.getContent())
                .votes(commentDTO.getVotes())
                .createdOn(commentDTO.getCreatedOn())
                .updatedOn(commentDTO.getUpdatedOn())
                .build();
    }
}
