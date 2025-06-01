package cn.org.shelly.edu.controller;

import cn.org.shelly.edu.common.PageInfo;
import cn.org.shelly.edu.common.Result;
import cn.org.shelly.edu.model.po.StopWord;
import cn.org.shelly.edu.model.po.Word;
import cn.org.shelly.edu.model.resp.StopWordResp;
import cn.org.shelly.edu.model.resp.WordFrequencyResp;
import cn.org.shelly.edu.service.StopWordService;
import cn.org.shelly.edu.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 词云模块
 * @author shelly
 */
@RestController
@RequestMapping("/word")
@Slf4j
@Tag(name = "词云模块")
@RequiredArgsConstructor
public class WordController {

    private final StopWordService stopWordService;

    private final WordService wordFrequencyService;
    @GetMapping("/stop/list")
    @Operation(summary = "获取停用词列表")
    public Result<PageInfo<StopWordResp>> getStopList(
      @RequestParam(required = false, defaultValue = "1") Long pageNum,
      @RequestParam(required = false, defaultValue = "10") Long pageSize,
      @RequestParam(required = false, defaultValue = "") String key
    ) {
      return Result.page(stopWordService.selectStopWordList(pageNum, pageSize, key));
    }

    @PostMapping("/stop/add")
    @Operation(summary = "添加停用词")
    public Result<String> addStopWord(@RequestBody StopWordResp req) {
      Long count = stopWordService.lambdaQuery()
        .eq(StopWord::getContent, req.getContent())
        .count();
      if(count > 0){
        return Result.fail("该停用词已存在");
      }
      stopWordService.save(StopWord.toEntity(req));
      return Result.fail("添加失败");
    }

    @PutMapping("/stop/update")
    @Operation(summary = "修改停用词")
    public Result<String> updateStopWord(@RequestBody StopWordResp req) {
      Long count = stopWordService.lambdaQuery()
        .eq(StopWord::getContent, req.getContent())
        .ne(StopWord::getId, req.getId())
        .count();
      if(count > 0){
        return Result.fail("该停用词已存在");
      }
      if (stopWordService.updateById(StopWord.toEntity(req))) {
        return Result.success("修改成功");
      }
      return Result.fail("修改失败");
    }

    @DeleteMapping("/stop/delete")
    @Operation(summary = "删除停用词")
    public Result<String> deleteStopWord(@RequestParam Long id) {
      if (stopWordService.removeById(id)) {
        return Result.success("删除成功");
      }
      return Result.fail("删除失败");
    }
    @GetMapping
    @Operation(summary = "获取词频列表")
    public Result<PageInfo<WordFrequencyResp>> getList(
      @RequestParam(required = false, defaultValue = "1") Long pageNum,
      @RequestParam(required = false, defaultValue = "10") Long pageSize,
      @RequestParam(required = false, defaultValue = "") String key
    ) {
      return Result.page(wordFrequencyService.wordList(pageNum, pageSize, key));
    }
    @PostMapping
    @Operation(summary = "超管主动添加词汇")
    public Result<String> addWord(@RequestBody WordFrequencyResp req) {
      Long count = wordFrequencyService.lambdaQuery()
        .eq(Word::getWord, req.getContent())
        .count();
      if(count > 0){
        return Result.fail("该词已存在");
      }
      Word w = Word.toEntity(req);
      w.setStatus(1);
      wordFrequencyService.save(w);
      return Result.success("添加成功");
    }
    @PutMapping
    @Operation(summary = "修改词汇")
    public Result<String> updateWord(@RequestBody WordFrequencyResp req) {
      Long count = wordFrequencyService.lambdaQuery()
        .eq(Word::getWord, req.getContent())
        .ne(Word::getId, req.getId())
        .count();
      if(count > 0){
        return Result.fail("该词已存在");
      }
      if (wordFrequencyService.updateById(Word.toEntity(req))) {
        return Result.success("修改成功");
      }
      return Result.fail("修改失败");
    }

    @DeleteMapping
    @Operation(summary = "删除词汇")
    public Result<String> deleteWord(@RequestParam Long id) {
      if (wordFrequencyService.removeById(id)) {
        return Result.success("删除成功");
      }
      return Result.fail("删除失败");
    }

    @GetMapping("/top/{x}")
    @Operation(summary = "获取top(x)词汇", description = "用来做词云的接口")
    public Result<List<WordFrequencyResp>> getTopX(@PathVariable Integer x) {
      return Result.success(
        wordFrequencyService.lambdaQuery()
          .orderByDesc(Word::getCount)
          .eq(Word::getIsDeleted, 0)
          .eq(Word::getStatus, 1)
          .last("limit " + x)
          .list()
          .stream()
          .map(WordFrequencyResp::toResp)
          .toList()
      );
    }
}
