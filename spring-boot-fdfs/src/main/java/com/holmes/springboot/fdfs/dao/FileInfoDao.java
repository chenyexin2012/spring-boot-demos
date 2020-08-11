package com.holmes.springboot.fdfs.dao;

import com.holmes.springboot.fdfs.entity.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.stereotype.Component;

import java.sql.Types;

@Component
public class FileInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(FileInfo fileInfo) {

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory("insert into file_info(file_name, url) value(?, ?)",
                Types.VARCHAR, Types.VARCHAR);
        jdbcTemplate.update(factory.newPreparedStatementCreator(new Object[]{fileInfo.getFileName(), fileInfo.getUrl()}));
    }
}
