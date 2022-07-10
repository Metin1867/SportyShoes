package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.com.macik.myapp.dao.UserDAO;
import tr.com.macik.myapp.pojo.User;
import tr.com.macik.myapp.repo.UserRepo;

@RestController
public class UserController {
	@Autowired
	UserDAO dao;
	@Autowired
	UserRepo repo;

	@PostMapping("/usr/insert")
	public User insert(@RequestBody User user) {
		return dao.insert(user);
	}
	
	@PostMapping("/usr/insertall")
	public List<User> insert(@RequestBody List<User> usrList) {
		return dao.insertAll(usrList);
	}

	@GetMapping("/usr/{id}")
	public User getUser(@PathVariable(value = "id") int usrID){
		return dao.get(usrID);
	}
	
	@GetMapping("/usr/getall")
	public List<User> getCategories(){
		return dao.getall();
	}

	@DeleteMapping("/usr/delete")
	public void delete(@RequestBody User user) {
		dao.deleteById(user.getUsrID());
	}

	@PostMapping("/usr/update")
	public User update(@RequestBody User user) {
		return dao.update(user);
	}

	@PostMapping("/usr/byLogin")
	public ResponseEntity<List<User>> getUserByLogin(@RequestParam String usrLogin) {
		System.out.println("/usr/byLogin "+ usrLogin);
		return new ResponseEntity<List<User>>(repo.findByUsrLogin(usrLogin), HttpStatus.OK);
	}
}
