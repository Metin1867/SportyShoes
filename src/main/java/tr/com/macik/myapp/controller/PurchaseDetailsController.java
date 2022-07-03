package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.macik.myapp.dao.PurchaseDetailsDAO;
import tr.com.macik.myapp.pojo.PurchaseDetails;

@RestController
public class PurchaseDetailsController {
	@Autowired
	PurchaseDetailsDAO dao;

	@PostMapping("/pcd/insert")
	public PurchaseDetails insert(@RequestBody PurchaseDetails purchaseDetails) {
		return dao.insert(purchaseDetails);
	}
	
	@PostMapping("/pcd/insertall")
	public List<PurchaseDetails> insert(@RequestBody List<PurchaseDetails> pcdList) {
		return dao.insertAll(pcdList);
	}

	@GetMapping("/pcd/get")
	public PurchaseDetails getPurchaseDetails(@RequestBody PurchaseDetails purchaseDetails){
		return dao.get(purchaseDetails.getPcdID());
	}
	
	@GetMapping("/pcd/getall")
	public List<PurchaseDetails> getCategories(){
		return dao.getall();
	}

	@PostMapping("/pcd/delete")
	public void delete(@RequestBody PurchaseDetails purchaseDetails) {
		dao.deleteById(purchaseDetails.getPcdID());
	}

	@PostMapping("/pcd/update")
	public PurchaseDetails update(@RequestBody PurchaseDetails purchaseDetails) {
		return dao.update(purchaseDetails);
	}

}
