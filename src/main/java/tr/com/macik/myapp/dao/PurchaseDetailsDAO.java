package tr.com.macik.myapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.PurchaseDetails;
import tr.com.macik.myapp.repo.PurchaseDetailsRepo;

//all dao operations are written under @Service 
@Service
public class PurchaseDetailsDAO {
	@Autowired
	PurchaseDetailsRepo repo;

	// DB INSERT operation
	public PurchaseDetails insert(PurchaseDetails s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<PurchaseDetails> getall(){
		return repo.findAll();
	}
	
	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	// DB UPDATE operation by object
	public PurchaseDetails update(PurchaseDetails obj) {
		PurchaseDetails db=repo.findById(obj.getPcdID()).orElse(null);
		db.setPchID(obj.getPchID());
		db.setPrdID(obj.getPrdID());
		db.setEnabled(obj.isEnabled());
		db.setCount(obj.getCount());
		db.setPrice(obj.getPrice());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<PurchaseDetails> insertAll(List<PurchaseDetails> pcdList) {
		return repo.saveAll(pcdList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public PurchaseDetails get(int pcdID){
		return repo.findById(pcdID).orElse(null);
	}
}
