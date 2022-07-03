package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tr.com.macik.myapp.dao.PersonDAO;
import tr.com.macik.myapp.pojo.Person;

@Controller
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
	public Person getPerson(@RequestBody Person person){
		return dao.get(person.getPrsID());
	}
	
	@GetMapping("/prs/getall")
	public List<Person> getCategories(){
		return dao.getall();
	}

	@PostMapping("/prs/delete")
	public void delete(@RequestBody Person person) {
		dao.deleteById(person.getPrsID());
	}

	@PostMapping("/prs/update")
	public Person update(@RequestBody Person person) {
		return dao.update(person);
	}

}
