package tr.com.macik.myapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.User;
import tr.com.macik.myapp.repo.UserRepo;

//all dao operations are written under @Service 
@Service
public class UserDAO {
	@Autowired
	UserRepo repo;

	// DB INSERT operation
	public User insert(User s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<User> getall(){
		return repo.findAll();
	}
	
	// DB DELETE operation by ID
	// public void deleteById(String usrLogin) {
	public void deleteById(int usrID) {
		repo.deleteById(usrID);
	}
	
	// DB UPDATE operation by object
	public User update(User obj) {
		User db=repo.findById(obj.getUsrID()).orElse(null);
		db.setUsrLogin(obj.getUsrLogin());
		db.setUsrPassword(obj.getUsrPassword());
		db.setPrsID(obj.getPrsID());
		db.setLastSuccessLogin(obj.getLastSuccessLogin());
		db.setLastFailedLogin(obj.getLastFailedLogin());
		db.setCounterLogin(obj.getCounterLogin());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<User> insertAll(List<User> usrList) {
		return repo.saveAll(usrList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public User get(int usrID){
		return repo.findById(usrID).orElse(null);
	}

	// DB SELECT operation, retrieve by id the object/entity
	public User getByLogin(String usrLogin){
		List<User> list = repo.findByUsrLogin(usrLogin);
		System.out.println("Size of returned User-List " + list.size());
		System.out.println(list);
		return list.get(0);
	}
}
