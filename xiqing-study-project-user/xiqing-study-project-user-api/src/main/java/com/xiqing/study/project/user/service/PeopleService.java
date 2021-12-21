package com.xiqing.study.project.user.service;

import com.xiqing.study.project.base.model.DataResponseBean;
import com.xiqing.study.project.domain.po.People;

import java.util.List;


public interface PeopleService {

    DataResponseBean<List<People>> selectAll();

    DataResponseBean<People> selectById(Integer id);
}
