package com.ailk.sets.platform;

public class AA {
public static void main(String[] args) {
	String a = "abc";
	String b = "abc";
	System.out.println(a==b);
	b = "def";
	System.out.println(a == b);
	a = "def";
	System.out.println(a == b);
}
}
