package com.krm.voteplateform.common.model;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * BootstrapTable实体类
 * 
 * @author JohnnyZhang
 */
public class BootstrapTable implements Serializable {

	private static final long serialVersionUID = 1L;

	private String iconSize = "sm";//

	private String url = "${ctxPath}/";// 请求URL

	private boolean striped = true;// 隔行换色

	private boolean pagination = false;// 是否分页

	private int pageNumber = 1;// 首页页码

	private int pageSize = 10;// 页码显示条数

	private String sidePagination = "server";

	private String contentType = "application/x-www-form-urlencoded";

	private String dataType = "json";

	private String method = "post";

	private List<BootstrapTableColumn> columns = Lists.newArrayList();

	private List<QueryParam> queryParams = Lists.newArrayList();

	/**
	 * @return the iconSize
	 */
	public String getIconSize() {
		return iconSize;
	}

	/**
	 * @param iconSize the inconSize to set
	 */
	public void setIconSize(String iconSize) {
		this.iconSize = iconSize;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the striped
	 */
	public boolean isStriped() {
		return striped;
	}

	/**
	 * @param striped the striped to set
	 */
	public void setStriped(boolean striped) {
		this.striped = striped;
	}

	/**
	 * @return the pagination
	 */
	public boolean isPagination() {
		return pagination;
	}

	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the sidePagination
	 */
	public String getSidePagination() {
		return sidePagination;
	}

	/**
	 * @param sidePagination the sidePagination to set
	 */
	public void setSidePagination(String sidePagination) {
		this.sidePagination = sidePagination;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the columns
	 */
	public List<BootstrapTableColumn> getColumns() {
		return columns;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(List<BootstrapTableColumn> columns) {
		this.columns = columns;
	}

	/**
	 * @return the queryParams
	 */
	public List<QueryParam> getQueryParams() {
		return queryParams;
	}

	/**
	 * @param queryParams the queryParams to set
	 */
	public void setQueryParams(List<QueryParam> queryParams) {
		this.queryParams = queryParams;
	}

	public class BootstrapTableColumn {
		private String field;
		private String title;
		private boolean sortable = false;

		/**
		 * @return the field
		 */
		public String getField() {
			return field;
		}

		/**
		 * @param field the field to set
		 */
		public void setField(String field) {
			this.field = field;
		}

		/**
		 * @return the title
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * @param title the title to set
		 */
		public void setTitle(String title) {
			this.title = title;
		}

		/**
		 * @return the sortable
		 */
		public boolean isSortable() {
			return sortable;
		}

		/**
		 * @param sortable the sortable to set
		 */
		public void setSortable(boolean sortable) {
			this.sortable = sortable;
		}
	}

	public class QueryParam {
		private String key;
		private String value;

		/**
		 * @return the key
		 */
		public String getKey() {
			return key;
		}

		/**
		 * @param key the key to set
		 */
		public void setKey(String key) {
			this.key = key;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param value the value to set
		 */
		public void setValue(String value) {
			this.value = value;
		}
	}
}
