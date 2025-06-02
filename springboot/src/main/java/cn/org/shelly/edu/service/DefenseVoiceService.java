package cn.org.shelly.edu.service;

import cn.org.shelly.edu.model.po.DefenseVoice;
import cn.org.shelly.edu.model.req.CommentReq;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Shelly6
* @description 针对表【defense_voice(国防心声表)】的数据库操作Service
* @createDate 2025-05-31 15:29:26
*/
public interface DefenseVoiceService extends IService<DefenseVoice> {

    void comment(CommentReq req);
}
