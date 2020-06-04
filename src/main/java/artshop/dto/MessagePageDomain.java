package artshop.dto;

public class MessagePageDomain {

    private String message;
    private String redirecturl;
    private Integer errorCode;
    private String buttonString;

    public MessagePageDomain() {
    }

    public MessagePageDomain(String message, String redirecturl, int errorCode, String buttonString) {
        this.message = message;
        this.redirecturl = redirecturl;
        this.errorCode = errorCode;
        this.buttonString = buttonString;
    }


    public String getButtonString() {
        return buttonString;
    }

    public void setButtonString(String buttonString) {
        this.buttonString = buttonString;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRedirecturl() {
        return redirecturl;
    }

    public void setRedirecturl(String redirecturl) {
        this.redirecturl = redirecturl;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
