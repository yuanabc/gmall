package com.atguigu.gmall.auth.service;

import com.atguigu.gmall.auth.entity.ValidateCode;
import com.atguigu.gmall.auth.security.constant.ValidateCodeTypeEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * @date 2020/08/28
 */
public interface ValidateCodeService {
    /**
     * 保存图形验证码
     * @param code 验证码
     */
    void saveImageCode(HttpServletRequest request, ValidateCode code, ValidateCodeTypeEnum codeType);

    /**
     * 获取验证码
     */
    ValidateCode getCode(HttpServletRequest request, ValidateCodeTypeEnum codeTypeEnum);

    /**
     * 删除验证码
     */
    void remove(HttpServletRequest request, ValidateCodeTypeEnum codeType);

    /**
     * 验证验证码
     */
    void validate(HttpServletRequest request);
    
}
