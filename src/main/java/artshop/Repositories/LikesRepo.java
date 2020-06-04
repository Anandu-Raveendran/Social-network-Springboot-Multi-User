package artshop.Repositories;

import artshop.Entities.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepo extends JpaRepository<Likes,String> {
    List<Likes> findByUserId(String user_id);
    List<Likes> findByPostId(String user_id);
}
