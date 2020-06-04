package artshop.Controller;

import artshop.Entities.Likes;
import artshop.Entities.Post;
import artshop.Entities.User;
import artshop.Services.CommentsService;
import artshop.Services.PostService;
import artshop.Services.UserService;
import artshop.dto.PostDomain;
import artshop.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("gallery")
public class GalleryController {

    private final Logger LOG = LoggerFactory.getLogger(GalleryController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentsService commentsService;

    @GetMapping("next_image_set")
    public ResponseEntity<?> getNextImageSet(Integer skip, Integer limit) {
        LOG.info("Getting next set skip = {}, limit = {}", skip, limit);

        if (skip == null)
            skip = 0;
        if (limit == null)
            limit = 5;
        if (skip < 0 || limit < 1) {
            LOG.error("Bad skip limit values");
            return new ResponseEntity<>("Skip or limit value not proper", HttpStatus.BAD_REQUEST);
        }
        List<Post> posts = postService.getNextImages(skip, limit);
        List<PostDomain> postDomains = new ArrayList<PostDomain>();
        for(Post post: posts) {
            User user = userService.findUserByid(post.getUserId());
            PostDomain postDomain = new PostDomain(post.getPostId(), user.getName(), user.getImageLocation(), post.getImgLocation(), post.getPostTitle(),
                    post.getLikesCount(), post.getPostText(), post.getCreatedOn(),
                    postService.getTopLikes(post.getPostId(), user.getUserId()),
                    commentsService.getTopCommentsByPostAndUser(post.getPostId(), user.getUserId()));

            postDomains.add(postDomain);
        }
        // when the skip is too much and that much data is not present
        if (postDomains == null || postDomains.isEmpty()) {
            LOG.info("skip invalid trying with skip = 0");
            posts = postService.getNextImages(0, limit);
        }
        LOG.info("Data received = {}", postDomains);
        return new ResponseEntity<>(postDomains, HttpStatus.OK);
    }

    @GetMapping("image/{imageId}")
    @PreAuthorize("hasRole('ROLE_ARTIST') or hasRole('ROLE_USER')")
    public ModelAndView getImage(@PathVariable("imageId") String imageId) {
        LOG.info("get image called with imageid {}",imageId);
        Post post = postService.getPostDetails(imageId);
        ModelAndView modelAndView = new ModelAndView("imageview");
        modelAndView.addObject("post", post);
        return modelAndView;
    }

    @GetMapping("like/{imageId}")
    public ResponseEntity<?> likeart(@PathVariable("imageId") String imageId) {
        LOG.info("get like image called with imageid {}",imageId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            postService.like(imageId, auth.getName());
        } catch (CustomException e) {
            LOG.error("error while liking image {}", imageId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
