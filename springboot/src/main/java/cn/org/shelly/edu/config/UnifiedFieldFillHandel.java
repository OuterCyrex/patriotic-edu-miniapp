package cn.org.shelly.edu.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充配置
 * @author shelly
 */
@Component
public class UnifiedFieldFillHandel implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String name = getUserName();
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
        // 创建者
        this.setFieldValByName("createBy", name, metaObject);
        // 更新者
        this.setFieldValByName("updateBy", name, metaObject);
        // 逻辑删除默认置0
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String name = getUserName();
        this.setFieldValByName("gmtModified", new Date(), metaObject);
        this.setFieldValByName("updateBy", name, metaObject);
    }

    private String getUserName() {
        // 如果是 Web 上下文中则获取实际登录用户的 ID，否则返回默认值
        try {
            if (SaHolder.getContext().getRequest() != null) {
                return "管理员";
            } else {
                return "佚名"; // 默认值
            }
        } catch (Exception e) {
            return "佚名"; // 默认值
        }
    }
}
