package com.atguigu.gmall.auth.security.service.impl;

import com.atguigu.gmall.auth.dao.SysUserRoleDao;
import com.atguigu.gmall.auth.entity.SysUserRoleEntity;
import com.atguigu.gmall.auth.security.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Description 用户与角色业务实现
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

}