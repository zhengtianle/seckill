package cn.personal.seckill.dao;

import cn.personal.seckill.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-6
 * @Author ZhengTianle
 * Description:
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id=#{id}")  //这里#{id}通过后面参数来为其赋值
    public User getById(@Param("id") long id);    //绑定

    //绑定在对象上面了----@Param("id")long id,@Param("pwd")long pwd 效果一致
    @Update("update user set pwd=#{pwd} where id=#{id}")
    public void update(User toupdateuser);
}
