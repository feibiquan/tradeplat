package com.xfpay.receive;


import com.xfpay.request.RequestHead;
import com.xfpay.response.PayResponse;
import com.xfpay.utils.IReceiveExector;

import javax.servlet.http.HttpServletRequest;


public abstract class Receive implements IReceiveExector {

	@Override
	public abstract PayResponse execute(RequestHead head, HttpServletRequest request);

	public boolean validateDouble(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validateLong(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validateInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getFrontCover(String orgStr, int length) {
		if (length > 0 && orgStr.length() < length) {
			int ablen = length - orgStr.length();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < ablen; i++) {
				sb.append("0");
			}
			sb.append(orgStr);
			return sb.toString();
		} else {
			return orgStr;
		}
	}

}
