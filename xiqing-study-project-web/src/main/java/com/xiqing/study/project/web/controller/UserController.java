package com.xiqing.study.project.web.controller;

import com.xiqing.study.project.base.model.DataResponseBean;
import com.xiqing.study.project.base.model.SimpleResponseBean;
import com.xiqing.study.project.domain.po.People;
import com.xiqing.study.project.user.service.PeopleService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/findByName")
    public DataResponseBean<List<People>> findByName(@RequestParam String name){
        return peopleService.findByName(name);
    }

    @PostMapping("/createPeopleIndex")
    public SimpleResponseBean createPeopleIndex(@RequestParam String index, @RequestParam String aliases){
        return peopleService.createPeopleIndex(index, aliases);
    }

    @DeleteMapping("/deletePeopleIndex")
    public SimpleResponseBean deletePeopleIndex(@RequestParam String index, @RequestParam String id){
        return peopleService.deletePeopleIndex(index, id);
    }

    @GetMapping("/searchIndex")
    public DataResponseBean<People> searchIndex(@RequestParam String index, @RequestParam String keyword){
        return peopleService.searchIndex(index, keyword);
    }

    @PutMapping("/save")
    public SimpleResponseBean save(@RequestBody People people){
        return peopleService.save(people);
    }

    @PutMapping("/saveList")
    public SimpleResponseBean saveList(@RequestBody List<People> people){
        return peopleService.saveList(people);
    }


}
