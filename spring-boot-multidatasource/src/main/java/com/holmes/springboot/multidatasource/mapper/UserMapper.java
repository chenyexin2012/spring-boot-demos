package com.holmes.springboot.multidatasource.mapper;

import com.holmes.springboot.multidatasource.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Results(id = "BaseResultMap", value = {
            @Result(column = "ID", property = "id"),
            @Result(column = "USER_NAME", property = "userName"),
            @Result(column = "USER_PASSWORD", property = "userPassword"),
            @Result(column = "USER_EMAIL", property = "userEmail"),
            @Result(column = "ADDRESS", property = "address")
    })
    @Select("select ID, USER_NAME, USER_PASSWORD, USER_EMAIL, ADDRESS from t_user")
    List<User> selectList();

    @Delete("delete from t_user where ID = #{id,jdbcType=BIGINT}")
    int deleteByPrimaryKey(@Param("id") Long id);

    @Insert("insert into t_user (ID, USER_NAME, USER_PASSWORD,\n" +
            "          USER_EMAIL, ADDRESS)\n" +
            "        values (#{id,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, #{userPassword,jdbcType=VARCHAR},\n" +
            "          #{userEmail,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR})")
    int insert(User record);

    @ResultMap("BaseResultMap")
    @Select("select ID, USER_NAME, USER_PASSWORD, USER_EMAIL, ADDRESS from t_user\n" +
            "        where ID = #{id,jdbcType=BIGINT}")
    User selectByPrimaryKey(Long id);

    @Update("update t_user\n" +
            "        set USER_NAME = #{userName,jdbcType=VARCHAR},\n" +
            "          USER_PASSWORD = #{userPassword,jdbcType=VARCHAR},\n" +
            "          USER_EMAIL = #{userEmail,jdbcType=VARCHAR},\n" +
            "          ADDRESS = #{address,jdbcType=VARCHAR}\n" +
            "        where ID = #{id,jdbcType=BIGINT}")
    int updateByPrimaryKey(User record);
}