package com.atguigu.gmall.auth.controller;

import com.atguigu.gmall.auth.entity.ImageCode;
import com.atguigu.gmall.auth.entity.ValidateCode;
import com.atguigu.gmall.auth.security.constant.ValidateCodeTypeEnum;
import com.atguigu.gmall.auth.service.ValidateCodeService;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

import static com.atguigu.gmall.auth.service.impl.ValidateCodeServiceImpl.VALIDATECODE_SESSIONKEY_PREFIX;

/**
 * 验证码提供
 *
 */
@Controller
public class ValidateCodeController {

	@Autowired
	private Producer producer;

	@Autowired
	private ValidateCodeService validateCodeService;

	/**
	 * 创建验证码
	 *
	 * @throws Exception
	 */
	@GetMapping("/validata/code")
	public void createCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);

		ImageCode imageCode = new ImageCode(image, text, 60);

		validateCodeService.saveImageCode(request, imageCode, ValidateCodeTypeEnum.IMAGE);
		try (
				ServletOutputStream out = response.getOutputStream()
		) {
			ImageIO.write(image, "JPEG", out);
		}
	}

	@GetMapping("/validata/1")
	@ResponseBody
	public String aa(HttpServletRequest request){
		StringBuilder buff = new StringBuilder(VALIDATECODE_SESSIONKEY_PREFIX);
		buff.append(ValidateCodeTypeEnum.IMAGE);
		String string = buff.toString();

		ValidateCode validateCode = (ValidateCode) request.getSession().getAttribute(string);
		if(validateCode !=null){
			return validateCode.getCode();
		}
		return "空";
	}
}
