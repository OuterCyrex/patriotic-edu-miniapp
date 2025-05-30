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
        Long id = getUserId();
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
        // 创建者
        this.setFieldValByName("createBy", id, metaObject);
        // 更新者
        this.setFieldValByName("updateBy", id, metaObject);
        // 逻辑删除默认置0
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long id = getUserId();
        this.setFieldValByName("gmtModified", new Date(), metaObject);
        this.setFieldValByName("updateBy", id, metaObject);
    }

    private Long getUserId() {
        // 如果是 Web 上下文中则获取实际登录用户的 ID，否则返回默认值
        try {
            if (SaHolder.getContext().getRequest() != null) {
                return StpUtil.getLoginId(0L);
            } else {
                return 1L; // 默认值
            }
        } catch (Exception e) {
            return 1L; // 默认值
        }
    }
}