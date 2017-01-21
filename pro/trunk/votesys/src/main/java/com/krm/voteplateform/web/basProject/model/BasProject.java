package com.krm.voteplateform.web.basProject.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.ColumnIgnore;

import com.krm.voteplateform.common.mybatis.plugins.DyTableModel;
import com.krm.voteplateform.web.basProExDetail.model.BasProExDetail;

public class BasProject extends DyTableModel{
	private static final long serialVersionUID = 1L;
	
	
	private List<BasProExDetail> detailForm;
	private String reviewTimeStart;//审议开始时间
	private String reviewTimeEnd;//审议结束时间
	

	public List<BasProExDetail> getDetailForm() {
		return detailForm;
	}

	public void setDetailForm(List<BasProExDetail> detailForm) {
		this.detailForm = detailForm;
	}

	@ColumnIgnore(insert=true,update=true)
	public String getReviewTimeStart() {
		return reviewTimeStart;
	}

	public void setReviewTimeStart(String reviewTimeStart) {
		this.reviewTimeStart = reviewTimeStart;
	}

	@ColumnIgnore(insert=true,update=true)
	public String getReviewTimeEnd() {
		return reviewTimeEnd;
	}

	public void setReviewTimeEnd(String reviewTimeEnd) {
		this.reviewTimeEnd = reviewTimeEnd;
	}
	
	private String projectId;
	private String conferenceId;
	private String specProCode;
	private String otherSysKey;
	private String projectTitle;
	private String kindId;
	private String kindName;
	private String applyDate;
	private String applyOrgId;
	private String applyOrgName;
	private String applyCatet;
	private String handSignFlagId;
	private String approvalDate;
	private String approvalDeptId;
	private String approvalDeptName;
	private String approvalTypeId;
	private String hostDeptId;
	private String hostDeptName;
	private String supportDeptId;
	private String supportDeptName;
	private String reviewTime;
	private String reviewResultId;
	private String reviewNote;
	private String additionalConditions;
	private String currencyId;
	private String monetaryUnitId;
	private BigDecimal appliedAmount;
	private BigDecimal approvedAmount;
	private String description;
	private String codeOa;
	private String delFlag;
	private Timestamp createTime;
	private String createBy;
	private String createName;
	private String createIp;
	private Timestamp updateTime;
	private String updateBy;
	private String updateName;
	private String updateIp;
	private String str1;
	private String str2;
	private String str3;
	private String str4;
	private String str5;
	private String str6;
	private String str7;
	private String str8;
	private String str9;
	private String str10;
	private BigDecimal no1;
	private BigDecimal no2;
	private BigDecimal no3;
	private BigDecimal no4;
	private BigDecimal no5;
	private BigDecimal no6;
	private BigDecimal no7;
	private BigDecimal no8;
	private BigDecimal amount1;
	private BigDecimal amount2;
	private BigDecimal amount3;
	private BigDecimal amount4;
	private BigDecimal amount5;
	private BigDecimal amount6;
	private BigDecimal amount7;
	private BigDecimal amount8;
	private Date datecol1;
	private Date datecol1Start; //备用开始时间
	private Date datecol1End; //备用结束时间
	private Date datecol2;
	private Date datecol2Start; //备用开始时间
	private Date datecol2End; //备用结束时间
	private Date datecol3;
	private Date datecol3Start; //备用开始时间
	private Date datecol3End; //备用结束时间
	private Date datecol4;
	private Date datecol4Start; //备用开始时间
	private Date datecol4End; //备用结束时间
	private Date datecol5;
	private Date datecol5Start; //备用开始时间
	private Date datecol5End; //备用结束时间
	private Date datecol6;
	private Date datecol6Start; //备用开始时间
	private Date datecol6End; //备用结束时间
	private String codename1Id;
	private String codename2Id;
	private String codename3Id;
	private String codename4Id;
	private String codename5Id;
	private String codename6Id;
	private String codename1Name;
	private String codename2Name;
	private String codename3Name;
	private String codename4Name;
	private String codename5Name;
	private String codename6Name;

	private String handSignFlagName;
	private String approvalTypeName;
	private String projectStateId;
	private String projectStateName;
	private String reviewResultName;
	private String currencyName;
	private String monetaryUnitName;
	private Integer projectOrder;
	
	
	@AssignID(value="uuid32",isCanOurDefine=true)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getConferenceId() {
		return conferenceId;
	}

	public void setConferenceId(String conferenceId) {
		this.conferenceId = conferenceId;
	}

	public String getSpecProCode() {
		return specProCode;
	}

	public String getProjectTitle() {
		return projectTitle;
	}

	public void setProjectTitle(String projectTitle) {
		this.projectTitle = projectTitle;
	}

	public String getKindId() {
		return kindId;
	}

	public void setKindId(String kindId) {
		this.kindId = kindId;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyOrgId() {
		return applyOrgId;
	}

	public void setApplyOrgId(String applyOrgId) {
		this.applyOrgId = applyOrgId;
	}

	public String getApplyOrgName() {
		return applyOrgName;
	}

	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}

	public String getApplyCatet() {
		return applyCatet;
	}

	public void setApplyCatet(String applyCatet) {
		this.applyCatet = applyCatet;
	}

	public String getHandSignFlagId() {
		return handSignFlagId;
	}

	public void setHandSignFlagId(String handSignFlagId) {
		this.handSignFlagId = handSignFlagId;
	}

	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getApprovalDeptId() {
		return approvalDeptId;
	}

	public void setApprovalDeptId(String approvalDeptId) {
		this.approvalDeptId = approvalDeptId;
	}

	public String getApprovalDeptName() {
		return approvalDeptName;
	}

	public void setApprovalDeptName(String approvalDeptName) {
		this.approvalDeptName = approvalDeptName;
	}

	public String getApprovalTypeId() {
		return approvalTypeId;
	}

	public void setApprovalTypeId(String approvalTypeId) {
		this.approvalTypeId = approvalTypeId;
	}

	public String getHostDeptId() {
		return hostDeptId;
	}

	public void setHostDeptId(String hostDeptId) {
		this.hostDeptId = hostDeptId;
	}

	public String getHostDeptName() {
		return hostDeptName;
	}

	public void setHostDeptName(String hostDeptName) {
		this.hostDeptName = hostDeptName;
	}

	public String getSupportDeptCodes() {
		return supportDeptId;
	}

	public void setSupportDeptCodes(String supportDeptId) {
		this.supportDeptId = supportDeptId;
	}

	public String getSupportDeptNames() {
		return supportDeptName;
	}

	public void setSupportDeptNames(String supportDeptName) {
		this.supportDeptName = supportDeptName;
	}

	public String getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getReviewResultId() {
		return reviewResultId;
	}

	public void setReviewResultId(String reviewResultId) {
		this.reviewResultId = reviewResultId;
	}

	public String getReviewNote() {
		return reviewNote;
	}

	public void setReviewNote(String reviewNote) {
		this.reviewNote = reviewNote;
	}

	public String getAdditionalConditions() {
		return additionalConditions;
	}

	public void setAdditionalConditions(String additionalConditions) {
		this.additionalConditions = additionalConditions;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getMonetaryUnitId() {
		return monetaryUnitId;
	}

	public void setMonetaryUnitId(String monetaryUnitId) {
		this.monetaryUnitId = monetaryUnitId;
	}

	public BigDecimal getAppliedAmount() {
		return appliedAmount;
	}

	public void setAppliedAmount(BigDecimal appliedAmount) {
		this.appliedAmount = appliedAmount;
	}

	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCodeOa() {
		return codeOa;
	}

	public void setCodeOa(String codeOa) {
		this.codeOa = codeOa;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateIp() {
		return updateIp;
	}

	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public String getStr3() {
		return str3;
	}

	public void setStr3(String str3) {
		this.str3 = str3;
	}

	public String getStr4() {
		return str4;
	}

	public void setStr4(String str4) {
		this.str4 = str4;
	}

	public String getStr5() {
		return str5;
	}

	public void setStr5(String str5) {
		this.str5 = str5;
	}

	public String getStr6() {
		return str6;
	}

	public void setStr6(String str6) {
		this.str6 = str6;
	}

	public String getStr7() {
		return str7;
	}

	public void setStr7(String str7) {
		this.str7 = str7;
	}

	public String getStr8() {
		return str8;
	}

	public void setStr8(String str8) {
		this.str8 = str8;
	}

	public String getStr9() {
		return str9;
	}

	public void setStr9(String str9) {
		this.str9 = str9;
	}

	public String getStr10() {
		return str10;
	}

	public void setStr10(String str10) {
		this.str10 = str10;
	}

	public BigDecimal getNo1() {
		return no1;
	}

	public void setNo1(BigDecimal no1) {
		this.no1 = no1;
	}

	public BigDecimal getNo2() {
		return no2;
	}

	public void setNo2(BigDecimal no2) {
		this.no2 = no2;
	}

	public BigDecimal getNo3() {
		return no3;
	}

	public void setNo3(BigDecimal no3) {
		this.no3 = no3;
	}

	public BigDecimal getNo4() {
		return no4;
	}

	public void setNo4(BigDecimal no4) {
		this.no4 = no4;
	}

	public BigDecimal getNo5() {
		return no5;
	}

	public void setNo5(BigDecimal no5) {
		this.no5 = no5;
	}

	public BigDecimal getNo6() {
		return no6;
	}

	public void setNo6(BigDecimal no6) {
		this.no6 = no6;
	}

	public BigDecimal getNo7() {
		return no7;
	}

	public void setNo7(BigDecimal no7) {
		this.no7 = no7;
	}

	public BigDecimal getNo8() {
		return no8;
	}

	public void setNo8(BigDecimal no8) {
		this.no8 = no8;
	}

	public BigDecimal getAmount1() {
		return amount1;
	}

	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}

	public BigDecimal getAmount2() {
		return amount2;
	}

	public void setAmount2(BigDecimal amount2) {
		this.amount2 = amount2;
	}

	public BigDecimal getAmount3() {
		return amount3;
	}

	public void setAmount3(BigDecimal amount3) {
		this.amount3 = amount3;
	}

	public BigDecimal getAmount4() {
		return amount4;
	}

	public void setAmount4(BigDecimal amount4) {
		this.amount4 = amount4;
	}

	public BigDecimal getAmount5() {
		return amount5;
	}

	public void setAmount5(BigDecimal amount5) {
		this.amount5 = amount5;
	}

	public BigDecimal getAmount6() {
		return amount6;
	}

	public void setAmount6(BigDecimal amount6) {
		this.amount6 = amount6;
	}

	public BigDecimal getAmount7() {
		return amount7;
	}

	public void setAmount7(BigDecimal amount7) {
		this.amount7 = amount7;
	}

	public BigDecimal getAmount8() {
		return amount8;
	}

	public void setAmount8(BigDecimal amount8) {
		this.amount8 = amount8;
	}

	public Date getDatecol1() {
		return datecol1;
	}

	public void setDatecol1(Date datecol1) {
		this.datecol1 = datecol1;
	}

	public Date getDatecol2() {
		return datecol2;
	}

	public void setDatecol2(Date datecol2) {
		this.datecol2 = datecol2;
	}

	public Date getDatecol3() {
		return datecol3;
	}

	public void setDatecol3(Date datecol3) {
		this.datecol3 = datecol3;
	}

	public Date getDatecol4() {
		return datecol4;
	}

	public void setDatecol4(Date datecol4) {
		this.datecol4 = datecol4;
	}

	public Date getDatecol5() {
		return datecol5;
	}

	public void setDatecol5(Date datecol5) {
		this.datecol5 = datecol5;
	}

	public Date getDatecol6() {
		return datecol6;
	}

	public void setDatecol6(Date datecol6) {
		this.datecol6 = datecol6;
	}

	public String getCodename1Id() {
		return codename1Id;
	}

	public void setCodename1Id(String codename1Id) {
		this.codename1Id = codename1Id;
	}

	public String getCodename2Id() {
		return codename2Id;
	}

	public void setCodename2Id(String codename2Id) {
		this.codename2Id = codename2Id;
	}

	public String getCodename3Id() {
		return codename3Id;
	}

	public void setCodename3Id(String codename3Id) {
		this.codename3Id = codename3Id;
	}

	public String getCodename4Id() {
		return codename4Id;
	}

	public void setCodename4Id(String codename4Id) {
		this.codename4Id = codename4Id;
	}

	public String getCodename5Id() {
		return codename5Id;
	}

	public void setCodename5Id(String codename5Id) {
		this.codename5Id = codename5Id;
	}

	public String getCodename6Id() {
		return codename6Id;
	}

	public void setCodename6Id(String codename6Id) {
		this.codename6Id = codename6Id;
	}

	public String getCodename1Name() {
		return codename1Name;
	}

	public void setCodename1Name(String codename1Name) {
		this.codename1Name = codename1Name;
	}

	public String getCodename2Name() {
		return codename2Name;
	}

	public void setCodename2Name(String codename2Name) {
		this.codename2Name = codename2Name;
	}

	public String getCodename3Name() {
		return codename3Name;
	}

	public void setCodename3Name(String codename3Name) {
		this.codename3Name = codename3Name;
	}

	public String getCodename4Name() {
		return codename4Name;
	}

	public void setCodename4Name(String codename4Name) {
		this.codename4Name = codename4Name;
	}

	public String getCodename5Name() {
		return codename5Name;
	}

	public void setCodename5Name(String codename5Name) {
		this.codename5Name = codename5Name;
	}

	public String getCodename6Name() {
		return codename6Name;
	}

	public void setCodename6Name(String codename6Name) {
		this.codename6Name = codename6Name;
	}

	public String getHandSignFlagName() {
		return handSignFlagName;
	}

	public void setHandSignFlagName(String handSignFlagName) {
		this.handSignFlagName = handSignFlagName;
	}

	public String getApprovalTypeName() {
		return approvalTypeName;
	}

	public void setApprovalTypeName(String approvalTypeName) {
		this.approvalTypeName = approvalTypeName;
	}

	public String getProjectStateId() {
		return projectStateId;
	}

	public void setProjectStateId(String projectStateId) {
		this.projectStateId = projectStateId;
	}

	public String getProjectStateName() {
		return projectStateName;
	}

	public void setProjectStateName(String projectStateName) {
		this.projectStateName = projectStateName;
	}

	public String getReviewResultName() {
		return reviewResultName;
	}

	public void setReviewResultName(String reviewResultName) {
		this.reviewResultName = reviewResultName;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getMonetaryUnitName() {
		return monetaryUnitName;
	}

	public void setMonetaryUnitName(String monetaryUnitName) {
		this.monetaryUnitName = monetaryUnitName;
	}

	public Integer getProjectOrder() {
		return projectOrder;
	}

	public void setProjectOrder(Integer projectOrder) {
		this.projectOrder = projectOrder;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setSpecProCode(String specProCode) {
		this.specProCode = specProCode;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol1Start() {
		return datecol1Start;
	}

	public void setDatecol1Start(Date datecol1Start) {
		this.datecol1Start = datecol1Start;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol1End() {
		return datecol1End;
	}

	public void setDatecol1End(Date datecol1End) {
		this.datecol1End = datecol1End;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol2Start() {
		return datecol2Start;
	}

	public void setDatecol2Start(Date datecol2Start) {
		this.datecol2Start = datecol2Start;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol2End() {
		return datecol2End;
	}

	public void setDatecol2End(Date datecol2End) {
		this.datecol2End = datecol2End;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol3Start() {
		return datecol3Start;
	}

	public void setDatecol3Start(Date datecol3Start) {
		this.datecol3Start = datecol3Start;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol3End() {
		return datecol3End;
	}

	public void setDatecol3End(Date datecol3End) {
		this.datecol3End = datecol3End;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol4Start() {
		return datecol4Start;
	}

	public void setDatecol4Start(Date datecol4Start) {
		this.datecol4Start = datecol4Start;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol4End() {
		return datecol4End;
	}

	public void setDatecol4End(Date datecol4End) {
		this.datecol4End = datecol4End;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol5Start() {
		return datecol5Start;
	}

	public void setDatecol5Start(Date datecol5Start) {
		this.datecol5Start = datecol5Start;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol5End() {
		return datecol5End;
	}

	public void setDatecol5End(Date datecol5End) {
		this.datecol5End = datecol5End;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol6Start() {
		return datecol6Start;
	}

	public void setDatecol6Start(Date datecol6Start) {
		this.datecol6Start = datecol6Start;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol6End() {
		return datecol6End;
	}

	public void setDatecol6End(Date datecol6End) {
		this.datecol6End = datecol6End;
	}

	@Override
	public String toString() {
		return "BasProject [detailForm=" + detailForm + ", reviewTimeStart=" + reviewTimeStart + ", reviewTimeEnd="
				+ reviewTimeEnd + ", projectId=" + projectId + ", conferenceId=" + conferenceId + ", specProCode="
				+ specProCode + ", projectTitle=" + projectTitle + ", kindId=" + kindId + ", kindName=" + kindName
				+ ", applyDate=" + applyDate + ", applyOrgId=" + applyOrgId + ", applyOrgName=" + applyOrgName
				+ ", applyCatet=" + applyCatet + ", handSignFlagId=" + handSignFlagId + ", approvalDate=" + approvalDate
				+ ", approvalDeptId=" + approvalDeptId + ", approvalDeptName=" + approvalDeptName + ", approvalTypeId="
				+ approvalTypeId + ", hostDeptId=" + hostDeptId + ", hostDeptName=" + hostDeptName
				+ ", supportDeptId=" + supportDeptId + ", supportDeptName=" + supportDeptName + ", reviewTime="
				+ reviewTime + ", reviewResultId=" + reviewResultId + ", reviewNote=" + reviewNote
				+ ", additionalConditions=" + additionalConditions + ", currencyId=" + currencyId + ", monetaryUnitId="
				+ monetaryUnitId + ", appliedAmount=" + appliedAmount + ", approvedAmount=" + approvedAmount
				+ ", description=" + description + ", codeOa=" + codeOa + ", delFlag=" + delFlag + ", createTime="
				+ createTime + ", createBy=" + createBy + ", createName=" + createName + ", createIp=" + createIp
				+ ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", updateName=" + updateName + ", updateIp="
				+ updateIp + ", str1=" + str1 + ", str2=" + str2 + ", str3=" + str3 + ", str4=" + str4 + ", str5="
				+ str5 + ", str6=" + str6 + ", str7=" + str7 + ", str8=" + str8 + ", str9=" + str9 + ", str10=" + str10
				+ ", no1=" + no1 + ", no2=" + no2 + ", no3=" + no3 + ", no4=" + no4 + ", no5=" + no5 + ", no6=" + no6
				+ ", no7=" + no7 + ", no8=" + no8 + ", amount1=" + amount1 + ", amount2=" + amount2 + ", amount3="
				+ amount3 + ", amount4=" + amount4 + ", amount5=" + amount5 + ", amount6=" + amount6 + ", amount7="
				+ amount7 + ", amount8=" + amount8 + ", datecol1=" + datecol1 + ", datecol1Start=" + datecol1Start
				+ ", datecol1End=" + datecol1End + ", datecol2=" + datecol2 + ", datecol2Start=" + datecol2Start
				+ ", datecol2End=" + datecol2End + ", datecol3=" + datecol3 + ", datecol3Start=" + datecol3Start
				+ ", datecol3End=" + datecol3End + ", datecol4=" + datecol4 + ", datecol4Start=" + datecol4Start
				+ ", datecol4End=" + datecol4End + ", datecol5=" + datecol5 + ", datecol5Start=" + datecol5Start
				+ ", datecol5End=" + datecol5End + ", datecol6=" + datecol6 + ", datecol6Start=" + datecol6Start
				+ ", datecol6End=" + datecol6End + ", codename1Id=" + codename1Id + ", codename2Id=" + codename2Id
				+ ", codename3Id=" + codename3Id + ", codename4Id=" + codename4Id + ", codename5Id=" + codename5Id
				+ ", codename6Id=" + codename6Id + ", codename1Name=" + codename1Name + ", codename2Name="
				+ codename2Name + ", codename3Name=" + codename3Name + ", codename4Name=" + codename4Name
				+ ", codename5Name=" + codename5Name + ", codename6Name=" + codename6Name + ", handSignFlagName="
				+ handSignFlagName + ", approvalTypeName=" + approvalTypeName + ", projectStateId=" + projectStateId
				+ ", projectStateName=" + projectStateName + ", reviewResultName=" + reviewResultName
				+ ", currencyName=" + currencyName + ", monetaryUnitName=" + monetaryUnitName + ", projectOrder="
				+ projectOrder + "]";
	}

	public String getOtherSysKey() {
		return otherSysKey;
	}

	public void setOtherSysKey(String otherSysKey) {
		this.otherSysKey = otherSysKey;
	}

	
	
	
	
	
	

	

	

}
