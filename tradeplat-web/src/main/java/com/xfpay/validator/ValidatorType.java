/******************************************************************************
 * Copyright (C) 2016~2018 ShenZhen RuiFu Technology Co., Ltd.
 * All Rights Reserved.
 * 本软件为深圳睿付科技有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件代码.
 *****************************************************************************/
package com.xfpay.validator;

/**
 * 验证类型
 *
 * @since [起始版本]
 */
public enum ValidatorType {
	NotNull("非空验证"), PROVICE("省份代码验证"), CITY("城市代码验证"), INDUSTRY("行业类别验证"), BANKCODE("银行编号验证"), CONTACTNUMBER(
			"联行号验证"), PHONE("手机号码"), EMAIL("邮箱地址"), CHINESE("中文验证"),IDCARD("身份证"),NOREPEAT("不允许重复"), ACCOUNTTYPE("账号类型");
	private String name;

	private ValidatorType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
