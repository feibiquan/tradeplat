package com.xfpay.entity;


import com.xfpay.annotation.ValidField;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrganizationInfo implements Serializable {
	private static final long serialVersionUID = 1L;
    private Long organizationId;
    @ValidField("机构")
    private String organizationName;
    private String networkUrl;
    @ValidField("所属省份")
    private String provinceCode;
    @ValidField("所属城市")
    private String cityCode;
    @ValidField("所属区域")
    private String districtCode;
    @ValidField("详细地址")
    private String address;
    @ValidField("企业座机电话")
    private String enterprisePhone;
    @ValidField("企业经营信息")
    private String business;
    @ValidField("负责人")
    private String responsiblePerson;
    @ValidField("负责人手机号码")
    private String responsiblePersonPhone;
    @ValidField("负责人身份证号码")
    private String responsiblePersonIdcard;
    @ValidField("企业邮箱")
    private String email;
    private Long parentId;
    private Date createTime;
//    private FlowStatus flowStatus;// 审核流程状态（初审）
//    private OrgStatus status;
    @ValidField("机构简称")
    private String institutionShortname;
//    @ValidField("机构类型")
//    private InstitutionType institutionType;
    private String wxChannelNo;
    private String licenseNumber;
    private String licenseImg;
    private String idcardImgUp;
    private String idcardImgDown;
    private String customCode;
    private String organizationStorey;
    private Integer organizationLevel;
    private String settleAccount;
    private String settleAccountName;
    private String settleAccountIdcard;
    private String settleAccountPhone;
    private String settleAccountBank;
//    private AccountType settleAccountType;
    private Date lastUpdateTime;
    private Long createUserId;
    private Long checkUserId;	//初审人员
    private Long reCheckUserId;	//复核人员
    private String description;//备注（审核原因）
    /**
     * 非库表字段
     */
    private Long cascadeOrgId;//所属机构
    private String parentName;//上级机构名称
    private String settleAccountBankName;
//    private FlowStatus flowStatus2;// 审核流程状态（复审）
    private String createUserName;//创建人
    private String checkUserName;	//初审人
    private String reCheckUserName;	//复核人

    private String provinceName;
    private String cityName;
    private String districtName;

//    private List<OrganizationChannelRate> feeList;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getNetworkUrl() {
		return networkUrl;
	}

	public void setNetworkUrl(String networkUrl) {
		this.networkUrl = networkUrl;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEnterprisePhone() {
		return enterprisePhone;
	}

	public void setEnterprisePhone(String enterprisePhone) {
		this.enterprisePhone = enterprisePhone;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getResponsiblePersonPhone() {
		return responsiblePersonPhone;
	}

	public void setResponsiblePersonPhone(String responsiblePersonPhone) {
		this.responsiblePersonPhone = responsiblePersonPhone;
	}

	public String getResponsiblePersonIdcard() {
		return responsiblePersonIdcard;
	}

	public void setResponsiblePersonIdcard(String responsiblePersonIdcard) {
		this.responsiblePersonIdcard = responsiblePersonIdcard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getInstitutionShortname() {
		return institutionShortname;
	}

	public void setInstitutionShortname(String institutionShortname) {
		this.institutionShortname = institutionShortname;
	}

	public String getWxChannelNo() {
		return wxChannelNo;
	}

	public void setWxChannelNo(String wxChannelNo) {
		this.wxChannelNo = wxChannelNo;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getLicenseImg() {
		return licenseImg;
	}

	public void setLicenseImg(String licenseImg) {
		this.licenseImg = licenseImg;
	}

	public String getIdcardImgUp() {
		return idcardImgUp;
	}

	public void setIdcardImgUp(String idcardImgUp) {
		this.idcardImgUp = idcardImgUp;
	}

	public String getIdcardImgDown() {
		return idcardImgDown;
	}

	public void setIdcardImgDown(String idcardImgDown) {
		this.idcardImgDown = idcardImgDown;
	}

	public String getCustomCode() {
		return customCode;
	}

	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}

	public String getOrganizationStorey() {
		return organizationStorey;
	}

	public void setOrganizationStorey(String organizationStorey) {
		this.organizationStorey = organizationStorey;
	}

	public Integer getOrganizationLevel() {
		return organizationLevel;
	}

	public void setOrganizationLevel(Integer organizationLevel) {
		this.organizationLevel = organizationLevel;
	}

	public String getSettleAccount() {
		return settleAccount;
	}

	public void setSettleAccount(String settleAccount) {
		this.settleAccount = settleAccount;
	}

	public String getSettleAccountName() {
		return settleAccountName;
	}

	public void setSettleAccountName(String settleAccountName) {
		this.settleAccountName = settleAccountName;
	}

	public String getSettleAccountIdcard() {
		return settleAccountIdcard;
	}

	public void setSettleAccountIdcard(String settleAccountIdcard) {
		this.settleAccountIdcard = settleAccountIdcard;
	}

	public String getSettleAccountPhone() {
		return settleAccountPhone;
	}

	public void setSettleAccountPhone(String settleAccountPhone) {
		this.settleAccountPhone = settleAccountPhone;
	}

	public String getSettleAccountBank() {
		return settleAccountBank;
	}

	public void setSettleAccountBank(String settleAccountBank) {
		this.settleAccountBank = settleAccountBank;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(Long checkUserId) {
		this.checkUserId = checkUserId;
	}

	public Long getReCheckUserId() {
		return reCheckUserId;
	}

	public void setReCheckUserId(Long reCheckUserId) {
		this.reCheckUserId = reCheckUserId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCascadeOrgId() {
		return cascadeOrgId;
	}

	public void setCascadeOrgId(Long cascadeOrgId) {
		this.cascadeOrgId = cascadeOrgId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getSettleAccountBankName() {
		return settleAccountBankName;
	}

	public void setSettleAccountBankName(String settleAccountBankName) {
		this.settleAccountBankName = settleAccountBankName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}

	public String getReCheckUserName() {
		return reCheckUserName;
	}

	public void setReCheckUserName(String reCheckUserName) {
		this.reCheckUserName = reCheckUserName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
}