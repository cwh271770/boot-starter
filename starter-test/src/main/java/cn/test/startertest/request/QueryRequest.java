package cn.test.startertest.request;

import cn.swordsmen.exception.custom.ArgumentException;
import cn.swordsmen.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 查询请求体
 *
 * @author caiwanghong
 * @date 2023/8/25 15:50
 * @version 1.0
 */
@Data
public class QueryRequest implements BaseRequest {
    private static final long serialVersionUID = -1719980866056598046L;

    @NotBlank(message = "id不能为空")
    private String id;

    @Override
    public void validate() throws ArgumentException {

    }
}
