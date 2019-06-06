/******************************************************************************
 * Copyright (C) 2017 ShenZhen RuiFu Technology Co., Ltd.
 * All Rights Reserved.
 * 本软件为深圳睿付科技有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件代码.
 *****************************************************************************/
package com.xfpay.exception;
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1805565035157855912L;

	private String code;
	public BusinessException(String code, String message) {
		super(message);
		this.code = code;

	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	public String getCode() {
		return code;
	}


}
