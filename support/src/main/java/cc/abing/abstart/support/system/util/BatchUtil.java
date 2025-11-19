package cc.abing.abstart.support.system.util;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class BatchUtil {


    /**
     * // 批量保存用户数据示例
     * List<User> users = Arrays.asList(
     *     new User("张三", "zhangsan@example.com"),
     *     new User("李四", "lisi@example.com"),
     *     // ... 更多用户数据
     * );
     *
     * // 假设数据库批量插入操作
     * BatchUtil.saveOrUpdateBatch(users, userList -> {
     *     // 执行批量插入操作，每次处理100条记录
     *     userService.saveOrUpdateBatch(userList);
     * }, 100);
     */
    public static <T> void saveOrUpdateBatch(Collection<T> ts, Consumer<List<T>> consumer, Integer BATCH_SIZE){
        if (ObjectUtils.isEmpty(ts)){
            return;
        }
        List<T> tempList = new ArrayList<>(BATCH_SIZE);
        for (T t : ts) {
            tempList.add(t);
            if (tempList.size()>=BATCH_SIZE){
                consumer.accept(tempList);
                tempList.clear();
            }
        }
        if (!tempList.isEmpty()){
            consumer.accept(tempList);
        }
    }

    /**
     * // 批量查询，每次处理50个ID
     * List<UserDetail> userDetails = BatchUtil.selectBatch(userIds, ids -> {
     *     // 根据ID列表查询用户详情，每次最多处理50个
     *     return userDetailService.queryByIds(ids);
     * }, 50);
     */
    public static <T,S> List<S> selectBatch(Collection<T> ts, Function<Collection<T>,List<S>> function, Integer BATCH_SIZE){
        List<S> result = new ArrayList<>();
        if (ObjectUtils.isEmpty(ts)){
            return result;
        }
        List<T> tempList = new ArrayList<>(BATCH_SIZE);
        for (T t : ts) {
            tempList.add(t);
            if (tempList.size()>=BATCH_SIZE){
                result.addAll(function.apply(tempList));
                tempList.clear();
            }
        }
        if (!tempList.isEmpty()){
            result.addAll(function.apply(tempList));
        }
        return result;
    }


}