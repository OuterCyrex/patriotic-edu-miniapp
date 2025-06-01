package cn.org.shelly.edu.utils;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.org.shelly.edu.constants.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Redis工具类
 *
 * @author HeXin
 * @date 2024/04/26
 */
@Component
@SuppressWarnings({"unchecked","unused"})
public class RedisUtil {

    private final RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public RedisUtil(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 给一个指定的 key 值附加过期时间
     *
     * @param key  键
     * @param time 值
     * @return 是否设置成功
     */
    public boolean expire(String key, long time) {
        return Boolean.TRUE.equals(redisTemplate.expire(key, time, TimeUnit.SECONDS));
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键
     * @return 过期时间
     */
    public long getTime(String key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire != null ? expire : 0L;
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键
     * @return 是否已经过期
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 移除指定key 的过期时间
     *
     * @param key 键
     * @return 是否移除成功
     */
    public boolean persist(String key) {
        return Boolean.TRUE.equals(redisTemplate.boundValueOps(key).persist());
    }

    //- - - - - - - - - - - - - - - - - - - - -  String类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 根据key获取值
     *
     * @param key 键
     * @return 值
     */
    public Object getObject(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取
     *
     * @param key 键
     * @return {@link String}
     */
    public String getString(String key){
        Object value = getObject(key);
        return value == null ? null : value.toString();
    }

    /**
     * 按类型获取值
     *
     * @param key  键
     * @param type 类型
     * @return {@link E}
     */
    @SuppressWarnings("rawtypes")
    public <E> E get(String key,Class<E> type){
        Object rawValue = getObject(key);
        if (rawValue == null) {
            return null;
        }
        // 基础类型
        if (type == String.class) {
            // 对于字符串，直接返回 JSON 字符串
            return type.cast(rawValue);
        } else if (type == Integer.class) {
            // 对于整数，使用 parseInteger 方法
            return type.cast(Integer.valueOf(rawValue.toString()));
        } else if (type == Long.class) {
            return type.cast(Long.parseLong(rawValue.toString()));
        } else if (type == Double.class) {
            return type.cast(Double.parseDouble(rawValue.toString()));
        } else if (type == Float.class) {
            return type.cast(Float.parseFloat(rawValue.toString()));
        } else if (type == Boolean.class) {
            return type.cast(Boolean.parseBoolean(rawValue.toString()));
        } else if (type == Character.class) {
            return type.cast(rawValue.toString().charAt(0));
        } else if (type == Byte.class) {
            return type.cast(Byte.parseByte(rawValue.toString()));
        } else if (type.isEnum()) {
            return (E) Enum.valueOf((Class<? extends Enum>) type, rawValue.toString());
        } else if (type == LocalDate.class) {
            return type.cast(LocalDate.parse(rawValue.toString()));
        } else if (type == LocalDateTime.class) {
            return type.cast(LocalDateTime.parse(rawValue.toString()));
        }
        // 其他类型
        String json = JSONUtil.toJsonStr(rawValue);
        return getValue(type,json);
    }

    /**
     * 获取值
     *
     * @param type 类型
     * @param json json格式
     * @return {@link E}
     */
    public  <E> E getValue(Class<E> type, String json) {
        if (type.isArray()) {
            // 对于数组类型，调用 getList 方法转换为 List
            List<E> list = (List<E>) getListOfJson(json, type.getComponentType());
            // 将 List 转换为数组
            return (E) list.toArray((Object[]) Array.newInstance(type.getComponentType(), list.size()));
        } else if(type.isAssignableFrom(List.class)){
            return (E) getListOfJson(json,type);
        }else if (type.isAssignableFrom(Set.class)) {
            // 对于 Set 类型，调用 toSet 方法转换为 Set
            return (E) getSet(json, Object.class); // 请根据实际情况替换 Object.class
        } else {
            // 对于其他对象类型，使用 toBean 方法
            return JSONUtil.toBean(json, type);
        }
    }

    /**
     * 获取 Set 集合
     *
     * @param json        JSON
     * @param elementType 元素类型
     * @return {@link Set}<{@link E}>
     */
    public  <E> Set<E> getSet(String json, Class<E> elementType) {
        Set<E> set = new HashSet<>();
        JSONArray jsonArray = JSONUtil.parseArray(json);
        for (int i = 0; i < jsonArray.size(); i++) {
            set.add(JSONUtil.toBean(jsonArray.getJSONObject(i), elementType));
        }
        return set;
    }

    /**
     * 获取列表
     *
     * @param json JSON
     * @param type 类型
     * @return {@link List}<{@link E}>
     */
    public  <E>  List<E> getListOfJson(String json, Class<E> type) {
        return JSONUtil.toList(json,type);
    }

    public <E> List<E> getListOfKey(String key,Class<E> type) {
        String jsonString = getString(key);
        if (jsonString == null) {
            return Collections.emptyList();
        }
        return getListOfJson(jsonString, type);
    }

    /**
     * 刷新缓存
     *
     * @param key   键
     * @param value 价值
     */
    public void refresh(String key,Object value) {
        // 清除缓存
        remove(key);
        // 新加缓存
        set(key,value);
    }

    /**
     * 刷新缓存
     *
     * @param constants 常数
     * @param value     价值
     */
    public void refresh(RedisConstants constants, Object value) {
        // 清除缓存
        remove(constants.getKey());
        // 新增缓存
        set(constants,value);
    }

    /**
     * 刷新缓存
     *
     * @param constants 常数
     * @param key       键
     * @param value     价值
     */
    public void refresh(RedisConstants constants,String key,Object value) {
        // 清除缓存
        remove(constants.getKey() + key);
        // 新增缓存
        set(constants,key,value);
    }

    /**
     * 刷新缓存
     *
     * @param key      键
     * @param value    价值
     * @param time     时间
     * @param timeUnit 时间单位
     */
    public void refresh(String key, Object value, long time, TimeUnit timeUnit) {
        // 清除缓存
        remove(key);
        // 新增缓存
        set(key, value,time,timeUnit);
    }

    /**
     * 将值放入缓存
     *
     * @param key   键   键
     * @param value 值
     */
    public void set(String key, Object value) {
        if (isPrimitiveOrWrapper(value.getClass())) {
            // 基本类型直接存
            redisTemplate.opsForValue().set(key, value);
        } else {
            redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value));
        }
    }

    /**
     * 判断一个类是否是基本类型或其包装类
     *
     * @param clazz 类
     * @return 是否是基本类型或其包装类
     */
    private boolean isPrimitiveOrWrapper(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz == Integer.class ||
                clazz == Long.class ||
                clazz == Double.class ||
                clazz == Float.class ||
                clazz == Boolean.class ||
                clazz == Byte.class ||
                clazz == Character.class;
    }

    /**
     * 将值放入缓存并设置时间及时间单位
     *
     * @param key      键   键
     * @param value    值
     * @param time     时间(秒) -1为无期限
     * @param timeUnit 时间单位
     */
    public void set(String key, Object value, long time, TimeUnit timeUnit) {
        if(!isPrimitiveOrWrapper(value.getClass())) {
            value = JSONUtil.toJsonStr(value);
        }
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 设置
     *
     * @param redisConstants Redis 常量
     * @param key            键
     * @param value          价值
     */
    public void set(RedisConstants redisConstants, String key, Object value) {
        if(!isPrimitiveOrWrapper(value.getClass())) {
            value = JSONUtil.toJsonStr(value);
        }
        Long ttl = redisConstants.getTtl();
        if (ttl > 0) {
            redisTemplate.opsForValue().set(redisConstants.getKey() + key, value, ttl, redisConstants.getTimeUnit());
        } else {
            redisTemplate.opsForValue().set(redisConstants.getKey() + key, value);
        }
    }

    /**
     * 集
     *
     * @param redisConstants Redis 常量
     * @param value          值
     */
    public void set(RedisConstants redisConstants,Object value) {
        if(!isPrimitiveOrWrapper(value.getClass())) {
            value = JSONUtil.toJsonStr(value);
        }
        Long ttl = redisConstants.getTtl();
        if (ttl > 0) {
            redisTemplate.opsForValue().set(redisConstants.getKey(), value, ttl, redisConstants.getTimeUnit());
        } else {
            redisTemplate.opsForValue().set(redisConstants.getKey(), value);
        }
    }

    /**
     * 清理缓存
     *
     * @param key 键
     * @return boolean 是否移除成功
     */
    public boolean remove(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 批量添加 key (重复的键会覆盖)
     *
     * @param keyAndValue 键和值
     */
    public void batchSet(Map<String, String> keyAndValue) {
        redisTemplate.opsForValue().multiSet(keyAndValue);
    }

    /**
     * 批量添加 key-value 只有在键不存在时,才添加
     * map 中只要有一个key存在,则全部不添加
     *
     * @param keyAndValue 键和值
     */
    public void batchSetIfAbsent(Map<String, String> keyAndValue) {
        redisTemplate.opsForValue().multiSetIfAbsent(keyAndValue);
    }

    /**
     * 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是长整型 ,将报错
     *
     * @param key    键
     * @param number 数
     * @return {@link Long}
     */
    public Long increment(String key, long number) {
        return redisTemplate.opsForValue().increment(key, number);
    }

    /**
     * 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是 纯数字 ,将报错
     *
     * @param key    键
     * @param number 数
     * @return {@link Double}
     */
    public Double increment(String key, double number) {
        return redisTemplate.opsForValue().increment(key, number);
    }

    //- - - - - - - - - - - - - - - - - - - - -  set类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 将数据放入set缓存
     *
     * @param key 键
     */
    public void sSet(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 获取变量中的值
     *
     * @param key 键
     * @return {@link Set}<{@link Object}>
     */
    public Set<Object> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取变量中指定个数的元素
     *
     * @param key   键   键
     * @param count 值
     */
    public void randomMembers(String key, long count) {
        redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取变量中的元素
     *
     * @param key 键
     * @return object
     */
    public Object randomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 弹出变量中的元素
     *
     * @param key 键
     * @return {@link Object}
     */
    public Object pop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 获取变量中值的长度
     *
     * @param key 键
     * @return long
     */
    public Long size(String key) {
        Long size = redisTemplate.opsForSet().size(key);
        return size != null ? size : 0L;
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    /**
     * 检查给定的元素是否在变量中。
     *
     * @param key 键
     * @param obj 元素对象
     * @return 是否在变量中
     */
    public boolean isMember(String key, Object obj) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, obj));
    }

    /**
     * 转移变量的元素值到目的变量。
     *
     * @param key     键     键
     * @param value   元素对象
     * @param destKey 元素对象
     * @return 是否转移成功
     */
    public boolean move(String key, Object value, String destKey) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().move(key, value, destKey));
    }

    /**
     * 批量移除set缓存中元素
     *
     * @param key    键    键
     * @param values 值
     */
    public void remove(String key, Object... values) {
        redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 通过给定的key求2个set变量的差值
     *
     * @param key     键     键
     * @param destKey 键
     * @return 计算出的差值
     */
    public Set<Object> difference(String key, String destKey) {
        return redisTemplate.opsForSet().difference(key, destKey);
    }

    /**
     * 增加 ZSet 中成员的分数，并设置过期时间
     * @param key ZSet 的键
     * @param value 成员
     * @param score 分数
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     */
    //- - - - - - - - - - - - - - - - - - - - -  zSet类型 - - - - - - - - - - - - - - - - - - - -
    public <T> void incrZet(String key, T value, Integer score, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForZSet().incrementScore(key, value, score.doubleValue());
        // 设置过期时间
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 从 ZSet 中获取指定成员的分数
     *
     * @param key Redis ZSet 的 key
     * @param member ZSet 中的成员
     * @return 成员的分数，如果成员不存在，则返回 null
     */
    public Double getScoreFromZSet(String key, String member) {
        return redisTemplate.opsForZSet().score(key, member);
    }



    /**
     * 向 ZSet 中添加成员，并设置过期时间
     * @param key ZSet 的键
     * @param value 成员
     * @param score 成员的分数
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     */
    public <T> void addZSet(String key, T value, Double score, long timeout, TimeUnit timeUnit) {
        // 向 ZSet 中添加成员
        redisTemplate.opsForZSet().add(key, value, score);

        // 设置过期时间
        redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 批量获取有序集合中多个成员的分数
     * @param key Redis 键
     * @param members 成员列表
     * @return 成员和分数的映射
     */
    public Map<Long, Double> getScoreFromZSetBatch(String key, List<Long> members) {
        // 将 Long 类型的成员转换为 String
        List<String> memberStrings = members.stream()
                .map(String::valueOf)
                .toList();
        // 创建一个空的 Map 来存储结果
        Map<Long, Double> resultMap = new HashMap<>();
        // 遍历每个成员，逐个获取分数
        for (String member : memberStrings) {
            Double score = redisTemplate.opsForZSet().score(key, member);
            // 将成员和对应的分数放入 Map 中
            resultMap.put(Long.valueOf(member), score);
        }
        return resultMap;
    }

    /**
     * 从 Redis 有序集合中获取所有的成员及其分数。
     *
     * @param key Redis 有序集合的键
     * @return 成员及其分数的映射
     */
    public Map<String, Double> getAllZSetScores(String key) {
        // 获取所有的成员及其分数
        Set<ZSetOperations.TypedTuple<Object>> tuples = redisTemplate.opsForZSet().rangeWithScores(key, 0, -1);

        // 如果 tuples 为 null，返回空 Map
        if (tuples == null) {
            return Collections.emptyMap();
        }

        // 将成员及其分数转换为 Map
        return tuples.stream()
                .collect(Collectors.toMap(
                        tuple -> (String) tuple.getValue(), // 进行类型转换
                        ZSetOperations.TypedTuple::getScore
                ));
    }

    //- - - - - - - - - - - - - - - - - - - - -  hash类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 加入缓存
     *
     * @param key 键
     * @param map 键
     */
    public void add(String key, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public void add(String key,String hashKey,Object value) {
        // 序列化 value
        if(!isPrimitiveOrWrapper(value.getClass())) {
            value = JSONUtil.toJsonStr(value);
        }
        redisTemplate.opsForHash().put(key,hashKey,value);
    }

    /**
     * 获取 key 下的 所有  hashkey 和 value
     *
     * @param key 键
     * @return 键值对
     */
    public Map<Object, Object> getHashEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 验证指定 key 下 有没有指定的 hashkey
     *
     * @param key     键
     * @param hashKey 哈希键
     * @return 是否有指定的键
     */
    public boolean hashKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取指定key的值string
     *
     * @param key 键  键
     * @param key2 键2 键
     * @return 字符串
     */
    public String getMapString(String key, String key2) {
        return Objects.requireNonNull(redisTemplate.opsForHash().get(key, key2)).toString();
    }

    /**
     * 获取指定的值Int
     *
     * @param key 键
     * @param key2 键2
     * @return 整形变量int
     */
    public Integer getMapInt(String key, String key2) {
        return (Integer) redisTemplate.opsForHash().get(key, key2);
    }

    /**
     * 弹出元素并删除
     *
     * @param key 键
     * @return 弹出的元素
     */
    public String popValue(String key) {
        return Objects.requireNonNull(redisTemplate.opsForSet().pop(key)).toString();
    }

    /**
     * 删除指定 hash 的 HashKey
     *
     * @param key      键
     * @param hashKeys 哈希键
     * @return 删除成功的 数量
     */
    public Long delete(String key, String[] hashKeys) {
        return redisTemplate.opsForHash().delete(key, (Object[]) hashKeys);
    }

    /**
     * 给指定 hash 的 hashkey 做增减操作
     *
     * @param key     键
     * @param hashKey 哈希键
     * @param number  数
     * @return {@link Long}
     */
    public Long increment(String key, String hashKey, long number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 增加
     * 给指定 hash 的 hashkey 做增减操作
     *
     * @param key     键
     * @param hashKey 哈希键
     * @param number  数
     * @return {@link Double}
     */
    public Double increment(String key, String hashKey, Double number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 哈希键
     * 获取 key 下的 所有 hashkey 字段
     *
     * @param key 键
     * @return hashkey 字段的set集合
     */
    public Set<Object> hashKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 哈希大小
     * 获取指定 hash 下面的 键值对 数量
     *
     * @param key 键
     * @return 键值对数量 long
     */
    public Long hashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }


    /**
     * 批量获取哈希表中多个字段的值
     * @param key Redis 键
     * @param fields 要获取的字段
     * @return 字段和值的映射
     */
    public Map<Long, Integer> getMapIntBatch(String key, List<Long> fields) {
        // 将 Long 类型的字段转换为 String 类型，并从 Redis 中批量获取对应的值
        List<String> stringFields = fields.stream()
                .map(String::valueOf)
                .toList();
        List<Object> results = redisTemplate.opsForHash().multiGet(key, new ArrayList<>(stringFields));
        // 将字段和结果组合成一个映射
        return IntStream.range(0, fields.size())
                .boxed()
                .collect(Collectors.toMap(
                        fields::get,
                        i -> results.get(i) != null ? (Integer) results.get(i) : -1   // 处理可能的 null 值
                ));
    }



    //- - - - - - - - - - - - - - - - - - - - -  list类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 在变量左边添加元素值
     *
     * @param key   键
     * @param value 价值
     */
    public void leftPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 获取集合指定位置的值。
     *
     * @param key   键
     * @param index 指数
     * @return 值
     */
    public Object index(String key, long index) {
        return redisTemplate.opsForList().index("list", 1);
    }

    /**
     * 获取指定区间的值。
     *
     * @param key   键
     * @param start 区间开始标志
     * @param end   区间结束标志
     * @return 指定区间的List集合
     */
    public List<Object> range(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 把最后一个参数值放到指定集合的第一个出现中间参数的前面，
     * 如果中间参数值存在的话。
     *
     * @param key   键
     * @param value 值
     * @param pivot 支点
     */
    public void leftPush(String key, String pivot, Object value) {
        redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    /**
     * 向左边批量添加参数元素。
     *
     * @param key    键
     * @param values 数量可变值元素
     */
    public void leftPushAll(String key, List<String> values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 向集合最右边添加元素。
     *
     * @param key   键
     * @param value 值
     */
    public void leftPushAll(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 向左边批量添加参数元素。
     *
     * @param key    键
     * @param values 值
     */
    public void rightPushAll(String key, String[] values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 向已存在的集合中添加元素。
     *
     * @param key   键
     * @param value 值
     */
    public void rightPushIfPresent(String key, Object value) {
        redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 获取存储在key处的列表的大小。
     *
     * @param key 键
     * @return 列表大小
     */
    public long listLength(String key) {
        Long size = redisTemplate.opsForList().size(key);
        return size != null ? size : 0;
    }

    /**
     * 移除集合中的左边第一个元素。
     *
     * @param key 键
     */
    public void leftPop(String key) {
        redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。
     *
     * @param key 键
     */
    public void leftPop(String key, long timeout, TimeUnit unit) {
        redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除集合中右边的元素。
     *
     * @param key 键
     */
    public void rightPop(String key) {
        redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。
     *
     * @param key 键
     */
    public void rightPop(String key, long timeout, TimeUnit unit) {
        redisTemplate.opsForList().rightPop(key, timeout, unit);
    }
}

