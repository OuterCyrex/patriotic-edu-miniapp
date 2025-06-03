package cn.org.shelly.edu.service;

import cn.org.shelly.edu.model.po.StopWord;
import cn.org.shelly.edu.model.resp.StopWordResp;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

public interface StopWordService extends IService<StopWord> {

  IPage<StopWordResp> selectStopWordList(Long pageNum, Long pageSize, String key);

    Set<String> getStopWords();
}
