package artshop.Services;

import artshop.Entities.Follow;
import artshop.Entities.User;
import artshop.exception.CustomException;
import artshop.utils.Constants;

import java.util.List;

public interface FollowService {

    List<User> getFollowers(String user_id);
    List<User> getFollowings(String user_id);
    boolean followUser(String user_id,String followedByUser_id);
    boolean unFollow(String user_id,String followedByUser_id);
    boolean isFollowing(String user_id, String FollowedByUser_id);
}