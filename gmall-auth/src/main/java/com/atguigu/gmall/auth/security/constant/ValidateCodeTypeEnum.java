package com.atguigu.gmall.auth.security.constant;

public enum ValidateCodeTypeEnum {
	IMAGE {
		@Override
		public String getParameterNameOnValidate() {
			return BrowserSecurityProperties.DEFAULT_REQUEST_PARAMETER_IMAGECODE;
		}
	},
	SMS {
		@Override
		public String getParameterNameOnValidate() {
			return BrowserSecurityProperties.DEFAULT_REQUEST_PARAMETER_SMSCODE;
		}
	};

	public abstract String getParameterNameOnValidate();

	public static void main(String[] args) {
		System.out.println(ValidateCodeTypeEnum.IMAGE.getParameterNameOnValidate());
	}
}
