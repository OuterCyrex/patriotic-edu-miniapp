package cn.org.shelly.edu.service;

import cn.org.shelly.edu.model.po.VoiceComment;
import cn.org.shelly.edu.model.resp.VoiceCommentResp;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Shelly6
* @description 针对表【voice_comment(心声评论表)】的数据库操作Service
* @createDate 2025-05-31 15:29:26
*/
public interface VoiceCommentService extends IService<VoiceComment> {

    IPage<VoiceCommentResp> listComment(Long voiceId, Long pageNum, Long pageSize);
}
