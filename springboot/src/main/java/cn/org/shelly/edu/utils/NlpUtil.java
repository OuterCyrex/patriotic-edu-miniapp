package cn.org.shelly.edu.utils;

import cn.hutool.core.collection.CollUtil;
import cn.org.shelly.edu.constants.RedisConstants;
import cn.org.shelly.edu.model.dto.BinaryGroup;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

/**
 * nlp工具
 * @author shelly
 */
@Slf4j
@Component
public class NlpUtil {
    /**
     * 普通字母
     */
    final String LETTER_REGULAR = "[a-z]+";
    final String NON_CHINESE_CHARACTERS = "[^\\u4e00-\\u9fa5]";


    private  RedisUtil redisUtil;
    private NlpUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * count关键字
     *
     * @param inputs 输入
     * @param limit  限度
     * @return @return {@link List }<{@link BinaryGroup }<{@link String }, {@link Integer }>>
     */
    public  List<BinaryGroup<String, Integer>> countKeyword(List<String> inputs, int limit) {
        if (CollUtil.isEmpty(inputs)) {
            return Collections.emptyList();
        }
        return counting(inputs, limit);
    }
    public Map<String, Integer> countSingleKeyword(String input, int limit) {
        if (StringUtils.isBlank(input)) {
            return Collections.emptyMap();
        }
        return counting(input, limit);
    }

    private Map<String, Integer> counting(String input, int limit) {
        // 获取停词表
        List<String> stopWords = redisUtil.getListOfKey(RedisConstants.STOP_WORD_CONTENT.getKey(), String.class);
        Map<String, Integer> frequency = new HashMap<>(limit);
        var s = input.replaceAll(NON_CHINESE_CHARACTERS, " ");
        var keywords = HanLP.extractKeyword(s, limit);
        keywords.forEach(keyword -> {
            // 过滤掉停词表中的内容
            if(!stopWords.contains(keyword)) {
                frequency.put(keyword, frequency.getOrDefault(keyword, 0) + 1);
            }
        });
        return frequency;
    }

    /**
     * count关键字
     *
     * @param inputs     输入
     * @param limit      限度
     * @param conversion 转变
     * @return @return {@link List }<{@link BinaryGroup }<{@link String }, {@link Integer }>>
     * @date 2023/10/26
     * @author fate
     * @see List<T>
     * @see int
     * @see Function <T, String>
     * @see List<BinaryGroup<String, Integer>>
     */
    public  <T> List<BinaryGroup<String, Integer>> countKeyword(List<T> inputs, int limit, Function<T, String> conversion) {
        if (CollUtil.isEmpty(inputs)) {
            return Collections.emptyList();
        }
        var list = inputs.stream().filter(Objects::nonNull).map(conversion).filter(StringUtils::isNotBlank).toList();
        return counting(list, limit);
    }

    /**
     * count关键字
     *
     * @param condition 条件
     * @param inputs    输入
     * @param limit     限度
     * @return @return {@link List }<{@link BinaryGroup }<{@link String }, {@link Integer }>>
     * @date 2023/10/25
     * @author fate
     * @see Boolean
     * @see List<String>
     * @see int
     * @see List<BinaryGroup<String, Integer>>
     */
    public  List<BinaryGroup<String, Integer>> countKeyword(Boolean condition, List<String> inputs, int limit) {
        if (Boolean.TRUE.equals(condition) && CollUtil.isEmpty(inputs)) {
            return Collections.emptyList();
        }
        return counting(inputs, limit);
    }

    /**
     * 计数
     *
     * @param inputs 输入
     * @param limit  限度
     * @return @return {@link List }<{@link BinaryGroup }<{@link String }, {@link Integer }>>
     * @date 2023/10/25
     * @author fate
     * @see List<String>
     * @see int
     * @see List< BinaryGroup <String, Integer>>
     */
    private List<BinaryGroup<String, Integer>> counting(List<String> inputs, int limit) {
        // 获取停词表
        List<String> stopWords = redisUtil.getListOfKey(RedisConstants.STOP_WORD_CONTENT.getKey(), String.class);
        Map<String, Integer> frequency = new HashMap<>(limit);
        inputs.forEach(input -> {
            var s = input.replaceAll(NON_CHINESE_CHARACTERS, " ");
            var keywords = HanLP.extractKeyword(s, limit);
            keywords.forEach(keyword -> {
                // 过滤掉停词表中的内容
                if(!stopWords.contains(keyword)) {
                    frequency.put(keyword, frequency.getOrDefault(keyword, 0) + 1);
                }
            });
        });
        return frequency.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(limit)
                .map(it -> new BinaryGroup<String, Integer>(it.getKey(), it.getValue()))
                .toList();
    }
}
