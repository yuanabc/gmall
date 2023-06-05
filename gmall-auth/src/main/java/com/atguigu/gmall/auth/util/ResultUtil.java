package com.atguigu.gmall.auth.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @Description TODO
 * @Date 2020/8/27 21:51
 * @Created by yuant
 */
@Slf4j
public class ResultUtil {

	/**
	 * 使用response输出JSON
	 * @Author Sans
	 * @CreateTime 2019/9/28 11:23
	 * @Param  resultMap 数据
	 * @Return void
	 */
	public static void responseJson(ServletResponse response, Object resultMap){
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			out = response.getWriter();
			out.println(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			log.error("【JSON输出异常】"+e);
		}finally{
			if(out!=null){
				out.flush();
				out.close();
			}
		}
	}

}
