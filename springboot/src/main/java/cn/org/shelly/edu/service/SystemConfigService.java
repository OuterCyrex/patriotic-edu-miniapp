package cn.org.shelly.edu.service;

import cn.org.shelly.edu.model.po.SystemConfig;
import cn.org.shelly.edu.model.req.FileUploadReq;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
* @author Shelly6
* @description 针对表【system_config(系统配置表（单行）)】的数据库操作Service
* @createDate 2025-05-31 15:29:26
*/
public interface SystemConfigService extends IService<SystemConfig> {

    String uploadTiny(MultipartFile file) throws IOException;
}
