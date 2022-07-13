package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.macik.myapp.dao.PurchaseDAO;
import tr.com.macik.myapp.pojo.Purchase;

@RestController
public class PurchaseController {
	@Autowired
	PurchaseDAO dao;

	@PostMapping("/pch/insert")
	public Purchase insert(@RequestBody Purchase purchase) {
		return dao.insert(purchase);
	}
	
	@PostMapping("/pch/insertall")
	public List<Purchase> insert(@RequestBody List<Purchase> pchList) {
		return dao.insertAll(pchList);
	}

	@GetMapping("/pch/{id}")
	public Purchase getPurchase(@PathVariable(value = "id") int pchID){
		return dao.get(pchID);
	}
	
	@GetMapping("/pch/getall")
	public List<Purchase> getCategories(){
		return dao.getall();
	}

	@DeleteMapping("/pch/delete")
	public void delete(@RequestBody Purchase purchase) {
		dao.deleteById(purchase.getPchID());
	}

	@PostMapping("/pch/update")
	public Purchase update(@RequestBody Purchase purchase) {
		return dao.update(purchase);
	}

}
