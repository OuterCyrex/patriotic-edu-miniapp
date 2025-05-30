package cn.org.shelly.edu.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.interceptor.SaInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token配置类
 * @author shelly
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    /**
     * 注册 Sa-Token 拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor().setAuth(r -> {
                    // ---------- 设置一些安全响应头 ----------
                    SaHolder.getResponse()
                            // 服务器名称
                            .setServer("server")
                            // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
                            .setHeader("X-Frame-Options", "SAMEORIGIN")
                            // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
                            .setHeader("X-XSS-Protection", "1; mode=block")
                            // 禁用浏览器内容嗅探
                            .setHeader("X-Content-Type-Options", "nosniff")
                            // 是否允许浏览器对JS进行调用： 0=不允许 | 1=允许
                            .setHeader("X-Permitted-Cross-Domain-Policies", "none")
                            // 是否允许浏览器发送身份验证信息： 0=不允许 | 1=允许
                            .setHeader("X-Requested-With", "?")
                            // 是否允许浏览器发送 Cookie： 0=不允许 | 1=允许
                            .setHeader("X-Powered-By", "JDK 17")
                            // 是否允许浏览器发送 Referer： 0=不允许 | 1=允许
                            .setHeader("Referrer-Policy", "strict-origin-when-cross-origin")
                            // 是否允许浏览器发送 Sec-Fetch-Dest： 0=不允许 | 1=允许
                            .setHeader("Sec-Fetch-Dest", "empty")
                            // 是否允许浏览器发送 Sec-Fetch-Mode： 0=不允许 | 1=允许
                            .setHeader("Sec-Fetch-Mode", "cors")
                            // 是否允许浏览器发送 Sec-Fetch-Site： 0=不允许 | 1=允许
                            .setHeader("Sec-Fetch-Site", "same-origin")
                            // 是否允许浏览器发送 Sec-GPC： 0=不允许 | 1=允许
                            .setHeader("Sec-GPC", "1");
                }))
                .addPathPatterns("/**")
                .excludePathPatterns("/doc.html/**",  "/user/login");
    }
}
