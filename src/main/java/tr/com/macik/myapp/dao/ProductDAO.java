package tr.com.macik.myapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.Product;
import tr.com.macik.myapp.repo.ProductRepo;

//all dao operations are written under @Service 
@Service
public class ProductDAO {
	@Autowired
	ProductRepo repo;

	// DB INSERT operation
	public Product insert(Product s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<Product> getall(){
		return repo.findAll();
	}
	
	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	// DB UPDATE operation by object
	public Product update(Product obj) {
		Product db=repo.findById(obj.getPrdID()).orElse(null);
		db.setCatID(obj.getCatID());
		db.setProductLabel(obj.getProductLabel());
		db.setProductDescription(obj.getProductDescription());
		db.setProductDetails(obj.getProductDescription());
		// db.setProductImage(obj.getProductImage());
		db.setPrice(obj.getPrice());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<Product> insertAll(List<Product> prdList) {
		return repo.saveAll(prdList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public Product get(int prdID){
		return repo.findById(prdID).orElse(null);
	}
}
