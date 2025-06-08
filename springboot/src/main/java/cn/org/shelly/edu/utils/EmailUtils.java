package cn.org.shelly.edu.utils;

import cn.org.shelly.edu.constants.CodeEnum;
import cn.org.shelly.edu.exception.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    /**
     * 判断邮箱是否合法
     */
    public static void isValidEmail(String email) {
        if (StringUtils.isBlank(email)) {
            throw new CustomException(CodeEnum.EMAIL_EMPTY);
        }
        if (!Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email)) {
            throw new CustomException(CodeEnum.EMAIL_FORMAT_ERROR);
        }
    }

    @Async
    public void sendMailMessage(String email, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // 发件人邮箱
            message.setFrom(sendMailer);
            // 收件人邮箱
            message.setTo(email);
            // 邮件标题
            message.setSubject("欢迎使用红星国防平台！");
            message.setText("这是您的注册验证码：\n" + "<font color='blue'>" + code + "</font>" + "\n验证码五分钟内有效。若非本人操作，请忽略此邮件！");
            mailSender.send(message);
        } catch (MailException e) {
            throw new CustomException(CodeEnum.EMAIL_SEND_ERROR);
        }
    }
}