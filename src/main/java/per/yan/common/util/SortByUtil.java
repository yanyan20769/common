package per.yan.common.util;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 场景：前端需要按照字段排序，但如果前端上传的是错误字段，则sql会报错。
 * 因此，将数据库表对应的DO对象的字段放入SoftReference中做缓存
 * @author gaoyan
 * @date 2019/2/13 10:17
 */
public class SortByUtil {
    private static volatile SoftReference<Map<Class<?>, Map<String, String>>> sortByCache = new SoftReference<>(new ConcurrentHashMap<>());

    public static Map<String, String> getValidField(Class<?> type) {
        Map<String, String> cache = Optional.ofNullable(sortByCache.get()).map(m -> m.get(type)).orElse(new HashMap<>(32));
        if (MapUtils.isNotEmpty(cache)) {
            return cache;
        }
        Field[] fields = type.getDeclaredFields();
        if (null != fields && fields.length > 0) {
            int i = -1;
            final String serialVersionUID = "serialVersionUID";
            while (++i < fields.length) {
                //将java类属性转成表字段
                String field = fields[i].getName();
                if (!serialVersionUID.equalsIgnoreCase(field)) {
                    String underlineStr = camelString2UnderlineString(field);
                    if (StringUtils.isNotBlank(underlineStr)) {
                        cache.put(field, underlineStr);
                        cache.put(underlineStr, underlineStr);
                    }
                }
            }
        }
        getCacheMap().putIfAbsent(type, cache);
        return cache;
    }

    private static String camelString2UnderlineString(String before) {
        if (StringUtils.isNotBlank(before)) {
            StringBuilder after = new StringBuilder();
            int i = -1;
            while (++i < before.length()) {
                char c = before.charAt(i);
                if (c < 65 || c > 90) {
                    after.append(c);
                } else {
                    after.append("_");
                    after.append((char) (c + 32));
                }
            }
            return after.toString();
        }
        return null;
    }

    private static Map<Class<?>, Map<String, String>> getCacheMap() {
        if (null == sortByCache.get()) {
            synchronized (SortByUtil.class) {
                if (null == sortByCache.get()) {
                    sortByCache = new SoftReference<>(new ConcurrentHashMap<>(16));
                }
            }
        }
        return sortByCache.get();
    }

}
