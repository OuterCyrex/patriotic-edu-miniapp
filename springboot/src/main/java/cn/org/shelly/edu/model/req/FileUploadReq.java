package cn.org.shelly.edu.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "FileUploadReq", description = "文件上传请求")
public class FileUploadReq {
    /**
     * 文件名称
     */
    @Schema(description = "文件名称", example = "测试文档.pdf")
    private String fileName;
    /**
     * 文件大小
     */
    @Schema(description = "文件大小（字节）",  example = "1024000")
    private Long size;
    /**
     * 文件
     */
    @Schema(description = "上传的文件对象", type = "file")
    private MultipartFile file;
}
