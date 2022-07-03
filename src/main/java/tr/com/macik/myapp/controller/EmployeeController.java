package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tr.com.macik.myapp.dao.EmployeeDAO;
import tr.com.macik.myapp.pojo.Employee;

@Controller
public class EmployeeController {
	@Autowired
	EmployeeDAO dao;

	@PostMapping("/emp/insert")
	public Employee insert(@RequestBody Employee employee) {
		return dao.insert(employee);
	}
	
	@PostMapping("/emp/insertall")
	public List<Employee> insert(@RequestBody List<Employee> catList) {
		return dao.insertAll(catList);
	}

	@GetMapping("/emp/get")
	public Employee getEmployee(@RequestBody Employee employee){
		return dao.get(employee.getEmpID());
	}
	
	@GetMapping("/emp/getall")
	public List<Employee> getCategories(){
		return dao.getall();
	}

	@PostMapping("/emp/delete")
	public void delete(@RequestBody Employee employee) {
		dao.deleteById(employee.getEmpID());
	}

	@PostMapping("/emp/update")
	public Employee update(@RequestBody Employee employee) {
		return dao.update(employee);
	}

}
