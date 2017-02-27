package com.zuidaima.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DictContent {

	
	
	public static Map<String,List<String[]>> map = new HashMap<String,List<String[]>>();
	
	private DictContent(){
		
		
		List<String[]> dictList = new ArrayList<String[]>();
		
		String[] array = new String[]{"1","男"};
		String[] array2 = new String[]{"2","女"};
		
		dictList.add(array);
		dictList.add(array2);
		
		map.put("xb01", dictList);
		
		
		dictList = new ArrayList<String[]>();
		
		String[] xl01 = new String[]{"1","专科"};
		String[] xl02 = new String[]{"2","本科"};
		String[] xl03 = new String[]{"2","硕士"};
		String[] xl04 = new String[]{"2","博士"};
		String[] xl05 = new String[]{"2","博士后"};
		
		dictList.add(xl01);
		dictList.add(xl02);
		dictList.add(xl03);
		dictList.add(xl04);
		dictList.add(xl05);
		map.put("xl01", dictList);

		
		
		
		
	}
	
	private static class SingletonHolder
	{
		static DictContent content = new DictContent();
	}
	
	
	public static DictContent getInstance(){
		
		
		return SingletonHolder.content;
	}
	
	
}
