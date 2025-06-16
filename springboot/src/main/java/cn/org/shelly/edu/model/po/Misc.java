package cn.org.shelly.edu.model.po;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 
 * @TableName misc
 */
@TableName(value ="misc")
@Data
public class Misc implements Serializable {
    /**
     * 
     */
    @TableId(value = "misc_key")
    private String miscKey;

    @TableField(value = "value")
    private String value;

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

    public static boolean isJson(String str) {
        try {
            JSONUtil.parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}