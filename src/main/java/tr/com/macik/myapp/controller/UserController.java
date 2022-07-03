package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tr.com.macik.myapp.dao.UserDAO;
import tr.com.macik.myapp.pojo.User;

@Controller
public class UserController {
	@Autowired
	UserDAO dao;

	@PostMapping("/usr/insert")
	public User insert(@RequestBody User user) {
		return dao.insert(user);
	}
	
	@PostMapping("/usr/insertall")
	public List<User> insert(@RequestBody List<User> usrList) {
		return dao.insertAll(usrList);
	}

	@GetMapping("/usr/get")
	public User getUser(@RequestBody User user){
		return dao.get(user.getUsrLogin());
	}
	
	@GetMapping("/usr/getall")
	public List<User> getCategories(){
		return dao.getall();
	}

	@PostMapping("/usr/delete")
	public void delete(@RequestBody User user) {
		dao.deleteById(user.getUsrLogin());
	}

	@PostMapping("/usr/update")
	public User update(@RequestBody User user) {
		return dao.update(user);
	}

}
