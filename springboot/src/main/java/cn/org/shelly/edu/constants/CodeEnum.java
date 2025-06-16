package cn.org.shelly.edu.constants;

import lombok.Getter;

/**
 * 系统状态常量
 * @author shelly
 */

@Getter
public enum CodeEnum {
    /**
     * 成功标志
     */
    SUCCESS(200, "成功！"),

    /**
     * 参数异常
     */
    PARAMETER_ERROR(400, "参数异常！"),

    /**
     * 邮箱为空
     */
    EMAIL_EMPTY(265, "邮箱为空！"),

    /**
     * 邮箱格式错误
     */
    EMAIL_FORMAT_ERROR(275, "邮箱格式错误！"),

    /**
     * 邮件发送失败
     */
    EMAIL_SEND_ERROR(295, "邮件发送失败，请稍后重试！"),

    /**
     * 邮箱为空
     */
    PHONE_EMPTY(265, "手机号为空！"),

    /**
     * 手机号码格式错误
     */
    PHONE_FORMAT_ERROR(275, "手机号格式错误！"),

    /**
     * 短信发送失败
     */
    PHONE_SEND_ERROR(295, "短信发送失败，请稍后重试！"),

    /**
     * 验证码错误
     */
    CODE_ERROR(285, "验证码错误！"),

    /**
     * 验证码失效
     */
    CODE_EXPIRED(205, "验证码已过期！"),
    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(404,"当前查询的数据不存在"),

    /**
     * 未登录
     */
    NOT_LOGIN(300, "未登录，请登录后再进行操作！"),

    /**
     * 登录过期
     */
    LOGIN_EXPIRED(305, "登录已过期，请重新登录！"),

    /**
     * 系统维护
     */
    SYSTEM_REPAIR(501, "系统维护中，请稍后！"),

    /**
     * 服务异常
     */
    FAIL(500, "服务异常！"),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(255, "用户不存在"),

    /**
     * 密码错误
     */
    PASSWORD_ERROR(290, "密码错误"),

    /**
     * 访问频繁
     */
    ACCESS_LIMITED(429, "访问频繁，请稍后再试"),

    /**
      * 两次密码不一致
      */
    PASSWORD_NOT_MATCH(291, "两次密码不一致"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR(502, "服务器异常！"),

    /**
     * 没有权限
     */
    NOT_AUTHORITY(403, "没有操作权限！"),
    USERNAME_EXIST(229, "该用户名已存在！"),
    EXPORT_FAILED(277,"导出失败！"),
    IMPORT_ERROR(269,"信息有误或缺失，导入失败！"),
    NOT_FOUND_ROLE(268,"未找到该角色！"),
    ;

    private final int code;
    private final String msg;

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
