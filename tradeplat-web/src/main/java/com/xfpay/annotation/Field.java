package com.xfpay.annotation;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Field {
	public String name();

	public boolean notNull() default true;
	
	public int length() default -1;
}
