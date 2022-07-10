package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/pch/get")
	public Purchase getPurchase(@RequestBody Purchase purchase){
		return dao.get(purchase.getPchID());
	}
	
	@GetMapping("/pch/getall")
	public List<Purchase> getCategories(){
		return dao.getall();
	}

	@PostMapping("/pch/delete")
	public void delete(@RequestBody Purchase purchase) {
		dao.deleteById(purchase.getPchID());
	}

	@PostMapping("/pch/update")
	public Purchase update(@RequestBody Purchase purchase) {
		return dao.update(purchase);
	}

}
