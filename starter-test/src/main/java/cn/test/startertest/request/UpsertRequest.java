package cn.test.startertest.request;

import cn.hutool.core.util.StrUtil;
import cn.swordsman.exception.custom.ArgumentException;
import cn.swordsman.request.BaseRequest;
import cn.test.startertest.groups.ModifyGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 新增修改请求体
 *
 * @author caiwanghong
 * @date 2023/8/25 15:50
 * @version 1.0
 */
@Data
public class UpsertRequest implements BaseRequest {
    private static final long serialVersionUID = 5109478838469086891L;

    @NotBlank(message = "id不能为空", groups = ModifyGroup.class)
    private String id;

    @NotBlank
    private String name;

    @Max(100)
    @Min(0)
    private Integer age;

    private String phoneNumber;

    @Length(max = 20, message = "地址长度非法")
    private String address;

    @Email
    private String email;

    @NotEmpty
    @Size(max = 5)
    private List<String> hobby;

    @Override
    public void validate() throws ArgumentException {
        if (StrUtil.isAllBlank(phoneNumber, email)) {
            throw new ArgumentException("phoneNumber和email至少有一个不为空！");
        }
    }
}
