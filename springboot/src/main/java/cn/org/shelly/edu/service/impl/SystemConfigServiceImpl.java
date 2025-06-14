package cn.org.shelly.edu.service.impl;
import cn.org.shelly.edu.config.properties.OssProperties;
import cn.org.shelly.edu.exception.CustomException;
import cn.org.shelly.edu.mapper.SystemConfigMapper;
import cn.org.shelly.edu.model.po.SystemConfig;
import cn.org.shelly.edu.service.SystemConfigService;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
* @author Shelly6
* @description 针对表【system_config(系统配置表（单行）)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfig>
    implements SystemConfigService {
    private final OssProperties ossProperties;

    @Override
    public String uploadTiny(MultipartFile file) throws IOException {
        String extName = getExtension(file);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileName = uuid + "." + extName;
        String objectName = "edu" + "/" + fileName;
        boolean uploadSuccess = this.upload(objectName, file.getInputStream());
        if (!uploadSuccess) {
            throw new CustomException("上传失败");
        }
        return getFileAccessUrl(objectName);
    }
    public String getExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null && filename.contains(".")) {
            return filename.substring(filename.lastIndexOf('.') + 1);
        }
        return "jpg";
    }

    private String getFileAccessUrl(String filePath) {
        return ossProperties.getUrl() + filePath;
    }

    public boolean upload(String path, InputStream stream) {
        OSS ossClient = getOssClient();
        try {
            // 调用 OSS 方法上传
            ossClient.putObject(ossProperties.getBucketName(), path, stream);
            return true; // 上传成功
        } catch (OSSException oe) {
            log.error("OSS 异常：{}", oe.getErrorMessage());
            log.error("错误码：{}", oe.getErrorCode());
            log.info("Request ID：{}", oe.getRequestId());
            log.info("Host ID：{}", oe.getHostId());
            return false;
        } catch (ClientException ce) {
            log.error("客户端异常：{}", ce.getMessage());
            return false;
        } finally {
            ossClient.shutdown();
        }
    }
    /**
     * 获取ossClient
     *
     * @return {@link OSS} ossClient
     */
    private OSS getOssClient() {
        return new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }
}




