package com.xiqing.study.project.book.service;

import com.xiqing.study.project.base.model.DataResponseBean;
import com.xiqing.study.project.base.model.SimpleResponseBean;
import com.xiqing.study.project.domain.po.Book;

public interface BookService {
    SimpleResponseBean test();

    DataResponseBean searchIndex(String index, String keyword);

    SimpleResponseBean insert(Book book);
}
