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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
public class FileUploadController {
    private final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Value("${image.upload.location}")
    private String UPLOADED_FOLDER;


    // Multiple file upload for users only
    @PostMapping("/user/upload")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> uploadFileMultiArtist(
            @Valid PostCreateDomain postCreateDomain, BindingResult bindingResult, HttpServletRequest request) {
        LOG.info("Post upload " + postCreateDomain);
        List<String> errors = new ArrayList<>();
        if ((StringUtils.isEmpty(postCreateDomain.getFiles()))
                && StringUtils.isEmpty(postCreateDomain.getText())
                && (StringUtils.isEmpty(postCreateDomain.getLocation()))) {
            errors.add("The post is empty");
            //If everything is null then return
        }

        if (errors.size() > 0) {
            return new ResponseEntity<>( errors, HttpStatus.BAD_REQUEST);
        }
        ModelAndView modelAndView;
        String uploadedFileName = Arrays.stream(postCreateDomain.getFiles()).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));
        try {
            // to get the current user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            User user = userService.findUserByEmail(auth.getName());
            // create new artPiece in the db
            Post post = new Post(user.getUserId(),
                    postCreateDomain.getLocation(), postCreateDomain.getText(),
                    Constants.PostStatus.ACTIVE.getValue(), System.currentTimeMillis(), 0L);
            // save the image in the locations
            post.setImgLocation(saveUploadedFiles(Arrays.asList(postCreateDomain.getFiles())));

            LOG.info("Attempting to save {}", post);
            postService.savePost(post);
            LOG.info("User {} uploaded = {}", auth.getName(), post);
        } catch (IOException e) {
            LOG.error("Exception {}",e);
            return new ResponseEntity<>("Unable to upload, try again later", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Uploaded successfully.", HttpStatus.OK);
    }

    //save file
    private String saveUploadedFiles(List<MultipartFile> files) throws IOException {
        LOG.info("Attempting to save {}", files);
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                LOG.info("File is empty");
                continue; //next pls
            }
            // check file type
            try (InputStream input = file.getInputStream()) {
                try {
                    ImageIO.read(input).toString();
                    // It's an image (only BMP, GIF, JPG and PNG are recognized).

                    String fileName = "art_" + UUID.randomUUID().toString() + file.getOriginalFilename()
                            .substring(file.getOriginalFilename().indexOf('.'));

                    byte[] bytes = file.getBytes();
                    //Save the uploaded file to this folder
                    Path path = Paths.get(UPLOADED_FOLDER + fileName);
                    Files.write(path, bytes);
                    LOG.info("File {} saved ", UPLOADED_FOLDER + fileName);
                    return "/uploadedimages/" + fileName;

                } catch (Exception e) {
                    // It's not an image.
                    File new_file = new File(UPLOADED_FOLDER);
                    //Creating the directory
                    boolean bool = new_file.mkdir();
                    if(bool){
                        System.out.println("Directory created successfully");
                    }else{
                        System.out.println("Sorry couldnt create specified directory");
                    }
                    throw new IOException("Invalid file type or "+UPLOADED_FOLDER+" doesnt exist", e);

                }
            }
        }
        return null;
    }
}