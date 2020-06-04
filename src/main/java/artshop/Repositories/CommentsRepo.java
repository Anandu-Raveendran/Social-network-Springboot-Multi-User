package artshop.Repositories;

import artshop.Entities.Comments;
import artshop.Entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepo extends JpaRepository<Comments,String> {
    List<Comments> findByPostId(String post_id);
    List<Comments> findByPostIdAndUserId(String post_id, String user_id);
}

