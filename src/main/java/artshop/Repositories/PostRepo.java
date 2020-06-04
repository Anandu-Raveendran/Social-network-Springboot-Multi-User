package artshop.Repositories;


import artshop.Entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepo extends JpaRepository<Post, String>
{
    Page<Post> findAll(Pageable pageable);

    List<Post> findByUserId(String userId);
}
