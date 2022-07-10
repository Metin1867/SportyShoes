package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.macik.myapp.dao.PersonDAO;
import tr.com.macik.myapp.pojo.Person;

@RestController
public class PersonController {
	@Autowired
	PersonDAO dao;

	@PostMapping("/prs/insert")
	public Person insert(@RequestBody Person person) {
		return dao.insert(person);
	}
	
	@PostMapping("/prs/insertall")
	public List<Person> insert(@RequestBody List<Person> prsList) {
		return dao.insertAll(prsList);
	}

	@GetMapping("/prs/get")
	public Person getPerson(@RequestBody int prsID){
		System.out.println("PersonControllet.get "+prsID);
		return dao.get(prsID);
	}
	
	@GetMapping("/prs/getall")
	public List<Person> getPersons(){
		return dao.getall();
	}
	
	@PostMapping("/prs/search")
	public List<Person> getSearch(@RequestBody Person person){
		return dao.search(person);
	}

	@DeleteMapping("/prs/delete")
	public void delete(@RequestBody Person person) {
		dao.deleteById(person.getPrsID());
	}

	@PostMapping("/prs/update")
	public Person update(@RequestBody Person person) {
		return dao.update(person);
	}

}
