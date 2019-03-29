package per.yan.common.model;

import lombok.Data;

/**
 * @author gaoyan
 * @date 2019/3/29 13:04
 */
@Data
public class PageListResponse<T> extends DataResponse<T> {
    private static final long serialVersionUID = 1L;
    private Integer page;
    private Integer pageSize;
    private long totalCount;

    public PageListResponse() {
        this(null);
    }

    public PageListResponse(T data) {
        this(SUCCESS_CODE, data);
    }

    public PageListResponse(Integer code, String resultMessage) {
        super(code, resultMessage);
    }

    public PageListResponse(Integer code, T data) {
        super(code, data);
    }

    public PageListResponse(Integer code, String resultMessage, T data) {
        super(code, resultMessage, data);
    }

    public PageListResponse(Integer code, String resultMessage, T data, Integer page, Integer PageSize) {
        super(code, resultMessage, data);
        this.setPage(page);
        this.setPageSize(PageSize);
    }

    @Override
    public void setData(T data) {
        super.setData(data);
    }
}
