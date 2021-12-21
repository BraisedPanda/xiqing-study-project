package com.xiqing.study.project.user.service;

import com.xiqing.project.redis.RedisUtil;
import com.xiqing.study.project.base.model.DataResponseBean;
import com.xiqing.study.project.domain.po.People;
import com.xiqing.study.project.domain.po.PeopleExample;
import com.xiqing.study.project.user.mapper.PeopleMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@DubboService(version = "1.0")
public class PeopleServiceImpl implements PeopleService{

    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public DataResponseBean<List<People>> selectAll() {
        PeopleExample peopleExample = new PeopleExample();
        List<People> list = peopleMapper.selectByExample(peopleExample);
        return DataResponseBean.SUCCESS(list);
    }


    @Override
    public DataResponseBean<People> selectById(Integer id) {
        People people = (People) redisUtil.get(id.toString());
        if(people == null){
            people = peopleMapper.selectByPrimaryKey(id);
            redisUtil.set(id.toString(), people);
        }
        return DataResponseBean.SUCCESS(people);
    }
}
