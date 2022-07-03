package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tr.com.macik.myapp.dao.ProductDAO;
import tr.com.macik.myapp.pojo.Product;

@Controller
public class ProductController {
	@Autowired
	ProductDAO dao;

	@PostMapping("/prd/insert")
	public Product insert(@RequestBody Product product) {
		return dao.insert(product);
	}
	
	@PostMapping("/prd/insertall")
	public List<Product> insert(@RequestBody List<Product> prdList) {
		return dao.insertAll(prdList);
	}

	@GetMapping("/prd/get")
	public Product getProduct(@RequestBody Product product){
		return dao.get(product.getPrdID());
	}
	
	@GetMapping("/prd/getall")
	public List<Product> getCategories(){
		return dao.getall();
	}

	@PostMapping("/prd/delete")
	public void delete(@RequestBody Product product) {
		dao.deleteById(product.getPrdID());
	}

	@PostMapping("/prd/update")
	public Product update(@RequestBody Product product) {
		return dao.update(product);
	}

}
