package artshop.Controller;

import artshop.Entities.Post;
import artshop.Entities.User;
import artshop.Services.PostService;
import artshop.Services.UserService;
import artshop.dto.PostCreateDomain;
import artshop.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/email")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_USER')")
    public ModelAndView getUserDetails(@RequestHeader String email) {
        LOG.info("get user details of {}", email);
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return new ModelAndView("redirect:/message?message=User not found.&redirectUrl=/register&buttonString=Okay!");
        }
        ModelAndView modelAndView = new ModelAndView("artist_bio");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping(value = "/completereg")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String completeArtistRegisteration(Model model) {
        LOG.info("Artist controller getting register page");
        model.addAttribute("page", "gallery");
        return "complete_artist_register";
    }



    @GetMapping(value = "/gallery")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getAGallery(Model model) {
        LOG.info("ArtistController getting gallery");
        return "artistgallery";
    }


    @GetMapping(value = "/dashboard")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ModelAndView getDashboard(@RequestParam(required = false) String page) {
        LOG.info("ArtistController getting dashboard with {}", page);
        ModelAndView modelAndView = new ModelAndView("artist_dashboard");

        if (page == null)
            page = "gallery";
        else if (page.equalsIgnoreCase("profile")) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findUserByEmail(auth.getName());
            if (user == null) {
                LOG.error("Artist not found");
                return new ModelAndView("redirect:/message");
            }
            modelAndView.addObject("artist", user);
        } else if (page.equalsIgnoreCase("upload")) {
            modelAndView.addObject("artPieceDomain", new PostCreateDomain());
        }
        modelAndView.addObject("page", page);
        return modelAndView;
    }


    @GetMapping("/new-upload")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getUploadPage(Model model) {
        LOG.info("Artist controller getting upload page");
        model.addAttribute("PostDomain", new PostCreateDomain());
        return "artist_upload";
    }


    @GetMapping("next_image_set")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> getMyNextImageSet(Integer skip, Integer limit) {
        LOG.info("Getting artists next set skip = {}, limit = {}", skip, limit);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if (user == null) {
            LOG.error("Artist not found");
            return new ResponseEntity<>("Artist not found", HttpStatus.BAD_REQUEST);
        }

        if (skip == null)
            skip = 0;
        if (limit == null)
            limit = 5;
        if (skip < 0 || limit < 1) {
            LOG.error("Bad skip limit values");
            return new ResponseEntity<>("Skip or limit value not proper", HttpStatus.BAD_REQUEST);
        }
        List<Post> posts = postService.getMyNextImages(skip, limit, user.getUserId());
        // when the skip is too much and that much data is not present
//        if (artPieces == null || artPieces.isEmpty()) {
//            LOG.info("skip invalid trying with skip = 0");
//            artPieces = artService.getMyNextImages(0, limit, artist);
//        }
        LOG.info("Data received = {}", posts);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/image/{imageId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_USER')")
    public ModelAndView getImage(@PathVariable("postId") String postId) {
        LOG.info("get imag imageId= {}", postId);
        Post post = postService.getPostDetails(postId);
        ModelAndView modelAndView = new ModelAndView("myimageview");
        modelAndView.addObject("artPiece", post);
        return modelAndView;
    }

    //TODO: find a better way to do it. display only images whos status is active in gallery
    @DeleteMapping("/image")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_USER')")
    public ResponseEntity<?> DeleteImage(@RequestHeader String imageId) {
        LOG.info("Delete art "+imageId);
        Post post = postService.getPostDetails(imageId);
        post.setPostStatus(Constants.PostStatus.DELETED.getValue());
        postService.deletePost(post.getPostId());
        //TODO: This only deletes the db entry. also delete the image file in the drive
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}

//TODO: link completereg to backend
//TODO: If an person registers but doesnt complete reg but logs in later it should redirect to completereg page only


// TODO: Payment Email about us. under construction