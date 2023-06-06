package com.tuananhdo.controller;

import com.tuananhdo.entity.User;
import com.tuananhdo.service.CommentLikeService;
import com.tuananhdo.util.VoteResult;
import com.tuananhdo.util.VoteType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentLikeVoteRestController {

    private final CommentLikeService commentLikeService;

    public CommentLikeVoteRestController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @PostMapping("/comment/{commentId}/{type}")
    public VoteResult voteComment(@PathVariable(name = "commentId") Long commentId,
                                  @PathVariable(name = "type") String type) {
        User user = commentLikeService.getCurrentUserByCommentLike();
        if (user == null) {
            return VoteResult.fail("You must login to vote the comment");
        }
        VoteType voteType = VoteType.valueOf(type.toUpperCase());
        return commentLikeService.doVote(commentId, user, voteType);
    }

}
