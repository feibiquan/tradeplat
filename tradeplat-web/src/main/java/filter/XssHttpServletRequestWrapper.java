/******************************************************************************
 * Copyright (C) 2017 ShenZhen RuiFu Technology Co., Ltd.
 * All Rights Reserved.
 * 本软件为深圳睿付科技有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件代码.
 *****************************************************************************/
package filter;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
//import org.apache.commons.lang3.StringEscapeUtils;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private boolean escapeHTML;
	private boolean escapeJavaScript;

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public XssHttpServletRequestWrapper(HttpServletRequest request, boolean escapeHTML, boolean escapeJavaScript) {
		super(request);
		this.escapeHTML = escapeHTML;
		this.escapeJavaScript = escapeJavaScript;
	}

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = stripXSS(values[i]);
		}

		return encodedValues;
	}

	private String stripXSS(String value) {
		if (escapeHTML) {
			value = StringEscapeUtils.escapeHtml4(value);
		}
		if (escapeJavaScript) {
			value = StringEscapeUtils.escapeEcmaScript(value);
		}
		return value;
	}

	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);

		return stripXSS(value);
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		return stripXSS(value);
	}

}
