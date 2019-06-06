/******************************************************************************
 * Copyright (C) 2016~2018 ShenZhen RuiFu Technology Co., Ltd.
 * All Rights Reserved.
 * 本软件为深圳睿付科技有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件代码.
 *****************************************************************************/
package com.xfpay.validator;


public class EmptyValidator extends DefaultValidator {

	@Override
	public boolean test(Object obj) {
		return test(BehaviorType.ADD, obj);
	}

	@Override
	public boolean test(BehaviorType behavior, Object obj) {
		if (behavior == BehaviorType.ADD) {
			if (obj instanceof String) {
				return obj != null && obj.toString().length() > 0;
			}
			return obj != null;
		}
		return false;
	}

}
