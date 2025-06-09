package cn.org.shelly.edu.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.org.shelly.edu.common.PageInfo;
import cn.org.shelly.edu.common.Result;
import cn.org.shelly.edu.model.po.DefenseVoice;
import cn.org.shelly.edu.model.po.Likes;
import cn.org.shelly.edu.model.po.VoiceComment;
import cn.org.shelly.edu.model.po.Word;
import cn.org.shelly.edu.model.req.CommentReq;
import cn.org.shelly.edu.model.req.VoiceReq;
import cn.org.shelly.edu.model.resp.VoiceCommentResp;
import cn.org.shelly.edu.model.resp.VoiceDetailResp;
import cn.org.shelly.edu.model.resp.VoiceFrontPageResp;
import cn.org.shelly.edu.model.resp.VoicePageResp;
import cn.org.shelly.edu.service.DefenseVoiceService;
import cn.org.shelly.edu.service.LikesService;
import cn.org.shelly.edu.service.VoiceCommentService;
import cn.org.shelly.edu.service.WordService;
import cn.org.shelly.edu.utils.NlpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 心声控制器
 * @author shelly
 */
@RestController
@RequestMapping("/voice")
@Tag(name = "心声管理")
@RequiredArgsConstructor
@Slf4j
public class VoiceController {
  private final DefenseVoiceService defenseVoiceService;
  private final VoiceCommentService voiceCommentService;
  private final NlpUtil nlpUtil;
  private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
  private final WordService wordService;
  private final LikesService likesService;

  @PostMapping("/submit")
  @Operation(summary = "提交心声")
  @SaCheckLogin
  public Result<Void> submit(@Validated @RequestBody VoiceReq req) {
      log.info("提交心声req：{}", req);
      DefenseVoice defenseVoice = VoiceReq.toDefenseVoice(req);
      String userId = StpUtil.getLoginIdAsString();
      defenseVoice.setCreateBy(userId);
      defenseVoice.setUpdateBy(userId);
      log.info("提交心声po：{}", defenseVoice);
      if(req.getContent().length() > 300){
        return Result.fail("内容不能超过300字");
      }
      if (defenseVoiceService.save(defenseVoice)) {
          CompletableFuture.runAsync(() -> {
            String description = req.getContent();
            var keyword = nlpUtil.countSingleKeyword(description, 10);
            if(keyword.isEmpty()){
              return;
            }
            wordService.addKeyword(keyword, defenseVoice.getId());
          }, threadPoolTaskExecutor);
          return Result.success();
      }
      return Result.fail();
  }

    @GetMapping("/list")
    @Operation(summary = "前台心声列表")
    public Result<PageInfo<VoiceFrontPageResp>> list(
            @RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize,
            @RequestParam(value = "key", required = false, defaultValue = "") String key,
            @RequestParam(value = "type", required = false, defaultValue = "-1") @Schema(description = "类型：-1全部，1我的心声") Integer type
    ) {
          var page = defenseVoiceService.lambdaQuery()
            .like(StringUtils.isNotBlank(key),  DefenseVoice::getAuthorName, key)
            .or().like(StringUtils.isNotBlank(key),  DefenseVoice::getIdentity, key)
            .eq(DefenseVoice::getStatus, 1)
            .eq(DefenseVoice::getIsDeleted, 0)
            .eq(type != -1, DefenseVoice::getCreateBy, StpUtil.getLoginIdAsString())
            .orderByDesc(DefenseVoice::getIsFeatured)
            .orderByDesc(DefenseVoice::getLikesCount)
            .orderByDesc(DefenseVoice::getGmtCreate)
            .page(new Page<>(pageNum, pageSize))
            .convert(VoiceFrontPageResp::fromDefenseVoice);
        return Result.page(page);
    }
    @GetMapping("/admin/list")
    @Operation(summary = "后台心声列表")
    public Result<PageInfo<VoicePageResp>> adminList(
            @RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize,
            @RequestParam(value = "key", required = false, defaultValue = "") String key,
            @RequestParam(value = "region", required = false, defaultValue = "") String region,
            @RequestParam(value = "identity", required = false, defaultValue = "") String identity
    ) {
        var page = defenseVoiceService.lambdaQuery()
                .like(StringUtils.isNotBlank(key),  DefenseVoice::getAuthorName, key)
                .or().like(StringUtils.isNotBlank(key),  DefenseVoice::getIdentity, key)
                .eq(DefenseVoice::getIsDeleted, 0)
                .like(StringUtils.isNotBlank(region),  DefenseVoice::getRegion, region)
                .like(StringUtils.isNotBlank(identity),  DefenseVoice::getIdentity, identity)
                .orderByDesc(DefenseVoice::getIsFeatured)
                .orderByDesc(DefenseVoice::getLikesCount)
                .orderByDesc(DefenseVoice::getGmtCreate)
                .page(new Page<>(pageNum, pageSize))
                .convert(VoicePageResp::fromDefenseVoice);
        return Result.page(page);
    }
    @GetMapping("/{id}")
    @Operation(summary = "前台心声详情")
    public Result<VoiceDetailResp> getVoice(@PathVariable("id") Integer id){
      var data = defenseVoiceService.getById(id);
      if(data == null){
        return Result.fail("心声不存在");
      }
      return Result.success(VoiceDetailResp.fromDefenseVoice(data));
    }
    @GetMapping("/comment")
    @Operation(summary = "前台心声评论列表")
    public Result<PageInfo<VoiceCommentResp>> comment(
            @RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize,
            @RequestParam @Schema(description = "心声id") Long voiceId
    ) {
        return Result.page(voiceCommentService.listComment(voiceId, pageNum, pageSize));
    }
    @PostMapping("/like")
    @Operation(summary = "前台心声点赞")
    public Result<Void> like(@RequestBody Likes req) {
        req.setUserId(StpUtil.getLoginIdAsLong());
        Likes count = likesService.lambdaQuery()
                .select(Likes::getId)
                .eq(Likes::getUserId, req.getUserId())
                .eq(Likes::getTargetId, req.getTargetId())
                .eq(Likes::getTargetType, req.getTargetType())
                .one();
        if(count != null){
            likesService.removeById(count.getId());
            return Result.success();
        }
        if(likesService.save(req)){
            defenseVoiceService.lambdaUpdate()
                    .eq(DefenseVoice::getId, req.getTargetId())
                    .setSql("likes_count = likes_count + 1")
                    .update();
            return Result.success();
        }
        return Result.fail();
    }
    @GetMapping("/likes")
    @Operation(summary = "前台心声点赞列表")
    public Result<List<Long>> getLikes(@RequestParam(required = false,  defaultValue = "-1L")
      @Schema(description = "心声id,传该值就表示获取该心声的子评论点赞，不传就是获取全部心声的点赞列表)")
                                           Long voiceId) {
      return Result.success(defenseVoiceService.getLikes(voiceId));
    }
    @PostMapping("/comment")
    @Operation(summary = "前台心声评论")
    public Result<Void> comment(@RequestBody @Validated CommentReq req) {
        defenseVoiceService.comment(req);
        return Result.success();
    }
    @DeleteMapping("/{id}/{type}")
    @Operation(summary = "后台删除接口", description = "type=1删除心声，type=2删除评论")
    public Result<Void> delete(@PathVariable("id") Integer id, @PathVariable("type") Integer type) {
        if (type == 1) {
            if (defenseVoiceService.removeById(id)) {
                voiceCommentService.lambdaUpdate()
                        .eq(VoiceComment::getVoiceId, id)
                        .remove();
                wordService.lambdaUpdate()
                        .eq(Word::getOrigin, id)
                        .remove();
                return Result.success();
            }
        } else if (type == 2 && voiceCommentService.removeById(id)) {
                voiceCommentService.lambdaUpdate()
                        .eq(VoiceComment::getParentId, id)
                        .remove();
                return Result.success();
            }
        return Result.fail();
    }
}
