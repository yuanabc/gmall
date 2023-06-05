package com.atguigu.gmall.auth.dao;

import com.atguigu.gmall.auth.entity.SysMenuEntity;
import com.atguigu.gmall.auth.entity.SysPermitEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限DAO
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Mapper
public interface SysPermitDao extends BaseMapper<SysPermitEntity> {

}