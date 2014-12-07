package cn.edu.tju.se.base.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.gson.Gson;

import cn.edu.tju.se.base.domain.Dict;
import cn.edu.tju.se.base.service.DictService;

@Controller
@RequestMapping(value="/dict")
public class DictController extends BaseController{
	@Autowired
	private DictService dictService;
	
	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

	@RequestMapping(method=RequestMethod.GET,value="/type/{type}")
	public @ResponseBody List<Dict> getByType(@PathVariable String type){
		return dictService.getByType(type);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody List<Dict> getAll(){
		return dictService.getAll();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public @ResponseBody Dict get(@PathVariable String id){
		return dictService.get(Long.parseLong(id));
	}

	@RequestMapping(method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void addByPost(@RequestBody String body){
		Dict dict = (new Gson()).fromJson(body, Dict.class);
		dictService.add(dict);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public void addByPut(@RequestBody String body){
		Dict dict = (new Gson()).fromJson(body, Dict.class);
		dictService.add(dict);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateByPost(@RequestBody String body){
		Dict dict = (new Gson()).fromJson(body, Dict.class);
		dictService.update(dict);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateByPut(@RequestBody String body){
		Dict dict = (new Gson()).fromJson(body, Dict.class);
		dictService.update(dict);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String id){
		dictService.remove(Long.parseLong(id));
	}
}
