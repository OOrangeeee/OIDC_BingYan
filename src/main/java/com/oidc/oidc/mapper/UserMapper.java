package com.oidc.oidc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oidc.oidc.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ChenXi Jin
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    //调用mybatis-plus的接口实现mapper，避免使用sql语句出错。
    //如果需要使用sql语句，可以重写该方法。
    //后续有时间再来更改。
    //TODO:重写UserMapper
}
