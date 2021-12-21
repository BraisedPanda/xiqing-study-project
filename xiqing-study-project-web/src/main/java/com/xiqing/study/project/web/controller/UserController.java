package com.xiqing.study.project.web.controller;

import com.xiqing.study.project.base.model.DataResponseBean;
import com.xiqing.study.project.domain.po.People;
import com.xiqing.study.project.user.service.PeopleService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @DubboReference(version = "1.0")
    private PeopleService peopleService;

    @RequestMapping("/test")
    public DataResponseBean<List<People>> test(){
        return peopleService.selectAll();
    }

    @GetMapping("/{id}")
    public DataResponseBean<People> selectById(@PathVariable Integer id){
        return peopleService.selectById(id);
    }

}
