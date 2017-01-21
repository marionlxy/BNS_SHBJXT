package com.krm.voteplateform.web.voteplan.model;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AssignID;

import com.krm.voteplateform.common.base.entity.BaseEntity;

/**
 * 
 * @author
 */
public class PtVotePlan extends BaseEntity {

	private static final long serialVersionUID = 5880127706554334449L;

	private String id; // id <主键id>

	private String votePlanName; // voteplan_name <名称>

	private String votePlanSummary; // voteplan_summary <方案摘要>

	private String adoptFormula; // adopt_formula <通过公式>

	private String adoptFormulaMark; // adopt_formula_mark <通过公式解释>

	private String againFormula; // again_formula <再议公式>

	private String againFormulaMark; // again_formula_mark <再议公式解释>

	private int state; // state <状态>

	private String delFlag; // del_flag <删除标记>

	private Date createTime; // create_time <创建时间>

	private String createBy; // update_by <更改人>

	private Date updateTime; // update_time <更改时间>

	private String updateBy; // update_by <更改人>

	public static final String TYPE_ACCESS = "1"; // 操作日志
	public static final String TYPE_EXCEPTION = "2"; // 异常日志

	@AssignID("uuid32")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getVotePlanName() {
		return votePlanName;
	}

	public void setVotePlanName(String votePlanName) {
		this.votePlanName = votePlanName;
	}

	public String getVotePlanSummary() {
		return votePlanSummary;
	}

	public void setVotePlanSummary(String votePlanSummary) {
		this.votePlanSummary = votePlanSummary;
	}

	public String getAdoptFormula() {
		return adoptFormula;
	}

	public void setAdoptFormula(String adoptFormula) {
		this.adoptFormula = adoptFormula;
	}

	public String getAdoptFormulaMark() {
		return adoptFormulaMark;
	}

	public void setAdoptFormulaMark(String adoptFormulaMark) {
		this.adoptFormulaMark = adoptFormulaMark;
	}

	public String getAgainFormula() {
		return againFormula;
	}

	public void setAgainFormula(String againFormula) {
		this.againFormula = againFormula;
	}

	public String getAgainFormulaMark() {
		return againFormulaMark;
	}

	public void setAgainFormulaMark(String againFormulaMark) {
		this.againFormulaMark = againFormulaMark;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PtCommission[");
		sb.append("id=");
		sb.append(id);
		sb.append(",votePlanName=");
		sb.append(votePlanName);
		sb.append(",votePlanSummary=");
		sb.append(votePlanSummary);
		sb.append(",adoptFormula=");
		sb.append(adoptFormula);
		sb.append(",adoptFormulaMark=");
		sb.append(adoptFormulaMark);
		sb.append(",againFormula=");
		sb.append(againFormula);
		sb.append(",againFormulaMark=");
		sb.append(againFormulaMark);
		sb.append(",state=");
		sb.append(state);
		sb.append(",delFlag=");
		sb.append(delFlag);
		sb.append(",createTime=");
		sb.append(createTime);
		sb.append(",createBy=");
		sb.append(createBy);
		sb.append(",updateTime=");
		sb.append(updateTime);
		sb.append(",updateBy=");
		sb.append(updateBy);
		sb.append("]");
		return sb.toString();

	}

}
