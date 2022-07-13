package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/pcd/{id}")
	public PurchaseDetails getPurchaseDetails(@PathVariable(value = "id") int pcdID){
		return dao.get(pcdID);
	}
	
	@GetMapping("/pcd/getall")
	public List<PurchaseDetails> getCategories(){
		return dao.getall();
	}

	@DeleteMapping("/pcd/delete")
	public void delete(@RequestBody PurchaseDetails purchaseDetails) {
		dao.deleteById(purchaseDetails.getPcdID());
	}

	@PostMapping("/pcd/update")
	public PurchaseDetails update(@RequestBody PurchaseDetails purchaseDetails) {
		return dao.update(purchaseDetails);
	}

}
