package com.tuananhdo.dto;

import com.tuananhdo.entity.Post;
import com.tuananhdo.entity.User;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeDTO {

    private Long id;
    private Post post;
    private User user;
    private boolean isLike;
}
