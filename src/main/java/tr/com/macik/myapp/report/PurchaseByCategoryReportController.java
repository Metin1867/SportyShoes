package tr.com.macik.myapp.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tr.com.macik.myapp.pojo.User;

@RestController
public class PurchaseByCategoryReportController {
	@Autowired
	PurchaseByCategoryReportDAO dao;
	@Autowired
	PurchaseByCategoryReportRepo repo;

	@GetMapping("/PurchaseByCategoryReport/{id}")
	public PurchaseByCategoryReport getUser(@PathVariable(value = "id") int usrID){
		return dao.get(usrID);
	}
	
	@GetMapping("/PurchaseByCategoryReport/getall")
	public List<PurchaseByCategoryReport> getUsers(){
		return dao.getall();
	}
	
	@PostMapping("/PurchaseByCategoryReport/search")
	public List<User> getSearch(@RequestBody User user){
		return dao.search(user);
	}
}
