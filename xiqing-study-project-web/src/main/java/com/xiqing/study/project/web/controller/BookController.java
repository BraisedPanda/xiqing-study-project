package com.xiqing.study.project.web.controller;

import com.xiqing.study.project.base.model.DataResponseBean;
import com.xiqing.study.project.base.model.SimpleResponseBean;
import com.xiqing.study.project.book.service.BookService;
import com.xiqing.study.project.domain.po.Book;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @DubboReference(version = "1.0")
    private BookService bookService;

    @RequestMapping("test")
    public SimpleResponseBean test(){
        return bookService.test();
    }

    @GetMapping("/searchIndex")
    public DataResponseBean searchIndex(@RequestParam String index, @RequestParam String keyword){
        return bookService.searchIndex(index, keyword);
    }

    @PostMapping("/insert")
    public SimpleResponseBean insert(@RequestBody Book book){
        return bookService.insert(book);
    }


}
