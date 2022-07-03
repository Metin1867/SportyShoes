package tr.com.macik.myapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.Employee;
import tr.com.macik.myapp.repo.EmployeeRepo;

//all dao operations are written under @Service 
@Service
public class EmployeeDAO {
	@Autowired
	EmployeeRepo repo;

	// DB INSERT operation
	public Employee insert(Employee s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<Employee> getall(){
		return repo.findAll();
	}
	
	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	// DB UPDATE operation by object
	public Employee update(Employee obj) {
		Employee db=repo.findById(obj.getEmpID()).orElse(null);
		db.setPrsID(obj.getPrsID());
		db.setSocialSecurityNumber(obj.getSocialSecurityNumber());
		db.setCivilStatus(obj.getCivilStatus());
		db.setNumberOfChild(obj.getNumberOfChild());
		db.setPhone(obj.getPhone());
		db.setEmail(obj.getEmail());
		db.setEmergencyContact(obj.getEmergencyContact());
		db.setEmergencyNumber(obj.getEmergencyNumber());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<Employee> insertAll(List<Employee> empList) {
		return repo.saveAll(empList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public Employee get(int empID){
		return repo.findById(empID).orElse(null);
	}
}
