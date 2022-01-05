package com.xiqing.study.project.book.service;

import com.xiqing.project.elasticsearch.ElasticsearchUtil;
import com.xiqing.study.project.base.model.DataResponseBean;
import com.xiqing.study.project.base.model.SimpleResponseBean;
import com.xiqing.study.project.book.mapper.BookMapper;
import com.xiqing.study.project.domain.po.Book;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DubboService(version = "1.0")
public class BookServiceImpl implements BookService{

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ElasticsearchUtil elasticsearchUtil;

    @Override
    public SimpleResponseBean test() {

        return SimpleResponseBean.SUCCESS();
    }

    @Override
    public SimpleResponseBean insert(Book book) {
        book.setCreateDate(LocalDateTime.now());
        int result = bookMapper.insert(book);
        List<Book> bookList = new ArrayList<>(Arrays.asList(book));
        if(result == 1){
            elasticsearchUtil.createDocument("book", bookList);
            return SimpleResponseBean.SUCCESS();
        }
        return SimpleResponseBean.FAILED();
    }

    @Override
    public DataResponseBean searchIndex(String index, String keyword) {
        List<String> indexList = new ArrayList<>(Arrays.asList(index));
        DataResponseBean<Book> responseBean = elasticsearchUtil.search(indexList, Book.class);
        return responseBean;
    }
}
