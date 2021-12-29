package com.xiqing.study.project.user.service;

import com.xiqing.study.project.base.model.DataResponseBean;
import com.xiqing.study.project.base.model.SimpleResponseBean;
import com.xiqing.study.project.domain.po.People;

import java.util.List;


public interface PeopleService {

    DataResponseBean<List<People>> selectAll();

    DataResponseBean<People> selectById(Integer id);

    DataResponseBean<List<People>> findByName(String name);

    SimpleResponseBean createPeopleIndex(String index, String aliases);

    SimpleResponseBean deletePeopleIndex(String index, String id);

    DataResponseBean<People> searchIndex(String index, String keyword);

    SimpleResponseBean save(People people);

    SimpleResponseBean saveList(List<People> people);
}
