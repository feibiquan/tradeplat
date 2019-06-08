package com.xfpay.utils;

import com.xfpay.request.RequestHead;
import com.xfpay.response.PayResponse;

import javax.servlet.http.HttpServletRequest;

public interface IReceiveExector {


	/**
	 * 从这里开始执行
	 * @param head
	 * @param request
	 * @return
	 * @since 1.0: 单机版
	 */
	public PayResponse execute(RequestHead head, HttpServletRequest request);


	/**
	 * 将内容转成自定义的request
	 * @param head
	 * @return
	 */
	public RequestHead resolve(RequestHead head);
}