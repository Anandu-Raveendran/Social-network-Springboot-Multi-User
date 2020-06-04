package artshop.dto;

public class ChangePasswordDomain {
    private String oldPassword;
    private String newPassword;
    private String errorMessage;

    public ChangePasswordDomain() {
    }

    public ChangePasswordDomain(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public ChangePasswordDomain(String oldPassword, String newPassword, String errorMessage) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.errorMessage = errorMessage;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ChangePasswordDomain{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
