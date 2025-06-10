package cn.org.shelly.edu.service.impl;
import cn.org.shelly.edu.mapper.VoiceCommentMapper;
import cn.org.shelly.edu.model.po.User;
import cn.org.shelly.edu.model.po.VoiceComment;
import cn.org.shelly.edu.model.resp.VoiceCommentResp;
import cn.org.shelly.edu.service.UserService;
import cn.org.shelly.edu.service.VoiceCommentService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author Shelly6
* @description 针对表【voice_comment(心声评论表)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
@RequiredArgsConstructor
public class VoiceCommentServiceImpl extends ServiceImpl<VoiceCommentMapper, VoiceComment>
    implements VoiceCommentService {
    private final UserService  userService;

    @Override
    public IPage<VoiceCommentResp> listComment(Long voiceId, Long pageNum, Long pageSize) {
        var list = lambdaQuery()
                 .eq(VoiceComment::getVoiceId, voiceId)
                 .eq(VoiceComment::getStatus,1)
                 .list()
                 .stream()
                 .map(VoiceCommentResp::toResp)
                 .toList();
        if (list.isEmpty()){
            return new Page<>(pageNum, pageSize, 0);
        }
        Map<Long, User> userMap = userService
                .lambdaQuery()
                .select(User::getId, User::getNickname, User::getAvatarUrl)
                .in(User::getId, list.stream().map(VoiceCommentResp::getUserId).toList())
                .list()
                .stream()
                .collect(Collectors.toMap(User::getId, user -> user));
        list = list.stream()
                .map(comment -> {
                    comment.setNickName(userMap.get(comment.getUserId()).getNickname());
                    comment.setAvatar(userMap.get(comment.getUserId()).getAvatarUrl());
                    return comment;
                }).toList();
        List<VoiceCommentResp> rootComments = list.stream()
                .filter( x -> x.getParentId() == 0)
                .sorted(Comparator.comparing(VoiceCommentResp::getLikesCount))
                .toList();
        int total = rootComments.size();
        int fromIndex = (int)((pageNum - 1) * pageSize);
        int toIndex = Math.min(fromIndex + pageSize.intValue(), total);
        if (fromIndex > total) {
            return new Page<VoiceCommentResp>().setRecords(Collections.emptyList()).setTotal(total);
        }
        List<VoiceCommentResp> pageRoots = rootComments.subList(fromIndex, toIndex);
        for (VoiceCommentResp root : pageRoots) {
            setChildren(root, list);
        }
        return new Page<VoiceCommentResp>()
                .setRecords(pageRoots)
                .setTotal(total)
                .setCurrent(pageNum)
                .setSize(pageSize);
    }

    private void setChildren(VoiceCommentResp parent, List<VoiceCommentResp> allComments) {
        //获取指定父id子评论
        List<VoiceCommentResp> children = allComments.stream()
                .filter(comment -> parent.getId().equals(comment.getParentId()))
                .toList();

        for (VoiceCommentResp child : children) {
            setChildren(child, allComments);
        }
        parent.setChildren(children);
    }
}




