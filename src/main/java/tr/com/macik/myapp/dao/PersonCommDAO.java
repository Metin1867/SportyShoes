package tr.com.macik.myapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.PersonComm;
import tr.com.macik.myapp.repo.PersonCommRepo;

//all dao operations are written under @Service 
@Service
public class PersonCommDAO {
	@Autowired
	PersonCommRepo repo;

	// DB INSERT operation
	public PersonComm insert(PersonComm s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<PersonComm> getall(){
		return repo.findAll();
	}
	
	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	// DB UPDATE operation by object
	public PersonComm update(PersonComm obj) {
		PersonComm db=repo.findById(obj.getPcoID()).orElse(null);
		db.setPrsID(obj.getPrsID());
		db.setCmmCd(obj.getCmmCd());
		db.setSubinfo(obj.getSubinfo());
		db.setLabel(obj.getLabel());
		db.setInfo(obj.getInfo());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<PersonComm> insertAll(List<PersonComm> catList) {
		return repo.saveAll(catList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public PersonComm get(int pcoID){
		return repo.findById(pcoID).orElse(null);
	}
}
