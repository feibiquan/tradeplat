/******************************************************************************
 * Copyright (C) 2017 ShenZhen RuiFu Technology Co., Ltd.
 * All Rights Reserved.
 * 本软件为深圳睿付科技有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件代码.
 *****************************************************************************/
package com.xfpay.exception;
public enum ExceptionCode {
	/**非法请求参数*/
	F001("非法请求参数", "F001"),
	F002("数字验签失败", "F002"),
	F003("非法的服务名称", "F003"),
	F004("非法的数据类型","F004"),
	F005("合作伙伴信息不存在", "F005"),
	F006("非法的请求方法", "F006"),
	F007("编码不正确", "F007"),
	F008("参数非法", "F008"),
	F009("进件商户重复", "F009"),
	F010("商户未激活电子协议", "F0010"),
	F999("系统错误", "F999"),
	F998("未登录","F998"),
	F997("实名信息未提交","F997"),
	F012("创建商户失败", "F012"),
	F013("数据过期", "F013");

	private String errorDesc;

	private String errorCode;

	ExceptionCode(String errorDesc,String errorCode){
		this.errorDesc=errorDesc;
		this.errorCode=errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public String getErrorCode() {
		return errorCode;
	}



}
