package com.tuananhdo.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comment_likes")
public class CommentLike {

    private static final int VOTE_UP_POINT = 1;
    private static final int VOTE_DOWN_POINT = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private int votes;

    public void voteUp() {
        this.votes = VOTE_UP_POINT;
    }

    public void voteDown() {
        this.votes = VOTE_DOWN_POINT;
    }

    @Transient
    public boolean isVoteUp() {
        return this.votes == VOTE_UP_POINT;
    }

    @Transient
    public boolean isVoteDown() {
        return this.votes == VOTE_DOWN_POINT;
    }
}
