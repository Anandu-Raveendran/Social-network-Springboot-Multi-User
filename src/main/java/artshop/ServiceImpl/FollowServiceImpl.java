package artshop.ServiceImpl;

import artshop.Entities.Comments;
import artshop.Entities.Follow;
import artshop.Entities.Post;
import artshop.Entities.User;
import artshop.Repositories.FollowRepo;
import artshop.Services.FollowService;
import artshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    FollowRepo followRepo;

    @Autowired
    UserService userService;


    @Override
    public List<User> getFollowers(String user_id) {
        List<Follow> followers = followRepo.findByUserId(user_id);
        List<User> followers_user = new ArrayList<>();
        for (Follow follower : followers) {
            followers_user.add(userService.findUserByid(follower.getFollowedByUserId()));
        }
        return followers_user;
    }

    @Override
    public List<User> getFollowings(String user_id) {
        List<Follow> followers = followRepo.findByFollowedByUserId(user_id);
        List<User> following_user = new ArrayList<>();
        for (Follow follower : followers) {
            following_user.add(userService.findUserByid(follower.getUserId()));
        }
        return following_user;
    }

    @Override
    public boolean followUser(String user_id, String followedByUser_id) {
        if (userService.findUserByid(user_id) != null &&
                userService.findUserByid(followedByUser_id) != null) {
            Follow follow = new Follow(user_id, followedByUser_id);
            saveFollow(follow);
            return true;
        } else {
            return false;
        }
    }


    public Follow saveFollow(Follow follow) {

        //only for first save this will be null
        if (follow.getFollowId() == null) {
            Follow Old;
            do {
                follow.setFollowId(String.valueOf(UUID.randomUUID()));
                Old = followRepo.findOne(follow.getFollowId());
                //Old will be null when the UUID is not in use
            } while (Old != null);
        }
        return followRepo.save(follow);
    }

    @Override
    public boolean unFollow(String user_id, String followedByUser_id) {
        Follow follow = followRepo.findOneByUserIdAndFollowedByUserId(user_id, followedByUser_id);
        if (follow != null) {
            followRepo.delete(follow);
        }
        return true;
    }

    @Override
    public boolean isFollowing(String user_id, String followedByUser_id) {
        Follow follow = followRepo.findOneByUserIdAndFollowedByUserId(user_id, followedByUser_id);
        if (follow != null) {
            return true;
        }
        return false;
    }
}
