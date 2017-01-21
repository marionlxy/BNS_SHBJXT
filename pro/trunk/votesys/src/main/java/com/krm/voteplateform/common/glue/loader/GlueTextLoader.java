package com.krm.voteplateform.common.glue.loader;

import java.util.List;

import com.krm.voteplateform.common.glue.model.PtDynamicSource;

/**
 * 取得Glue代码接口
 * 
 * @author JohnnyZhang
 */
public interface GlueTextLoader {

	/**
	 * 取得Glue代码
	 * 
	 * @param id 主键
	 * @return
	 */
	public PtDynamicSource load(String id);

	public int insert(PtDynamicSource pds);

	public int update(PtDynamicSource pds);

	public List<PtDynamicSource> getListByType(String type);
}
