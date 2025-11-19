package cc.abing.abstart.support.system.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 一个通用的列表差异比较工具类。
 * 用于找出两个列表之间需要新增和删除的元素。
 *
 * @param <T>  列表中元素的类型
 * @param <ID> 元素唯一标识符的类型
 */
public class ListDifferenceUtils<T, ID> {

    private final Function<T, ID> idExtractor;

    /**
     * 构造函数。
     *
     * @param idExtractor 一个函数，用于从元素 T 中提取唯一标识符 ID。
     *                    例如：IdentifyFeature::getId
     */
    public ListDifferenceUtils(Function<T, ID> idExtractor) {
        this.idExtractor = Objects.requireNonNull(idExtractor, "ID 提取函数不能为空");
    }

    /**
     * 比较两个列表，找出需要新增和删除的元素。
     *
     * @param oldList 代表“旧状态”或“源状态”的列表。
     * @param newList 代表“新状态”或“目标状态”的列表。
     * @return 一个 DifferenceResult 对象，包含了需要新增和删除的元素列表。
     */
    public DifferenceResult<T> calculateDifferences(List<T> oldList, List<T> newList) {
        // 处理 null 输入，将其视为空列表
        List<T> safeOldList = oldList == null ? Collections.emptyList() : oldList;
        List<T> safeNewList = newList == null ? Collections.emptyList() : newList;

        // 1. 提取旧列表中所有元素的 ID，并存储在 Set 中以提高查找效率
        Set<ID> oldIds = safeOldList.stream()
                .map(idExtractor)
                .filter(Objects::nonNull) // 过滤掉 ID 为 null 的元素
                .collect(Collectors.toSet());

        // 2. 找出需要新增的元素：存在于 newList 但不存在于 oldIds 中
        List<T> toBeAdded = safeNewList.stream()
                .filter(newItem -> {
                    ID newItemId = idExtractor.apply(newItem);
                    return newItemId == null || !oldIds.contains(newItemId);
                })
                .collect(Collectors.toList());

        // 3. 提取新列表中所有元素的 ID
        Set<ID> newIds = safeNewList.stream()
                .map(idExtractor)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 4. 找出需要删除的元素：存在于 oldList 但不存在于 newIds 中
        List<T> toBeDeleted = safeOldList.stream()
                .filter(oldItem -> !newIds.contains(idExtractor.apply(oldItem)))
                .collect(Collectors.toList());

        return new DifferenceResult<>(toBeAdded, toBeDeleted);
    }

    /**
     * 一个简单的 POJO，用于封装差异比较的结果。
     *
     * @param <T> 列表中元素的类型
     */
    public static class DifferenceResult<T> {
        private final List<T> toBeAdded;
        private final List<T> toBeDeleted;

        public DifferenceResult(List<T> toBeAdded, List<T> toBeDeleted) {
            this.toBeAdded = Collections.unmodifiableList(new ArrayList<>(toBeAdded));
            this.toBeDeleted = Collections.unmodifiableList(new ArrayList<>(toBeDeleted));
        }

        /**
         * @return 需要新增到“旧列表”以使其变为“新列表”的元素列表。
         */
        public List<T> getToBeAdded() {
            return toBeAdded;
        }

        /**
         * @return 需要从“旧列表”中删除以使其变为“新列表”的元素列表。
         */
        public List<T> getToBeDeleted() {
            return toBeDeleted;
        }

        @Override
        public String toString() {
            return "DifferenceResult{" +
                    "toBeAdded.size=" + toBeAdded.size() +
                    ", toBeDeleted.size=" + toBeDeleted.size() +
                    '}';
        }
    }
}