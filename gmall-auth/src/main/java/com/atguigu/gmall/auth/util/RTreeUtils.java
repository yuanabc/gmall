package com.atguigu.gmall.auth.util;


import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description TODO  递归构建树
 * @Date 2020/5/25 16:57
 * @Created by yuant
 */
public class RTreeUtils<T extends TreeNode> {


	/**
	 * Gets child tree nodes.
	 *
	 * @param list     the list
	 * @param parentId the parent id
	 *
	 * @return the child tree nodes
	 */
	public List<T> getChildTreeNodes(List<T> list, String parentId) {
		List<T> returnList = new ArrayList<>();

		for (T treeNode : list) {
			if (StringUtils.isEmpty(treeNode.getParentId())) {
				continue;
			}

			if (Objects.equals(treeNode.getParentId(), parentId)) {
				recursionFn(list, treeNode);
				returnList.add(treeNode);
			}
		}
		return returnList;
	}

	/**
	 * 递归列表
	 */
	private void recursionFn(List<T> list, T node) {
		List<T> childList = getChildList(list, node);
		if (CollectionUtils.isEmpty(childList)) {
			return;
		}
		node.setChildren(childList);
		for (T tChild : childList) {
			recursionFn(list, tChild);
		}
	}

	/**
	 * 得到子节点列表
	 */
	private List<T> getChildList(List<T> list, T t) {
		List<T> tList = new ArrayList<>();

		for (T treeNode : list) {
			if (StringUtils.isBlank(treeNode.getParentId())) {
				continue;
			}
			if (Objects.equals(treeNode.getParentId(), t.getId())) {
				tList.add(treeNode);
			}
		}
		return tList;
	}


	////
	/**
	 * 使用递归方法建树
	 *
	 * @param treeNodes
	 * @return
	 */
	public <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
		List<T> trees = new ArrayList<T>();
		for (T treeNode : treeNodes) {
			if (root.equals(treeNode.getParentId())) {
				trees.add(findChildren(treeNode, treeNodes));
			}
		}
		return trees;
	}

	/**
	 * 递归查找子节点
	 *
	 * @param treeNodes
	 * @return
	 */
	public <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
		for (T it : treeNodes) {
			if (treeNode.getId().equals(it.getParentId() ) ) {
				if (treeNode.getChildren() == null) {
					treeNode.setChildren(new ArrayList<>());
				}
				treeNode.add(findChildren(it, treeNodes));
			}
		}
		return treeNode;
	}


}
