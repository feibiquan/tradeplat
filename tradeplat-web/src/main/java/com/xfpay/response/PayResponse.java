package com.xfpay.response;



import com.xfpay.exception.BusinessException;
import com.xfpay.exception.ExceptionCode;

import java.io.Serializable;

public class PayResponse implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String return_code;
	private String return_msg;
	private String channel_no;
	private String nonce_str;
	private String sign;
	private String sign_type;

	public PayResponse() {

	}

	public PayResponse(Exception e) {
		if (e instanceof BusinessException) {
			BusinessException exception = (BusinessException) e;
			this.return_code = exception.getCode();
			this.return_msg = exception.getMessage();
		} else if (e instanceof Exception) {
			this.return_code = ExceptionCode.F999.getErrorCode();
			this.return_msg = e.getMessage();
		}
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}

	public String getChannel_no() {
		return channel_no;
	}

	public void setChannel_no(String channel_no) {
		this.channel_no = channel_no;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

}
