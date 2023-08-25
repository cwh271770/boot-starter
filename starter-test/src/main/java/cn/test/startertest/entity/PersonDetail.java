package cn.test.startertest.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 人员信息详情
 *
 * @author caiwanghong
 * @date 2023/8/25 15:48
 * @version 1.0
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
