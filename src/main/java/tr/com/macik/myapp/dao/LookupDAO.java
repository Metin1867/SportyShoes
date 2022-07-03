package tr.com.macik.myapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.Lookup;
import tr.com.macik.myapp.repo.LookupRepo;

//all dao operations are written under @Service 
@Service
public class LookupDAO {
	@Autowired
	LookupRepo repo;

	// DB INSERT operation
	public Lookup insert(Lookup s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<Lookup> getall(){
		return repo.findAll();
	}
	
	// DB DELETE operation by ID
	/*public void deleteById(String[] pkeys) {
		repo.deleteById(pkeys);
	}*/
	public void deleteById(int lkpID) {
		repo.deleteById(lkpID);
	}
	
	// DB UPDATE operation by object
	public Lookup update(Lookup lookup) {
	//public Lookup update(Lookup obj) {
		//String[] pkeys = {obj.getLkClass(), obj.getLkKey()};
		//Lookup db=repo.findById(pkeys).orElse(null);
		Lookup db=repo.findById(lookup.getLkpID()).orElse(null);
		db.setLkValue(lookup.getLkValue());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<Lookup> insertAll(List<Lookup> lkpList) {
		return repo.saveAll(lkpList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	/*public Lookup get(String[] pkeys){
		return repo.findById(pkeys).orElse(null);
	}*/
	public Lookup get(int lkpID){
		return repo.findById(lkpID).orElse(null);
	}
}
