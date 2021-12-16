package com.xiqing.study.project.user.service;

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

    @Override
    public List<People> selectAll() {
        PeopleExample peopleExample = new PeopleExample();
        return peopleMapper.selectByExample(peopleExample);
    }
}
