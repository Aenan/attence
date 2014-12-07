package cn.edu.tju.se.base.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * DAO基类的接口，其它DAO接口可以直接继承这个DAO接口，不但可以复用共用的方法，还可以获得泛型的好处。
 */
public interface BaseDao<T>{


    /**
     * 根据ID加载PO实例
     *
     * @param id
     * @return 返回相应的持久化PO实例
     */
    public T load(Serializable id) ;

    /**
     * 根据ID获取PO实例
     *
     * @param id
     * @return 返回相应的持久化PO实例
     */
    public T get(Serializable id);
    
    /**
     * 获取PO的所有对象
     *
     * @return
     */
    public List<T> loadAll() ;

    /**
     * 保存PO
     *
     * @param entity
     */
    public void save(T entity);

    /**
     * 删除PO
     *
     * @param entity
     */
    public void remove(T entity) ;

    /**
     * 更改PO
     *
     * @param entity
     */
    public void update(T entity) ;

    /**
     * 执行HQL查询
     *
     * @param sql
     * @return 查询结果
     */
    public List find(String hql) ;

    /**
     * 执行带参的HQL查询
     *
     * @param sql
     * @param params
     * @return 查询结果
     */
    public List find(String hql, Object... params);
    /**
     * 对延迟加载的实体PO执行初始化
     * @param entity
     */
    public void initialize(Object entity) ;
    
    public HibernateTemplate getHibernateTemplate();
}