package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.macik.myapp.dao.PersonCommDAO;
import tr.com.macik.myapp.pojo.PersonComm;

@RestController
public class PersonCommController {
	@Autowired
	PersonCommDAO dao;

	@PostMapping("/pco/insert")
	public PersonComm insert(@RequestBody PersonComm personComm) {
		return dao.insert(personComm);
	}
	
	@PostMapping("/pco/insertall")
	public List<PersonComm> insert(@RequestBody List<PersonComm> pcoList) {
		return dao.insertAll(pcoList);
	}

	@GetMapping("/pco/{id}")
	public PersonComm getPersonComm(@PathVariable(value = "id") int pcoID){
		return dao.get(pcoID);
	}
	
	@PostMapping("/pco/search")
	public List<PersonComm> getSearch(@RequestBody PersonComm personComm){
		return dao.search(personComm);
	}

	@GetMapping("/pco/getall")
	public List<PersonComm> getCategories(){
		return dao.getall();
	}

	@DeleteMapping("/pco/delete")
	public void delete(@RequestBody PersonComm personComm) {
		dao.deleteById(personComm.getPcoID());
	}

	@PostMapping("/pco/update")
	public PersonComm update(@RequestBody PersonComm personComm) {
		return dao.update(personComm);
	}

}
