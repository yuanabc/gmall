package com.atguigu.gmall.auth.security.service.impl;

import com.atguigu.gmall.auth.dao.SysMenuDao;
import com.atguigu.gmall.auth.entity.SysMenuEntity;
import com.atguigu.gmall.auth.security.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Description 权限业务实现
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

}