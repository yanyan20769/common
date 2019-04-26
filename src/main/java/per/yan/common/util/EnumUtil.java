package per.yan.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

/**
 * @author gaoyan
 * @date 2019/4/25 17:54
 */
public class EnumUtil {

    /**
     * @param p   待比较的属性值
     * @param c   枚举类型
     * @param f   枚举实例的获取属性值的方法
     * @param <P> 参数类型
     * @param <E> 枚举类
     * @return 枚举实例
     */
    public static <P, E extends Enum> E getEnum(
            P p, Class<E> c, Function<E, P> f) {

        if (p == null || c == null || f == null) {
            return null;
        }

        for (E e : c.getEnumConstants()) {
            if (p.equals(f.apply(e))) {
                return e;
            }
        }
        return null;
    }

    public static <P, E extends Enum> String getEnumName(
            P p, Class<E> c, Function<E, P> f) {

        E e = getEnum(p, c, f);
        return e == null ? StringUtils.EMPTY : e.name();
    }
}
