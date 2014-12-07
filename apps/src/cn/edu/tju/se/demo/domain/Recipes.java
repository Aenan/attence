package cn.edu.tju.se.demo.domain;

import java.util.ArrayList;
import java.util.List;


public class Recipes {

	public static long SN = 0L;
	public static List<Recipe> recipes = new ArrayList<Recipe>();
	
	static {
		recipes = new ArrayList<Recipe>();
		Recipe recipe1 = new Recipe();
		recipe1.setId(++SN);
		System.out.println("SN:"+SN);
		System.out.println("ID:"+recipe1.getId());
		recipe1.setTitle("title1");
		recipe1.setInstructions("instructions1");
		Recipe recipe2 = new Recipe();
		recipe2.setId(++SN);
		System.out.println("SN:"+SN);
		System.out.println("ID:"+recipe2.getId());
		recipe2.setTitle("title2");
		recipe2.setInstructions("instructions2");
		Recipe recipe3 = new Recipe();
		recipe3.setId(++SN);
		System.out.println("SN:"+SN);
		System.out.println("ID:"+recipe3.getId());
		recipe3.setTitle("title3");
		recipe3.setInstructions("instructions3");
		recipes.add(recipe1);
		recipes.add(recipe2);
		recipes.add(recipe3);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
