package com.krm.voteplateform.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings({"unchecked"})
public class TreeUtils {
	
	
	/**
	 * 转换成List形式树结构 (如果是缓存的list，请务必深度copy一个)
	* @param list
	* @return
	 */
	public static <T extends TreeEntity> List<T> toTreeNodeList(List<T> source,Class<T> bean){
		
		final Map<String, T> nodes = new HashMap<String, T>();
		
		//深度copy一个，防止源list内部结构改变
		List<T> list = Collections3.copyTo(source, bean);
		
		
		//将list放到children的children下面
		final TreeEntity root = new TreeEntity();
		root.put("level", 0);
		//所有节点记录下来
		ArrayList<T> arrayList = new ArrayList<T>();
				for(T node : list){
					node.put("level", -1);
					node.put("hasChild", false);
					node.put("children", new ArrayList<T>());
				    nodes.put(node.getId(), node);//第一级
				    arrayList.add(node);
				}
		root.put("children", arrayList);//[{},{}]
		root.put("hasChild", true);
		nodes.put("0", (T) root);//0根
		
	
		
		for(T node : list){
		    final T parent = nodes.get(node.getParentid());
			if(parent == null){
				((ArrayList<T>)root.get("children")).add(node);
				continue;
			//throw new RuntimeException("子节点有父级id，却没有找到此父级的对象");
			}else{
				 //添加子节点
				((List<T>)parent.get("children")).add(node);
			}
	}
		
		/*int max = 0;
		for(T node : list){
			max = Math.max(resolveLevel(node, nodes), max);
		}*/

		return (List<T>) root.get("children");
	}
	
	//递归找level
	private static <T extends TreeEntity> int resolveLevel(final T node, final Map<String, T> nodes){
		//System.out.println(node.getIntValue("level"));
		int level = 1;
		//第一次循环根节点秘书端根节点 level=-1
		if(node != null){
		    	level = node.getIntValue("level");
				//level=node.getParentIds().split(",").length;
				if(level==-2){//其它
					throw new RuntimeException("Node循环了, id=" + node.getId());
				}
				if(level==-1){//是孩子节点
					//node.put("level", -2);
					//level = resolveLevel(nodes.get(node.getParentid()),nodes) +1;
					node.put("level", level);
				}else{//是根节点
					node.put("hasChild", true);
				}
		}
	    return level;
	}
	
}
