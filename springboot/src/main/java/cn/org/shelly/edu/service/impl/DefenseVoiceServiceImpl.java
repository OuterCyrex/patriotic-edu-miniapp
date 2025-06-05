package cn.org.shelly.edu.service.impl;
import cn.org.shelly.edu.exception.CustomException;
import cn.org.shelly.edu.mapper.DefenseVoiceMapper;
import cn.org.shelly.edu.model.po.DefenseVoice;
import cn.org.shelly.edu.model.po.VoiceComment;
import cn.org.shelly.edu.model.req.CommentReq;
import cn.org.shelly.edu.service.DefenseVoiceService;
import cn.org.shelly.edu.service.VoiceCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author Shelly6
* @description 针对表【defense_voice(国防心声表)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class DefenseVoiceServiceImpl extends ServiceImpl<DefenseVoiceMapper, DefenseVoice>
    implements DefenseVoiceService {
    private  final VoiceCommentService voiceCommentService;

    @Override
    public void comment(CommentReq req) {
        log.info("comment:{}", req);
        lambdaUpdate()
                .eq(DefenseVoice::getId, req.getId())
                .setSql("comments_count = comments_count + 1")
                .update();
        VoiceComment voiceComment = new VoiceComment()
                .setVoiceId(req.getId())
                .setUserId(1L)
                .setParentId(0L)
                .setContent(req.getContent());
        if(req.getType() == 1){
             voiceCommentService.save(voiceComment);
        }
        else if(req.getType() == 2){
             voiceComment.setParentId(req.getReplyId());
            voiceCommentService.save(voiceComment);
        }else{
            throw  new CustomException("类型错误");
        }
    }
}




