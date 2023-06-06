package com.tuananhdo.service;

import com.tuananhdo.entity.CommentLike;
import com.tuananhdo.entity.User;
import com.tuananhdo.util.VoteResult;
import com.tuananhdo.util.VoteType;

public interface CommentLikeService {

    void getCommentById(Long id);

    User getCurrentUserByCommentLike();

    VoteResult doVote(Long commentId, User user, VoteType voteType);

    VoteResult undoVote(CommentLike vote, Long commentId, VoteType voteType);
}
