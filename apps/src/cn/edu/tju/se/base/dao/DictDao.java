package cn.edu.tju.se.base.dao;

import java.util.List;
import cn.edu.tju.se.base.domain.Dict;

public interface DictDao extends BaseDao<Dict>{

    /**
     * 根据用户名查询User对象
     * @param userName 用户名
     * @return 对应userName的User对象，如果不存在，返回null。
     */
	public List<Dict> getByType(String type);
}
