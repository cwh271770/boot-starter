package cn.test.startertest.controller;

import cn.swordsmen.response.RespResult;
import cn.test.startertest.entity.PersonDetail;
import cn.test.startertest.groups.AddGroup;
import cn.test.startertest.groups.ModifyGroup;
import cn.test.startertest.request.QueryRequest;
import cn.test.startertest.request.UpsertRequest;
import cn.test.startertest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.groups.Default;

/**
 * 测试控制器 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/8/23 9:36
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @PostMapping("/add")
    RespResult<Void> add(@RequestBody @Validated({AddGroup.class, Default.class}) UpsertRequest request) {
        testService.add(request);
        return RespResult.success();
    }

    @PostMapping("/modify")
    RespResult<Void> modify(@RequestBody @Validated({ModifyGroup.class, Default.class}) UpsertRequest request) {
        testService.modify(request);
        return RespResult.success();
    }

    @PostMapping("/query")
    RespResult<PersonDetail> query(@RequestBody @Valid QueryRequest request) {
        return RespResult.success(testService.query(request));
    }
}
