package tr.com.macik.myapp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.CommunicationType;
import tr.com.macik.myapp.repo.CommunicationTypeRepo;

//all dao operations are written under @Service 
@Service
public class CommunicationTypeDAO {
	@Autowired
	CommunicationTypeRepo repo;

	// DB INSERT operation
	public CommunicationType insert(CommunicationType s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<CommunicationType> getall(){
		return repo.findAll();
	}
	
	// DB DELETE operation by ID
	public void deleteById(String cmmCd) {
		repo.deleteById(cmmCd);
	}
	
	// DB UPDATE operation by object
	public CommunicationType update(CommunicationType obj) {
		CommunicationType db=repo.findById(obj.getCmmCd()).orElse(null);
		db.setMasterCd(obj.getMasterCd());
		db.setLabel(obj.getLabel());
		db.setRexpression(obj.getRexpression());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<CommunicationType> insertAll(List<CommunicationType> empList) {
		return repo.saveAll(empList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public CommunicationType get(String CmmCd){
		return repo.findById(CmmCd).orElse(null);
	}
}
