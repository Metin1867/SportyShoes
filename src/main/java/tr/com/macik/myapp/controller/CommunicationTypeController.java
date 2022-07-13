package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.macik.myapp.dao.CommunicationTypeDAO;
import tr.com.macik.myapp.pojo.CommunicationType;

@RestController
public class CommunicationTypeController {
	@Autowired
	CommunicationTypeDAO dao;

	@PostMapping("/cmm/insert")
	public CommunicationType insert(@RequestBody CommunicationType communicationType) {
		return dao.insert(communicationType);
	}
	
	@PostMapping("/cmm/insertall")
	public List<CommunicationType> insert(@RequestBody List<CommunicationType> cmmList) {
		return dao.insertAll(cmmList);
	}

	@GetMapping("/cmm/{id}")
	public CommunicationType get(@PathVariable(value = "id") String cmmCd){
		return dao.get(cmmCd);
	}
	
	@GetMapping("/cmm/getall")
	public List<CommunicationType> getAll(){
		return dao.getall();
	}

	@DeleteMapping("/cmm/delete")
	public void delete(@RequestBody CommunicationType communicationType) {
		dao.deleteById(communicationType.getCmmCd());
	}

	@PostMapping("/cmm/update")
	public CommunicationType update(@RequestBody CommunicationType communicationType) {
		return dao.update(communicationType);
	}
}
