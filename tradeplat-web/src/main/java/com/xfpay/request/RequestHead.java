package com.xfpay.request;


import com.xfpay.annotation.Field;
import com.xfpay.annotation.Ignore;
import com.xfpay.entity.AccessPartner;

import java.io.Serializable;

/**
 * 请求头部
 *
 * @author Administrator
 *
 */
public class RequestHead implements Serializable {

	private static final long serialVersionUID = 1L;
	@Field(name = "版本号", length = 4)
	private String version = "1.0";
	@Field(name = "字符集", notNull = false, length = 8)
	private String charset = "UTF-8";
	@Field(name = "渠道号", length = 32)
	private String channel_no;
	@Field(name = "服务接口", length = 32)
	private String service;
	@Field(name = "报文体")
	@Ignore
	private String data_content;
	@Field(name = "签名方式", length = 8)
	private String sign_type = "MD5";
	@Field(name = "随机字符串", length = 32)
	private String nonce_str;
	@Field(name = "签名")
	@Ignore
	private String sign;

//	@Ignore
//	private OrganizationInfo channel;
	@Ignore
	private AccessPartner accessPartner;

	private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getChannel_no() {
		return channel_no;
	}

	public void setChannel_no(String channel_no) {
		this.channel_no = channel_no;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getData_content() {
		return data_content;
	}

	public void setData_content(String data_content) {
		this.data_content = data_content;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}


	public AccessPartner getAccessPartner() {
		return accessPartner;
	}

	public void setAccessPartner(AccessPartner accessPartner) {
		this.accessPartner = accessPartner;
	}
}
