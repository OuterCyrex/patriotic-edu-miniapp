package cn.org.shelly.edu.model.req;

public record PasswordReq(String oldPassword, String newPassword, String confirmPassword) {
}
