package com.xfpay.entity;

public class AccessPartner {
    private Long organizationId;
    private String publicKey;
    private String privateKey;
    private String patnerPublicKey;
    private String transNoticeUrl;
    private String checkNoticeUrl;
    private String callbackUrl;
    private String remark;
    private String encryptType;
    private String md5Key;
    private String weixinNoticeUrl;
    private String d0SettleNoticeUrl;

    /**
     * 非库表字段
     * @return
     */
    private String organizationName;

	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getPatnerPublicKey() {
		return patnerPublicKey;
	}
	public void setPatnerPublicKey(String patnerPublicKey) {
		this.patnerPublicKey = patnerPublicKey;
	}
	public String getTransNoticeUrl() {
		return transNoticeUrl;
	}
	public void setTransNoticeUrl(String transNoticeUrl) {
		this.transNoticeUrl = transNoticeUrl;
	}
	public String getCheckNoticeUrl() {
		return checkNoticeUrl;
	}
	public void setCheckNoticeUrl(String checkNoticeUrl) {
		this.checkNoticeUrl = checkNoticeUrl;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEncryptType() {
		return encryptType;
	}
	public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}
	public String getMd5Key() {
		return md5Key;
	}
	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}
	public String getWeixinNoticeUrl() {
		return weixinNoticeUrl;
	}
	public void setWeixinNoticeUrl(String weixinNoticeUrl) {
		this.weixinNoticeUrl = weixinNoticeUrl;
	}
	public String getD0SettleNoticeUrl() {
		return d0SettleNoticeUrl;
	}
	public void setD0SettleNoticeUrl(String d0SettleNoticeUrl) {
		this.d0SettleNoticeUrl = d0SettleNoticeUrl;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
}