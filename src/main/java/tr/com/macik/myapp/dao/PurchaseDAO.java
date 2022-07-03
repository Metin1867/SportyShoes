package tr.com.macik.myapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.Purchase;
import tr.com.macik.myapp.repo.PurchaseRepo;

//all dao operations are written under @Service 
@Service
public class PurchaseDAO {
	@Autowired
	PurchaseRepo repo;

	// DB INSERT operation
	public Purchase insert(Purchase s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<Purchase> getall(){
		return repo.findAll();
	}
	
	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	// DB UPDATE operation by object
	public Purchase update(Purchase obj) {
		Purchase db=repo.findById(obj.getPchID()).orElse(null);
		db.setPrsID(obj.getPrsID());
		// no update db.setBasketCreated(obj.getBasketCreated());
		db.setPurchaseActivated(obj.getPurchaseActivated());
		db.setDeliveryAddress(obj.getDeliveryAddress());
		db.setDeliveryDate(obj.getDeliveryDate());
		db.setInvoiceAddress(obj.getInvoiceAddress());
		db.setPaymentDate(obj.getPaymentDate());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<Purchase> insertAll(List<Purchase> pchList) {
		return repo.saveAll(pchList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public Purchase get(int pchID){
		return repo.findById(pchID).orElse(null);
	}
}
