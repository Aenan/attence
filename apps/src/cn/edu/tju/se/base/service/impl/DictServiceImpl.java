package cn.edu.tju.se.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.tju.se.base.dao.DictDao;
import cn.edu.tju.se.base.domain.Dict;
import cn.edu.tju.se.base.service.DictService;

@Service
public class DictServiceImpl implements DictService {
	@Autowired 
	private DictDao dictDao;

	@Override
	public List<Dict> getByType(String type) {
		return dictDao.getByType(type);
	}

	@Override
	public Dict get(long id) {
		// TODO Auto-generated method stub
		return dictDao.get(id);
	}

	@Override
	public void add(Dict dict) {
		// TODO Auto-generated method stub
		dictDao.save(dict);
	}

	@Override
	public void update(Dict dict) {
		// TODO Auto-generated method stub
		dictDao.update(dict);
	}

	@Override
	public void remove(long id) {
		// TODO Auto-generated method stub
		dictDao.remove(dictDao.get(id));
	}

	@Override
	public List<Dict> getAll() {
		// TODO Auto-generated method stub
		return dictDao.loadAll();
	}
	
}
