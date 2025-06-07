package cn.org.shelly.edu.service.impl;
import cn.dev33.satoken.stp.StpUtil;
import cn.org.shelly.edu.exception.CustomException;
import cn.org.shelly.edu.mapper.DefenseVoiceMapper;
import cn.org.shelly.edu.model.po.DefenseVoice;
import cn.org.shelly.edu.model.po.Likes;
import cn.org.shelly.edu.model.po.VoiceComment;
import cn.org.shelly.edu.model.req.CommentReq;
import cn.org.shelly.edu.service.DefenseVoiceService;
import cn.org.shelly.edu.service.LikesService;
import cn.org.shelly.edu.service.VoiceCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private final LikesService likesService;

    @Override
    public void comment(CommentReq req) {
        log.info("comment:{}", req);
        lambdaUpdate()
                .eq(DefenseVoice::getId, req.getId())
                .setSql("comments_count = comments_count + 1")
                .update();
        VoiceComment voiceComment = new VoiceComment()
                .setVoiceId(req.getId())
                .setUserId(StpUtil.getLoginIdAsLong())
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

    @Override
    public List<Long> getLikes(Long voiceId) {
        if(voiceId == -1L){
            return likesService.lambdaQuery()
                    .select(Likes::getTargetId)
                    .eq(Likes::getTargetType, 1)
                    .eq(Likes::getUserId, StpUtil.getLoginIdAsLong())
                    .list()
                    .stream()
                    .map(Likes::getTargetId)
                    .toList();
        }
        List<Long> list = voiceCommentService.lambdaQuery()
                .select(VoiceComment::getId)
                .eq(VoiceComment::getVoiceId, voiceId)
                .list()
                .stream()
                .map(VoiceComment::getId)
                .toList();
        return likesService.lambdaQuery()
                .select(Likes::getTargetId)
                .in(Likes::getTargetId, list)
                .eq(Likes::getTargetType, 2)
                .eq(Likes::getUserId, StpUtil.getLoginIdAsLong())
                .list()
                .stream()
                .map(Likes::getTargetId)
                .toList();
    }
}




