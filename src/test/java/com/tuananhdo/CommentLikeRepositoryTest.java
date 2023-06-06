package com.tuananhdo;

import com.tuananhdo.entity.Comment;
import com.tuananhdo.entity.CommentLike;
import com.tuananhdo.entity.User;
import com.tuananhdo.repository.CommentLikeRepository;
import com.tuananhdo.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CommentLikeRepositoryTest {

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testSaveVotes() {
        User user = new User();
        user.setId(2L);

        Comment comment = new Comment();
        comment.setId(3L);

        CommentLike vote = new CommentLike();
        vote.setComment(comment);
        vote.setUser(user);
        vote.voteUp();

        CommentLike save = commentLikeRepository.save(vote);
        assertThat(save.getId()).isGreaterThan(0);
    }


    @Test
    public void testFindByCommentAndUser() {
        Long userId = 2L;
        Long commentId = 3L;

        CommentLike vote = commentLikeRepository.findByCommentAndUser(commentId, userId);
        assertThat(vote).isNotNull();
        System.out.println(vote);

    }

    @Test
    public void testUpdateVoteCount() {
        Long commentId = 3L;
        commentRepository.updateVoteCountByComment(commentId);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        assertThat(comment.getVotes()).isEqualTo(1);
    }

    @Test
    public void testGetVoteCount(){
        Long commentId = 3L;
        Long voteCount = commentRepository.getVoteCountByComment(commentId);
        assertThat(voteCount).isEqualTo(1);
    }

}
