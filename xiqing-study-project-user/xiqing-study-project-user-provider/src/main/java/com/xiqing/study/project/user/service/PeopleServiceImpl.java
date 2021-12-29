package com.xiqing.study.project.user.service;

import com.xiqing.project.elasticsearch.ElasticsearchUtil;
import com.xiqing.project.redis.RedisUtil;
import com.xiqing.study.project.base.model.DataResponseBean;
import com.xiqing.study.project.base.model.SimpleResponseBean;
import com.xiqing.study.project.domain.po.People;
import com.xiqing.study.project.domain.po.PeopleExample;
import com.xiqing.study.project.user.mapper.PeopleMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@DubboService(version = "1.0")
public class PeopleServiceImpl implements PeopleService{

    @Autowired
    private PeopleMapper peopleMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ElasticsearchUtil elasticsearchUtil;

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

    @Override
    public DataResponseBean<List<People>> findByName(String name) {
        return null;
    }


    @Override
    public SimpleResponseBean createPeopleIndex(String index, String aliases){
        return elasticsearchUtil.createIndex(index, aliases);
    }

    @Override
    public SimpleResponseBean deletePeopleIndex(String index, String id) {
            return elasticsearchUtil.deleteIndex(index, id);
    }

    @Override
    public DataResponseBean<People> searchIndex(String index, String keyword) {
        List<String> indexList = new ArrayList<>(Arrays.asList(index));
        if(keyword != null && keyword.length() > 0){
            return elasticsearchUtil.search(indexList, keyword, People.class);
        }
        return elasticsearchUtil.search(indexList, People.class);
    }

    @Override
    public SimpleResponseBean save(People people) {
        peopleMapper.insert(people);
        return elasticsearchUtil.createDocument("people" ,people);
    }

    @Override
    public SimpleResponseBean saveList(List<People> list) {
        peopleMapper.insertBatch(list);
        return elasticsearchUtil.createDocument("people" ,list);
    }
}
