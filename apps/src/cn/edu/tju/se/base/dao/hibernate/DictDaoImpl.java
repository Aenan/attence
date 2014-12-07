package cn.edu.tju.se.base.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.edu.tju.se.base.dao.DictDao;
import cn.edu.tju.se.base.domain.Dict;
@Repository
public class DictDaoImpl extends BaseDaoImpl<Dict> implements DictDao {
    private final String GET_BY_TYPE = "from Dict d where d.type = ? order by number";

    @Override
    public List<Dict> getByType(String type){
        List<Dict> find = (List<Dict>)find(GET_BY_TYPE,type);
		return find;
    }
}
