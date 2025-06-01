package cn.org.shelly.edu.service;

import cn.org.shelly.edu.model.po.Word;
import cn.org.shelly.edu.model.resp.WordFrequencyResp;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author Shelly6
* @description 针对表【word(词云统计表)】的数据库操作Service
* @createDate 2025-05-31 15:29:26
*/
public interface WordService extends IService<Word> {

  IPage<WordFrequencyResp> wordList(Long pageNum, Long pageSize, String key);

  void addKeyword(Map<String, Integer> keyword);
}
