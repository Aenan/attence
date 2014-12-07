package cn.edu.tju.se.demo.dao.hibernate;

import org.springframework.stereotype.Repository;

import cn.edu.tju.se.base.dao.hibernate.BaseDaoImpl;
import cn.edu.tju.se.demo.dao.RecipeDao;
import cn.edu.tju.se.demo.domain.Recipe;
@Repository
public class RecipeDaoImpl extends BaseDaoImpl<Recipe> implements RecipeDao{

}
