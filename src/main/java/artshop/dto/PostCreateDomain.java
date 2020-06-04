package artshop.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Arrays;

public class PostCreateDomain implements Serializable {

    private String location;
    private String text;
    private MultipartFile[] files;

    public PostCreateDomain() {
    }

    public PostCreateDomain(String location, String text, MultipartFile[] files) {
        this.location = location;
        this.text = text;
        this.files = files;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }


    @Override
    public String toString() {
        return "PostDomain{" +
                "title='" + location + '\'' +
                ", text='" + text + '\'' +
                ", files=" + Arrays.toString(files) +
                '}';
    }
}