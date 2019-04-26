package per.yan.common.util;

import com.alibaba.dubbo.common.utils.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gaoyan
 * @date 2019/3/14 17:13
 */
public class GlobalMethodUtil {

    private static final String PERCENT = "%";

    public static <T, K, V> Map<K, V> list2Map(List<T> list, Function<T, K> f1, Function<T, V> f2) {
        Map<K, V> map = null;
        if (CollectionUtils.isNotEmpty(list)) {
            map = list.stream().collect(Collectors.toMap(f1, f2, (f, s) -> s));
        }
        return map;
    }

    public static <T, K> Map<K, T> list2Map(List<T> list, Function<T, K> f1) {
        Map<K, T> map = null;
        if (CollectionUtils.isNotEmpty(list)) {
            map = list.stream().collect(Collectors.toMap(f1, t -> t, (f, s) -> s));
        }
        return map;
    }

    public static <P, R> Set<R> list2Set(List<P> list, Function<P, R> f) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.stream().map(f).collect(Collectors.toSet());
    }

    public static <T, K> Map<K, List<T>> group2Map(List<T> list, Function<T, K> f) {
        Map<K, List<T>> map = null;
        if (CollectionUtils.isNotEmpty(list)) {
            map = list.stream().collect(Collectors.groupingBy(f));
        }
        return map;
    }

    public static String appendPercent(String property) {
        return appendPercentSuffix(appendPercentPrefix(property));
    }

    public static String appendPercentPrefix(String property) {
        return PERCENT + property;
    }

    public static String appendPercentSuffix(String property) {
        return property + PERCENT;
    }
}
