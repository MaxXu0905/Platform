package com.ailk.sets.platform.dao.impl;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ailk.sets.platform.util.PaperCreateUtils;


public class Test1 {
	@Test
	public void test1() throws Exception{
    	 /* Map<Integer,Object> a = new HashMap<Integer,Object>();
    	  a.put(1, 1);
//    	  a.put("2", 2);
    	  System.out.println(a.get(1));
    	  System.out.println(a.get("2"));
    	  System.out.println(a.get(new Integer(1)));
    	*/
		  String a = "aaa";
		  String b ="aaa";
		  System.out.println(a.equals(b));
		  System.out.println(a.hashCode() == b.hashCode());
		  
		  Integer c = 2;
		  Integer d = 2;
		  System.out.println(c.equals(d));
		  System.out.println(c.hashCode() == d.hashCode());
		 /* Map<Integer, List<QbSkill>> numberToSkills = new TreeMap<Integer, List<QbSkill>>();
		  for(int i=0;i<5;i++){
			  int degreeLow = 2;
	    	  List<QbSkill> qbSkills2 = numberToSkills.get(degreeLow);
	    	  System.out.println(numberToSkills.get(degreeLow));
			   if (qbSkills2 == null) {
				   System.out.println("nbew");
					qbSkills2 = new ArrayList<QbSkill>();
					numberToSkills.put(degreeLow, qbSkills2);
				}
				System.out.println( numberToSkills.get(degreeLow));
		  }*/
		  
		  List<String> list = new ArrayList<String>();
		  list.add("abc");
		  list.add("dfg");
		  
		  List<List<String>>  lists = PaperCreateUtils.getSubSet(list, 2);
		  List<String> sub1 = lists.get(0);
		  System.out.println(list.size());
		  System.out.println(sub1.size());
		  list.removeAll(sub1);
		  System.out.println("deleted....");
		  System.out.println(list.size());
		  System.out.println(sub1.size());
		  
		/*  List<String>  list2 = new ArrayList<String>();
		  
		  list2.add(list.get(0));
		  
		  list.removeAll(list2);
		  
		  System.out.println(list2.size());
		  System.out.println(list.size());*/
    	  
	}
}
