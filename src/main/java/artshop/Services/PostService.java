package artshop.Services;

import artshop.Entities.Likes;
import artshop.Entities.Post;
import artshop.exception.CustomException;

import java.util.List;


public interface PostService {

    Post getPostDetails(String post_id);

    List<Post> getPostByUser(String userId);

    Post savePost(Post post);

    boolean deletePost(String post_id);

    List<Post> getNextImages(int skip, int limit);

    List<Post> getMyNextImages(Integer skip, Integer limit, String user_id);

    void like(String post_id, String user_id) throws CustomException;

    String getTopLikes(String postId, String userId);

    List<Likes> getAllLikes(String postId);

}
