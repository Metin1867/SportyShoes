package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tr.com.macik.myapp.dao.PersonCommDAO;
import tr.com.macik.myapp.pojo.PersonComm;

@Controller
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

	@GetMapping("/pco/get")
	public PersonComm getPersonComm(@RequestBody PersonComm personComm){
		return dao.get(personComm.getPcoID());
	}
	
	@GetMapping("/pco/getall")
	public List<PersonComm> getCategories(){
		return dao.getall();
	}

	@PostMapping("/pco/delete")
	public void delete(@RequestBody PersonComm personComm) {
		dao.deleteById(personComm.getPcoID());
	}

	@PostMapping("/pco/update")
	public PersonComm update(@RequestBody PersonComm personComm) {
		return dao.update(personComm);
	}

}
