package cn.org.shelly.edu.controller;

import cn.org.shelly.edu.common.PageInfo;
import cn.org.shelly.edu.common.Result;
import cn.org.shelly.edu.model.po.KnowledgeQuestion;
import cn.org.shelly.edu.model.po.ScenarioQuestion;
import cn.org.shelly.edu.model.req.KnowledgeQuestionReq;
import cn.org.shelly.edu.model.req.ScenarioQuestionReq;
import cn.org.shelly.edu.model.resp.KnowledgeQuestionResp;
import cn.org.shelly.edu.model.resp.KnowledgeResultResp;
import cn.org.shelly.edu.model.resp.ScenarioQuestionResp;
import cn.org.shelly.edu.model.resp.ScenarioResultResp;
import cn.org.shelly.edu.service.KnowledgeQuestionService;
import cn.org.shelly.edu.service.ScenarioQuestionService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 问题控制器
 * @author shelly
 */
@RequestMapping("/question")
@RestController
@RequiredArgsConstructor
@Tag(name = "问题管理")
public class QuestionController {
    private final KnowledgeQuestionService questionService;
    private final ScenarioQuestionService  scenarioQuestionService;
    //-----------------------------------------知识---------------------------------------------------------------------------
    @GetMapping("/knowledge")
    @Operation(summary = "获取随机10道知识性问题")
    public Result<List<KnowledgeQuestionResp>> getQuiz() {
        return Result.success(questionService.getQuiz());
    }
    @PostMapping("/knowledgeAns")
    @Operation(summary = "提交知识问答题目答案")
    public Result<KnowledgeResultResp> submit(Long questionId, Integer answer) {
        return Result.success(questionService.submit(questionId, answer));
    }
    @PostMapping("/knowledge")
    @Operation(summary = "添加知识题目")
    public Result<Void> addKnowledgeQuiz(KnowledgeQuestionReq knowledgeQuestionReq) {
        questionService.addKnowledgeQuiz(knowledgeQuestionReq);
        return Result.success();
    }
    @PutMapping("/knowledge")
    @Operation(summary = "修改知识性题目")
    public Result<Void> updateQuiz(KnowledgeQuestionReq knowledgeQuestionReq) {
        questionService.updateScenarioQuiz(knowledgeQuestionReq);
        return Result.success();
    }
    @GetMapping("/knowledge/list")
    @Operation(summary = "后台获取知识题库")
    public Result<PageInfo<KnowledgeQuestion>> getKnowledgeQuiz(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0") Integer difficulty,
            @RequestParam(required = false, defaultValue = "") String keyword)
    {
        var page = questionService.lambdaQuery()
                .like(StringUtils.isNotBlank(keyword), KnowledgeQuestion::getQuestion, keyword)
                .orderByDesc(KnowledgeQuestion::getGmtModified)
                .eq(difficulty != 0 ,KnowledgeQuestion::getDifficulty, difficulty)
                .eq(KnowledgeQuestion::getIsDeleted, 0)
                .page(new Page<>(pageNum, pageSize));
        return Result.page(page);
    }

    //---- -------------------------------------------情景----------------------------------------------------------------------
    @GetMapping("/scenario")
    @Operation(summary = "获取随机10道情景性问题")
    public Result<List<ScenarioQuestionResp>> getScenarioQuiz() {
        return Result.success(scenarioQuestionService.getScenarioQuiz());
    }
    @PostMapping("/scenarioAns")
    @Operation(summary = "提交情景性题目答案")
    public Result<ScenarioResultResp> submitScenarioQuiz(Long questionId, Integer answer) {
        return Result.success(scenarioQuestionService.submit(questionId, answer));
    }
    @PostMapping("/scenario")
    @Operation(summary = "添加情景性题目")
     public Result<Void> addScenarioQuiz(ScenarioQuestionReq scenarioQuestionReq) {
        scenarioQuestionService.addScenarioQuiz(scenarioQuestionReq);
        return Result.success();
    }
    @PutMapping("/scenario")
    @Operation(summary = "修改情景性题目")
    public Result<Void> updateScenarioQuiz(ScenarioQuestionReq scenarioQuestionReq) {
        scenarioQuestionService.updateScenarioQuiz(scenarioQuestionReq);
        return Result.success();
    }
    @GetMapping("/scenario/list")
    @Operation(summary = "后台获取情景题库")
    public Result<PageInfo<ScenarioQuestion>> getScenarioQuiz(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String keyword)
    {
        var page = scenarioQuestionService.lambdaQuery()
                .like(StringUtils.isNotBlank(keyword), ScenarioQuestion::getQuestion, keyword)
                .eq(ScenarioQuestion::getStatus, 1)
                .eq(ScenarioQuestion::getIsDeleted, 0)
                .page(new Page<>(pageNum, pageSize));
        return Result.page(page);
    }
    //---- --------------------------------通用---------------------------------------------------------------------------------
    @DeleteMapping("/{id}/{type}")
    @Operation(summary = "删除题目",  description = "type=1:删除知识题目，type=2:删除情景题目")
    public Result<Void> deleteScenarioQuiz(@PathVariable Long id, @PathVariable Integer type) {
         if (type != 1 && type != 2) {
            return Result.fail("参数错误");
         }
         if(type == 1){
             questionService.removeById(id);
         }
         else{
             scenarioQuestionService.removeById(id);
         }
        return Result.success();
    }


}
