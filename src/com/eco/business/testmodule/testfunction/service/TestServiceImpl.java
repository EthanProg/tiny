package com.eco.business.testmodule.testfunction.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @author Ethan
 * @date 2016/6/15
 */
@Service("testService")
public class TestServiceImpl implements ITestService{

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public List getUsers(Map parMap) {
//        return testMapper.getUsers(parMap);
        PageBounds pageBounds = new PageBounds(1, 3);
        SqlSession sqlSession = null;
        List list = null;
        try {
            sqlSession = SqlSessionUtils.getSqlSession(sqlSessionFactory);
            list = sqlSession.selectList("com.eco.business.testmodule.testfunction.service.TestMapper.getUsers", new HashMap(), pageBounds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return list;
    }
}
