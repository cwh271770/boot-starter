package cn.test.startertest.service;

import cn.test.startertest.entity.PersonDetail;
import cn.test.startertest.request.QueryRequest;
import cn.test.startertest.request.UpsertRequest;

/**
 * 测试服务类
 *
 * @author caiwanghong
 * @date 2023/8/25 15:50
 * @version 1.0
 */
public interface TestService {
    /**
     * 新增测试
     *
     * @author caiwanghong
     * @param request
     * @date 2023/8/25 15:51
     * @return void
     */
    void add(UpsertRequest request);

    /**
     * 修改测试
     *
     * @author caiwanghong
     * @param request
     * @date 2023/8/25 15:51
     * @return void
     */
    void modify(UpsertRequest request);

    /**
     * 查询测试
     *
     * @author caiwanghong
     * @param request
     * @date 2023/8/25 15:51
     * @return PersonDetail
     */
    PersonDetail query(QueryRequest request);
}
