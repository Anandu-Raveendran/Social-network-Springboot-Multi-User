package artshop.Repositories;

import artshop.Entities.Follow;
import artshop.Entities.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepo extends JpaRepository<Follow,String> {
    List<Follow> findByUserId(String user_id);
    List<Follow> findByFollowedByUserId(String user_id);
    Follow findOneByUserIdAndFollowedByUserId(String user_id, String followedBYUser_id);
}
