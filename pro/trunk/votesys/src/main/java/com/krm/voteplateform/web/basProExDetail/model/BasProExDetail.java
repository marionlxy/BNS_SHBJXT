package com.krm.voteplateform.web.basProExDetail.model;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.ColumnIgnore;

import com.krm.voteplateform.common.mybatis.plugins.DyTableModel;

public class BasProExDetail extends DyTableModel{

	/**
	 * 项目扩展明细表字典
	 */
	private static final long serialVersionUID = 1L;

	
	
	private String id;
	private String extendGroupId;
	private String projectId;
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
	private Integer no1;
	private Integer no2;
	private Integer no3;
	private Integer no4;
	private Integer no5;
	private Integer no6;
	private Integer no7;
	private Integer no8;
	private Date datecol1;
	private Date datecol1Start;  //开始
	private Date datecol1End;    //结束
	private Date datecol2;
	private Date datecol2Start; //开始
	private Date datecol2End;
	private Date datecol3;
	private Date datecol3Start; //开始
	private Date datecol3End;
	private Date datecol4;
	private Date datecol4Start; //开始
	private Date datecol4End;
	private Date datecol5;
	private Date datecol5Start;
	private Date datecol5End;
	private Date datecol6;
	private Date datecol6Start;
	private Date datecol6End;
	private Date datecol7;
	private Date datecol7Start;
	private Date datecol7End;
	private Date datecol8;
	private Date datecol8Start;
	private Date datecol8End;
	private Date datecol9;
	private Date datecol9Start;
	private Date datecol9End;
	private Date datecol10;
	private Date datecol10Start;
	private Date datecol10End;
	private String codename1Id;
	private String codename2Id;
	private String codename3Id;
	private String codename4Id;
	private String codename5Id;
	private String codename6Id;
	private String codename7Id;
	private String codename8Id;
	private String codename9Id;
	private String codename10Id;
	private String codename1Name;
	private String codename2Name;
	private String codename3Name;
	private String codename4Name;
	private String codename5Name;
	private String codename6Name;
	private String codename7Name;
	private String codename8Name;
	private String codename9Name;
	private String codename10Name;
	private String delFlag;
	private Timestamp  createTime;
	private String createBy;
	private String createName;
	private String createIp;
	private Timestamp updateTime;
	private String updateBy;
	private String updateName;
	private String updateIp;
	
	@AssignID("uuid32")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExtendGroupId() {
		return extendGroupId;
	}
	public void setExtendGroupId(String extendGroupId) {
		this.extendGroupId = extendGroupId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
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
	public Integer getNo1() {
		return no1;
	}
	public void setNo1(Integer no1) {
		this.no1 = no1;
	}
	public Integer getNo2() {
		return no2;
	}
	public void setNo2(Integer no2) {
		this.no2 = no2;
	}
	public Integer getNo3() {
		return no3;
	}
	public void setNo3(Integer no3) {
		this.no3 = no3;
	}
	public Integer getNo4() {
		return no4;
	}
	public void setNo4(Integer no4) {
		this.no4 = no4;
	}
	public Integer getNo5() {
		return no5;
	}
	public void setNo5(Integer no5) {
		this.no5 = no5;
	}
	public Integer getNo6() {
		return no6;
	}
	public void setNo6(Integer no6) {
		this.no6 = no6;
	}
	public Integer getNo7() {
		return no7;
	}
	public void setNo7(Integer no7) {
		this.no7 = no7;
	}
	public Integer getNo8() {
		return no8;
	}
	public void setNo8(Integer no8) {
		this.no8 = no8;
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
	public Date getDatecol7() {
		return datecol7;
	}
	public void setDatecol7(Date datecol7) {
		this.datecol7 = datecol7;
	}
	public Date getDatecol8() {
		return datecol8;
	}
	public void setDatecol8(Date datecol8) {
		this.datecol8 = datecol8;
	}
	public Date getDatecol9() {
		return datecol9;
	}
	public void setDatecol9(Date datecol9) {
		this.datecol9 = datecol9;
	}
	public Date getDatecol10() {
		return datecol10;
	}
	public void setDatecol10(Date datecol10) {
		this.datecol10 = datecol10;
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
	public String getCodename7Id() {
		return codename7Id;
	}
	public void setCodename7Id(String codename7Id) {
		this.codename7Id = codename7Id;
	}
	public String getCodename8Id() {
		return codename8Id;
	}
	public void setCodename8Id(String codename8Id) {
		this.codename8Id = codename8Id;
	}
	public String getCodename9Id() {
		return codename9Id;
	}
	public void setCodename9Id(String codename9Id) {
		this.codename9Id = codename9Id;
	}
	public String getCodename10Id() {
		return codename10Id;
	}
	public void setCodename10Id(String codename10Id) {
		this.codename10Id = codename10Id;
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
	public String getCodename7Name() {
		return codename7Name;
	}
	public void setCodename7Name(String codename7Name) {
		this.codename7Name = codename7Name;
	}
	public String getCodename8Name() {
		return codename8Name;
	}
	public void setCodename8Name(String codename8Name) {
		this.codename8Name = codename8Name;
	}
	public String getCodename9Name() {
		return codename9Name;
	}
	public void setCodename9Name(String codename9Name) {
		this.codename9Name = codename9Name;
	}
	public String getCodename10Name() {
		return codename10Name;
	}
	public void setCodename10Name(String codename10Name) {
		this.codename10Name = codename10Name;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol7Start() {
		return datecol7Start;
	}
	public void setDatecol7Start(Date datecol7Start) {
		this.datecol7Start = datecol7Start;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol7End() {
		return datecol7End;
	}
	public void setDatecol7End(Date datecol7End) {
		this.datecol7End = datecol7End;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol8Start() {
		return datecol8Start;
	}
	public void setDatecol8Start(Date datecol8Start) {
		this.datecol8Start = datecol8Start;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol8End() {
		return datecol8End;
	}
	public void setDatecol8End(Date datecol8End) {
		this.datecol8End = datecol8End;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol9Start() {
		return datecol9Start;
	}
	public void setDatecol9Start(Date datecol9Start) {
		this.datecol9Start = datecol9Start;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol9End() {
		return datecol9End;
	}
	public void setDatecol9End(Date datecol9End) {
		this.datecol9End = datecol9End;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol10Start() {
		return datecol10Start;
	}
	public void setDatecol10Start(Date datecol10Start) {
		this.datecol10Start = datecol10Start;
	}
	@ColumnIgnore(insert=true,update=true)
	public Date getDatecol10End() {
		return datecol10End;
	}
	public void setDatecol10End(Date datecol10End) {
		this.datecol10End = datecol10End;
	}
	@Override
	public String toString() {
		return "BasProExDetail [id=" + id + ", extendGroupId=" + extendGroupId + ", projectId=" + projectId + ", str1="
				+ str1 + ", str2=" + str2 + ", str3=" + str3 + ", str4=" + str4 + ", str5=" + str5 + ", str6=" + str6
				+ ", str7=" + str7 + ", str8=" + str8 + ", str9=" + str9 + ", str10=" + str10 + ", no1=" + no1
				+ ", no2=" + no2 + ", no3=" + no3 + ", no4=" + no4 + ", no5=" + no5 + ", no6=" + no6 + ", no7=" + no7
				+ ", no8=" + no8 + ", datecol1=" + datecol1 + ", datecol1Start=" + datecol1Start + ", datecol1End="
				+ datecol1End + ", datecol2=" + datecol2 + ", datecol2Start=" + datecol2Start + ", datecol2End="
				+ datecol2End + ", datecol3=" + datecol3 + ", datecol3Start=" + datecol3Start + ", datecol3End="
				+ datecol3End + ", datecol4=" + datecol4 + ", datecol4Start=" + datecol4Start + ", datecol4End="
				+ datecol4End + ", datecol5=" + datecol5 + ", datecol5Start=" + datecol5Start + ", datecol5End="
				+ datecol5End + ", datecol6=" + datecol6 + ", datecol6Start=" + datecol6Start + ", datecol6End="
				+ datecol6End + ", datecol7=" + datecol7 + ", datecol7Start=" + datecol7Start + ", datecol7End="
				+ datecol7End + ", datecol8=" + datecol8 + ", datecol8Start=" + datecol8Start + ", datecol8End="
				+ datecol8End + ", datecol9=" + datecol9 + ", datecol9Start=" + datecol9Start + ", datecol9End="
				+ datecol9End + ", datecol10=" + datecol10 + ", datecol10Start=" + datecol10Start + ", datecol10End="
				+ datecol10End + ", codename1Id=" + codename1Id + ", codename2Id=" + codename2Id + ", codename3Id="
				+ codename3Id + ", codename4Id=" + codename4Id + ", codename5Id=" + codename5Id + ", codename6Id="
				+ codename6Id + ", codename7Id=" + codename7Id + ", codename8Id=" + codename8Id + ", codename9Id="
				+ codename9Id + ", codename10Id=" + codename10Id + ", codename1Name=" + codename1Name
				+ ", codename2Name=" + codename2Name + ", codename3Name=" + codename3Name + ", codename4Name="
				+ codename4Name + ", codename5Name=" + codename5Name + ", codename6Name=" + codename6Name
				+ ", codename7Name=" + codename7Name + ", codename8Name=" + codename8Name + ", codename9Name="
				+ codename9Name + ", codename10Name=" + codename10Name + ", delFlag=" + delFlag + ", createTime="
				+ createTime + ", createBy=" + createBy + ", createName=" + createName + ", createIp=" + createIp
				+ ", updateTime=" + updateTime + ", updateBy=" + updateBy + ", updateName=" + updateName + ", updateIp="
				+ updateIp + "]";
	}


	
	
	
	
	
	
	

}
