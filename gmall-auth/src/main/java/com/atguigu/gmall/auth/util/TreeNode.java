package com.atguigu.gmall.auth.util;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Date 2020/9/24 16:19
 * @Created by yuant
 */
@Getter
@Setter
public class TreeNode {

	@TableId
	protected String id;
	protected String parentId;

			@TableField(exist = false)
	List children = new ArrayList();

	public void add(TreeNode node) {
		children.add(node);
	}
}
