package cn.org.shelly.edu.utils;

import cn.org.shelly.edu.constants.CodeEnum;
import cn.org.shelly.edu.exception.CustomException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@Component
@Slf4j
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    @Autowired
    private org.thymeleaf.TemplateEngine templateEngine;

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
            // 创建邮件消息
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 设置邮件基本信息
            helper.setFrom("40505282@qq.com", "红星耀国防平台");
            helper.setTo(email);
            helper.setSubject("【红星耀国防平台】邮箱验证码");

            // 准备Thymeleaf模板变量
            Context context = new Context();
            context.setVariable("code", code);

            String htmlContent = templateEngine.process("register.html", context);

            // 设置邮件内容
            helper.setText(htmlContent, true);

            // 发送邮件
            mailSender.send(message);

            log.info("验证码邮件发送成功: {} -> {}", email, code);

        } catch (Exception e) {
            log.error("发送验证码邮件失败: {}", e.getMessage());
            throw new CustomException("邮件发送失败");
        }
    }
}