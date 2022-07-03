package tr.com.macik.myapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.Person;
import tr.com.macik.myapp.repo.PersonRepo;

//all dao operations are written under @Service 
@Service
public class PersonDAO {
	@Autowired
	PersonRepo repo;

	// DB INSERT operation
	public Person insert(Person s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<Person> getall(){
		return repo.findAll();
	}
	
	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	// DB UPDATE operation by object
	public Person update(Person obj) {
		Person db=repo.findById(obj.getPrsID()).orElse(null);
		db.setSalutation(obj.getSalutation());
		db.setFirstname(obj.getFirstname());
		db.setLastname(obj.getLastname());
		db.setBirthday(obj.getBirthday());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<Person> insertAll(List<Person> prsList) {
		return repo.saveAll(prsList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public Person get(int prsID){
		return repo.findById(prsID).orElse(null);
	}
}
