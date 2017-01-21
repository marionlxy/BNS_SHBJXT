package com.krm.voteplateform.web.basProject.model;

import org.beetl.sql.core.annotatoin.ColumnIgnore;

public class BasProjectExp extends BasProject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String reviewTimeStart;
	public String reviewTimeEnd;
	
	/*7.3. @ColumnIgnore

	在beetlsql 内置的insert或者update方法的时候，使用此注解的字段（作用于getter方法）将根据注解的属性来决定是否忽略此字段

	@ColumnIgnore(insert=true,update=false)
	public Date getBir(){
		return  bir;
	}*/

	//如上例子，插入的时候忽略bir属性（往往是因为数据库指定了默认值为当前时间），更新的时候不能忽略 @ColumnIgnore的insert默认是true，update是false，因此也可以直接用 @ColumnIgnore()
	
	@ColumnIgnore(insert=false,update=false)
	public String getReviewTimeStart() {
		return reviewTimeStart;
	}
	public void setReviewTimeStart(String reviewTimeStart) {
		this.reviewTimeStart = reviewTimeStart;
	}
	@ColumnIgnore(insert=false,update=false)
	public String getReviewTimeEnd() {
		return reviewTimeEnd;
	}
	public void setReviewTimeEnd(String reviewTimeEnd) {
		this.reviewTimeEnd = reviewTimeEnd;
	}
	@Override
	public String toString() {
		return "BasProjectExp [reviewTimeStart=" + reviewTimeStart + ", reviewTimeEnd=" + reviewTimeEnd + "]";
	}
	
	
	
	
	
	
	
	

}
