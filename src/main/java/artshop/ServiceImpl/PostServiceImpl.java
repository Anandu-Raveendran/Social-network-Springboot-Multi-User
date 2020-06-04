package artshop.ServiceImpl;

import artshop.Entities.*;
import artshop.Repositories.LikesRepo;
import artshop.Repositories.PostRepo;
import artshop.Services.PostService;
import artshop.Services.UserService;
import artshop.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Anandu on 14/11/17
 */
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepo postRepo;

    @Autowired
    LikesRepo likesRepo;

    @Autowired
    UserService userService;

    @Override
    public Post getPostDetails(String id) {
        return postRepo.findOne(id);
    }


    @Override
    public List<Post> getPostByUser(String user_id) {
        return postRepo.findByUserId(user_id);
    }


    @Override
    public Post savePost(Post post) {

        //only for first save this will be null
        if (post.getPostId() == null) {
            Post Old;
            do {
                post.setPostId(String.valueOf(UUID.randomUUID()));
                Old = postRepo.findOne(post.getPostId());
                //Old will be null when the UUID is not in use
            } while (Old != null);
        }

        return postRepo.save(post);
    }


    @Override
    public boolean deletePost(String post_id) {
        postRepo.delete(post_id);
        return true;
    }

    @Override
    public List<Post> getNextImages(int skip, int limit) {

        List<Post> posts = new ArrayList<>();

        // get the art pieces till we get the required number
        int tempLimit = limit;
        do {
            posts.addAll(postRepo.findAll(new PageRequest(skip, tempLimit, Sort.Direction.DESC, "createdOn")).getContent());
            skip = 0;
            tempLimit = limit - posts.size();
        } while (posts.size() < limit && posts.size() != 0);

        return posts;
    }

    @Override
    public void like(String post_id, String user_id) throws CustomException {
        Post post = postRepo.getOne(post_id);
        if (post == null)
            throw new CustomException("post not found");
        User user = userService.findUserByEmail(user_id);
        Likes likes = new Likes(user.getUserId(),post_id);
        //only for first save this will be null
        Likes Old;
        do {
            likes.setLikeId(String.valueOf(UUID.randomUUID()));
            Old = likesRepo.findOne(likes.getLikeId());
            //Old will be null when the UUID is not in use
        } while (Old != null);

        if(likesRepo.save(likes)!=null);
        post.setLikesCount(post.getLikesCount() + 1);
        postRepo.save(post);
    }

    @Override
    public String getTopLikes(String postId, String userId) {
        return "This guys liked ur pic";
    }

    @Override
    public List<Likes> getAllLikes(String postId) {
        return likesRepo.findByPostId(postId);
    }

    @Override
    public List<Post> getMyNextImages(Integer skip, Integer limit, String user_id) {

        List<Post> posts = new ArrayList<>();

        for (Post postFor : postRepo.findAll(new PageRequest(skip, limit, Sort.Direction.DESC, "created_on")).getContent()) {
            if (postFor.getUserId().compareToIgnoreCase(user_id)==0)
                posts.add(postFor);
        }
        return posts;
    }


}
