package cn.org.shelly.edu.service.impl;
import cn.org.shelly.edu.mapper.WordMapper;
import cn.org.shelly.edu.model.po.Word;
import cn.org.shelly.edu.model.resp.WordFrequencyResp;
import cn.org.shelly.edu.service.WordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author Shelly6
* @description 针对表【word(词云统计表)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Word>
    implements WordService {

  @Override
  public IPage<WordFrequencyResp> wordList(Long pageNum, Long pageSize, String key) {
    return lambdaQuery()
      .select(Word::getId, Word::getWord, Word::getCount, Word::getStatus)
      .like(StringUtils.isNotBlank(key), Word::getWord, key)
      .eq(Word::getIsDeleted, 0)
      .orderByDesc(Word::getCount)
      .page(new Page<>(pageNum, pageSize))
        .convert(WordFrequencyResp::toResp);
  }

  @Override
  public void addKeyword(Map<String, Integer> keyword) {
    // 获取所有与给定类型匹配的 WordFrequency 列表
    List<Word> existingList = lambdaQuery()
      .in(Word::getWord, keyword.keySet())
      .eq(Word::getIsDeleted, 0)
      .list();

    // 将已有的 WordFrequency 放到一个 Map 中，方便后续更新
    Map<String, Word> existingMap = existingList.stream()
      .collect(Collectors.toMap(Word::getWord, wf -> wf));
    // 准备批量更新的列表
    List<Word> updateList = new ArrayList<>();
    // 遍历传入的关键词及其频率
    for (Map.Entry<String, Integer> entry : keyword.entrySet()) {
      String content = entry.getKey();
      Integer frequency = entry.getValue();
      // 如果这个关键词已经存在，就更新其频率
      if (existingMap.containsKey(content)) {
        Word existing = existingMap.get(content);
        existing.setCount(existing.getCount() + frequency);
        updateList.add(existing);
      } else {
        Word wordFrequency = new Word();
        wordFrequency.setWord(content);
        wordFrequency.setCount(frequency);
        wordFrequency.setStatus(1);
        updateList.add(wordFrequency);
      }
    }
    // 批量更新
    if (!updateList.isEmpty()) {
      saveOrUpdateBatch(updateList);
    }
  }
}




