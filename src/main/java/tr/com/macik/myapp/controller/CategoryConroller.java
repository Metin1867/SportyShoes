package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.macik.myapp.dao.CategoryDAO;
import tr.com.macik.myapp.pojo.Category;

@RestController
public class CategoryConroller {
	@Autowired
	CategoryDAO dao;

	@PostMapping("/cat/insert")
	public Category insert(@RequestBody Category category) {
		return dao.insert(category);
	}
	
	@PostMapping("/cat/insertall")
	public List<Category> insert(@RequestBody List<Category> catList) {
		return dao.insertAll(catList);
	}

	@GetMapping("/cat/get")
	public Category getCategory(@RequestBody Category category){
		return dao.get(category.getCatID());
	}
	
	@GetMapping("/cat/getall")
	public List<Category> getCategories(){
		return dao.getall();
	}

	@DeleteMapping("/cat/delete")
	public void delete(@RequestBody Category category) {
		dao.deleteById(category.getCatID());
	}

	@PostMapping("/cat/update")
	public Category update(@RequestBody Category category) {
		return dao.update(category);
	}
}
