package cn.test.startertest.service.impl;

import cn.swordsman.exception.custom.BusinessException;
import cn.test.startertest.entity.PersonDetail;
import cn.test.startertest.request.QueryRequest;
import cn.test.startertest.request.UpsertRequest;
import cn.test.startertest.service.TestService;
import org.springframework.stereotype.Service;

import static cn.test.startertest.enums.ErrorEnum.DATA_IS_NOT_EXIST;

/**
 * 测试服务实现类
 *
 * @author caiwanghong
 * @date 2023/8/25 15:50
 * @version 1.0
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public void add(UpsertRequest request) {
        throw new BusinessException("测试业务异常");
    }

    @Override
    public void modify(UpsertRequest request) {
        DATA_IS_NOT_EXIST.throwError();
    }

    @Override
    public PersonDetail query(QueryRequest request) {
        throw new BusinessException("测试业务异常");
    }
}
