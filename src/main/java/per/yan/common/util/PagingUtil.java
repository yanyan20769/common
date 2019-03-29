package per.yan.common.util;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.pagehelper.PageInfo;
import per.yan.common.model.PageListResponse;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author gaoyan
 * @date 2019/3/14 9:45
 */
public class PagingUtil {

    /**
     *
     * @param p 分页查询入参对象
     * @param f 分页查询方法
     * @param b 结果集转换方法
     * @param <P> 入参对象
     * @param <D> 数据库DO对象
     * @param <R> 返回结果对象
     */
    public static <P, D, R> PageListResponse<List<R>> doSearch(
            P p, Function<P, PageInfo<D>> f, BiConsumer<PageInfo<D>, PageListResponse<List<R>>> b) {

        PageListResponse<List<R>> response = new PageListResponse<>();
        PageInfo<D> pageInfo = f.apply(p);
        setPageInfo(response, pageInfo);
        if (null != pageInfo && CollectionUtils.isNotEmpty(pageInfo.getList())) {
            b.accept(pageInfo, response);
        }
        return response;
    }

    public static <P, D, R> PageListResponse<List<R>> process(P p, Function<P, PageInfo<D>> f1, Function<D, R> f2) {
        PageInfo<D> pageInfo = f1.apply(p);
        List<R> list = modelConvert(pageInfo, f2);
        return assemblePageResponse(list, pageInfo);
    }

    public static <R, D> List<R> modelConvert(PageInfo<D> pageInfo, Function<D, R> f2) {
        List<R> list = null;
        if (null != pageInfo && CollectionUtils.isNotEmpty(pageInfo.getList())) {
            list = pageInfo.getList().stream().map(f2).collect(Collectors.toList());
        }
        return list;
    }

    private static <R> PageListResponse<List<R>> assemblePageResponse(List<R> list, PageInfo pageInfo) {
        PageListResponse<List<R>> response = new PageListResponse<>(list);
        setPageInfo(response, pageInfo);
        return response;
    }

    public static void setPageInfo(PageListResponse response, PageInfo info) {
        if (response == null || info == null) {
            return;
        }
        response.setPage(info.getPageNum());
        response.setPageSize(info.getPageSize());
        response.setTotalCount(info.getTotal());
    }
}
