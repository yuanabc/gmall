package com.atguigu.gmall.auth.entity;

import com.atguigu.gmall.auth.util.TreeNode;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description TODO
 * @Date 2020/9/24 16:05
 * @Created by yuant
 */
@Data
@TableName("sys_permit")
public class SysPermitEntity extends TreeNode {


	/**
	 * 用户ID
	 */
	private String id;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 密码
	 */
	private String alias;
	/**
	 * 状态:NORMAL正常  PROHIBIT禁用
	 */
	private Integer level;

//	private String parentId;

}
