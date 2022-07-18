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
public class PurchaseByDateReportController {
	@Autowired
	PurchaseByDateReportDAO dao;
	@Autowired
	PurchaseByDateReportRepo repo;

	@GetMapping("/PurchaseByDateReport/{id}")
	public PurchaseByDateReport getUser(@PathVariable(value = "id") int usrID){
		return dao.get(usrID);
	}
	
	@GetMapping("/PurchaseByDateReport/getall")
	public List<PurchaseByDateReport> getUsers(){
		return dao.getall();
	}
	
	@PostMapping("/PurchaseByDateReport/search")
	public List<User> getSearch(@RequestBody User user){
		return dao.search(user);
	}
}
