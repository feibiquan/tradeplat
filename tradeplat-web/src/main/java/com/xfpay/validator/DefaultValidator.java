/******************************************************************************
 * Copyright (C) 2016~2018 ShenZhen RuiFu Technology Co., Ltd.
 * All Rights Reserved.
 * 本软件为深圳睿付科技有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件代码.
 *****************************************************************************/
package com.xfpay.validator;

public abstract class DefaultValidator {

	protected String errorDesc = "字段不允许为空";
	/**
	 * 测试
	 *
	 * @param obj
	 * @return
	 * @see [类、类#方法、类#属性]
	 * @since [起始版本]
	 */
	public abstract boolean test(Object obj);

	/**
	 * 根据不同行为进行验证字段
	 *
	 * @param behavior
	 * @param obj
	 * @return
	 * @see [类、类#方法、类#属性]
	 * @since [起始版本]
	 */
	public abstract boolean test(BehaviorType behavior, Object obj);

	/**
	 * 获取异常描述
	 *
	 * @return
	 * @see [类、类#方法、类#属性]
	 * @since [起始版本]
	 */
	public  String getErrorDesc(){
		return errorDesc;
	}
}
