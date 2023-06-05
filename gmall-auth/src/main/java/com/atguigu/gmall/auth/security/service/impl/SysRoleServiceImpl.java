package com.atguigu.gmall.auth.security.service.impl;

import com.atguigu.gmall.auth.dao.SysRoleDao;
import com.atguigu.gmall.auth.entity.SysRoleEntity;
import com.atguigu.gmall.auth.security.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Description 角色业务实现
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

}