package cn.test.startertest.request;

import cn.swordsmen.exception.custom.ArgumentException;
import cn.swordsmen.request.BaseRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * description: [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/8/23 9:59
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
