package com.atguigu.gmall.auth.controller.guest;

import com.atguigu.gmall.auth.entity.SysPermitEntity;
import com.atguigu.gmall.auth.security.service.SysPermitService;
import com.atguigu.gmall.auth.util.RTreeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description TODO
 * @Date 2020/9/24 16:10
 * @Created by yuant
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/guest/p")
public class PermitController {

	private final SysPermitService sysPermitService;

	@GetMapping
	public Object list(){
		List<SysPermitEntity> list = sysPermitService.lambdaQuery().list();

		RTreeUtils treeUtils = new RTreeUtils();
		List childTreeNodes = treeUtils.getChildTreeNodes(list, "0");
		return childTreeNodes;

	}



}
