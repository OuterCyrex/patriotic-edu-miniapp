package cn.org.shelly.edu.common;

import cn.org.shelly.edu.constants.CodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回键值对对象
 * @author shelly
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "ResultMap", title = "通用返回键值对对象")
public class ResultMap extends Result<Map<String, Object>> {
    String message;
    Integer code;
    Map<String, Object> data = new HashMap<>();

    private ResultMap() {
        super();
    }

    /**
     * 判断执行是否成功
     */
    public static ResultMap isSuccess(Boolean isSuccess) {
        if (Boolean.TRUE.equals(isSuccess)) {
            return success();
        } else {
            return fail();
        }
    }


    /**
     * 成功结果
     */
    public static ResultMap success() {
        ResultMap resultMap = new ResultMap();
        resultMap.message = "Success";
        resultMap.code = CodeEnum.SUCCESS.getCode();
        return resultMap;
    }

    /**
     * 失败结果
     */
    public static ResultMap fail() {
        ResultMap resultMap = new ResultMap();
        resultMap.code = CodeEnum.FAIL.getCode();
        return resultMap;
    }

    public static ResultMap fail(String message) {
        ResultMap result = new ResultMap();
        result.code = CodeEnum.FAIL.getCode();
        result.message = message;
        return result;
    }

    /**
     * 添加数据（键值对形式）
     */
    public ResultMap data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 添加数据（Map集合形式）
     */
    public ResultMap data(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    /**
     * 设置返回信息
     */
    @Override
    public ResultMap message(String message) {
        this.message = message;
        return this;
    }

    @Override
    public Result<Map<String, Object>> code(int code) {
        this.code = code;
        return this;
    }
}
