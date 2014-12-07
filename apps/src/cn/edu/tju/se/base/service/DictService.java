package cn.edu.tju.se.base.service;

import java.util.List;

import cn.edu.tju.se.base.domain.Dict;

public interface DictService {
	/**
	 * 
	 * @param type
	 * @return
	 */
	public List<Dict> getByType(String type);

	public Dict get(long id);

	public void add(Dict dict);

	public void update(Dict dict);

	public void remove(long id);

	public List<Dict> getAll();

}
