package com.atguigu.gmall.auth.security.service.impl;

import com.atguigu.gmall.auth.dao.SysRoleMenuDao;
import com.atguigu.gmall.auth.entity.SysRoleMenuEntity;
import com.atguigu.gmall.auth.security.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Description 角色与权限业务实现
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

}