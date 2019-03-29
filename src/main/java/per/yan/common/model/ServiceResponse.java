package per.yan.common.model;


import lombok.Data;

import java.io.Serializable;

/**
 * @author gaoyan
 * @date 2019/3/29 13:04
 */
@Data
public class ServiceResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String resultMessage;
    public static final Integer SUCCESS_CODE = 200;

    public ServiceResponse() {
        this.setCode(SUCCESS_CODE);
        this.setResultMessage("");
    }

    public ServiceResponse(Integer code, String resultMessage) {
        this.setCode(code);
        this.setResultMessage(resultMessage);
    }
}
