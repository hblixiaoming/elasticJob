package com.lxm.elasticjob.mapper;

        import com.lxm.elasticjob.model.User;
        import org.apache.ibatis.annotations.Param;

        import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectWithMod(@Param("mod") Integer mod);
}