package com.holmes.springboot.atomikos.utils;

import com.github.pagehelper.PageHelper;

import java.util.List;
import java.util.function.Supplier;

public class PageUtils {

    public static<T> List<T> getPageList(Integer startPage, Integer pageSize, Supplier<List<T>> supplier) {

        PageHelper.startPage(startPage, pageSize);
        return supplier.get();
    }
}
