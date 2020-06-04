package artshop.ServiceImpl;

import artshop.Entities.Comments;
import artshop.Repositories.CommentsRepo;
import artshop.Services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


/**
 * Created by Anandu on 14/11/17
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    CommentsRepo commentsRepo;

    @Override
    public List<Comments> getCommentsByPost(String post_id) {
        //TODO:add a limit
        return commentsRepo.findByPostId(post_id);
    }

    @Override
    public List<Comments> getCommentsByPostAndUser(String post_id,String user_id) {
        //TODO:add a limit
        return commentsRepo.findByPostIdAndUserId(post_id,user_id);
    }


    @Override
    public Comments saveComments(Comments comments) {

        //only for first save this will be null
        if (comments.getCommentId() == null) {
            Comments Old;
            do {
                comments.setCommentId(String.valueOf(UUID.randomUUID()));
                Old = commentsRepo.findOne(comments.getCommentId());
                //Old will be null when the UUID is not in use
            } while (Old != null);
        }

        return commentsRepo.save(comments);
    }


    @Override
    public boolean deleteComments(String Comments_id) {
        commentsRepo.delete(Comments_id);
        return true;
    }

    @Override
    public List<Comments> getTopCommentsByPostAndUser(String postId, String userId) {
        return commentsRepo.findByPostIdAndUserId(postId,userId);
    }

}
