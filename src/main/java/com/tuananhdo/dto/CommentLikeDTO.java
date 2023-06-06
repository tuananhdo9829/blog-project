package com.tuananhdo.dto;

import com.tuananhdo.entity.Comment;
import com.tuananhdo.entity.User;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeDTO {

    private Long id;
    private Comment comment;
    private User user;
    private int votes;
}
