package cn.test.startertest.service;

import cn.test.startertest.entity.PersonDetail;
import cn.test.startertest.request.QueryRequest;
import cn.test.startertest.request.UpsertRequest;

/**
 * 测试服务类 [蔡旺鸿]
 *
 * @Author caiwanghong
 * @Date 2023/8/23 9:55
 * @Version 1.0
 * @apiNote
 */
public interface TestService {

    /***
     * 新增测试 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param request
     * @return void
     * @date 2023/8/23 9:56
     */
    void add(UpsertRequest request);

    /***
     * 修改测试 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param request
     * @return void
     * @date 2023/8/23 9:56
     */
    void modify(UpsertRequest request);


    /***
     * 查询测试 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param request
     * @return PersonDetail
     * @date 2023/8/23 10:04
     */
    PersonDetail query(QueryRequest request);
}
