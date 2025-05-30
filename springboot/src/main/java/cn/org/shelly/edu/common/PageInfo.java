package cn.org.shelly.edu.common;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 通用分页返回结果
 * @author HeXin
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "通用分页返回结果")
public class  PageInfo<T> {
    @Schema(name = "list", description = "分页详细数据")
    List<T> list;
    @Schema(name = "total", description = "分页数据总数")
    long total;
    @Schema(name = "current", description = "当前页数")
    long current;
    @Schema(name = "size", description = "总数据量")
    long size;
    @Schema(name = "sizes", description = "总分页页数")
    long pages;

}
