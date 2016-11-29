package com.demo.test.example;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:Test <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date:     2016-11-28 下午5:33:09 <br/>
 * @author   cgw 
 */
public class Test {
	public static void main(String[] args) {
		List list = new ArrayList();
		list.add(0, "a");
		list.add(0, "b");
		System.out.println(list.get(1));
	}
}

