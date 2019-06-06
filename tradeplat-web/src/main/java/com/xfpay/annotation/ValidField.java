/******************************************************************************
 * Copyright (C) 2016~2018 ShenZhen RuiFu Technology Co., Ltd.
 * All Rights Reserved.
 * 本软件为深圳睿付科技有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件代码.
 *****************************************************************************/
package com.xfpay.annotation;


import com.xfpay.validator.DefaultValidator;
import com.xfpay.validator.EmptyValidator;
import com.xfpay.validator.ValidatorType;

import java.lang.annotation.*;


@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidField {
	// 字段中文名称
	public String value() default "";

	/**
	 * 验证控制器
	 *
	 * @return
	 * @see [类、类#方法、类#属性]
	 * @since [起始版本]
	 */
	Class<? extends DefaultValidator>[] valide() default EmptyValidator.class;

	/**
	 * 根据验证类型进行验证
	 *
	 * @return
	 * @see [类、类#方法、类#属性]
	 * @since [起始版本]
	 */
	ValidatorType[] valideType() default ValidatorType.NotNull;

	/**
	 *
	 * @return
	 * @see [类、类#方法、类#属性]
	 * @since [起始版本]
	 */
	int length() default -1;

	/**
	 * 最小长度 如果字段是INT类型，则比较数字大小
	 *
	 * @return
	 * @see [类、类#方法、类#属性]
	 * @since [起始版本]
	 */
	int min() default -1;

	/**
	 * 最大长度。 如果字段是INT类型，则比较数字大小
	 *
	 * @return
	 * @see [类、类#方法、类#属性]
	 * @since [起始版本]
	 */
	int max() default -1;
}
