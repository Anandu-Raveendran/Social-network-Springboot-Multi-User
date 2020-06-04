package artshop.Services;

import artshop.Entities.Comments;

import java.util.List;


public interface CommentsService {


    List<Comments> getCommentsByPost(String post_id);

    List<Comments> getCommentsByPostAndUser(String post_id, String user_id);

    Comments saveComments(Comments Comments);

    boolean deleteComments(String Comments_id);

    List<Comments> getTopCommentsByPostAndUser(String postId, String userId);
}
