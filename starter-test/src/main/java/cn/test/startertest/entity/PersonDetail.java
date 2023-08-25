package cn.test.startertest.entity;

import cn.test.startertest.groups.ModifyGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.List;

/**
 * description: [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/8/23 10:01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDetail {
    private String id;

    private String name;

    private Integer age;

    private String phoneNumber;

    private String address;

    private String email;

    private List<String> hobby;
}
