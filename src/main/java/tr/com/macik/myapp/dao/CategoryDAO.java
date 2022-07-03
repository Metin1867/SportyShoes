package tr.com.macik.myapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.Category;
import tr.com.macik.myapp.repo.CategoryRepo;

//all dao operations are written under @Service 
@Service
public class CategoryDAO {
	@Autowired
	CategoryRepo repo;

	// DB INSERT operation
	public Category insert(Category s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public Category get(int catID){
		return repo.findById(catID).orElse(null);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<Category> getall(){
		return repo.findAll();
	}
	
	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	// DB UPDATE operation by object
	public Category update(Category obj) {
		Category db=repo.findById(obj.getCatID()).orElse(null);
		db.setCategoryLabel(obj.getCategoryLabel());
		db.setCategoryDescription(obj.getCategoryDescription());
		// db.setCategoryImage(obj.getCategoryImage());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<Category> insertAll(List<Category> catList) {
		return repo.saveAll(catList);
	}
}
