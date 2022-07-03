package tr.com.macik.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tr.com.macik.myapp.dao.LookupDAO;
import tr.com.macik.myapp.pojo.Lookup;

@Controller
public class LookupController {
	@Autowired
	LookupDAO dao;

	@PostMapping("/lkp/insert")
	public Lookup insert(@RequestBody Lookup lookup) {
		return dao.insert(lookup);
	}
	
	@PostMapping("/lkp/insertall")
	public List<Lookup> insert(@RequestBody List<Lookup> lkpList) {
		return dao.insertAll(lkpList);
	}

	@GetMapping("/lkp/get")
	public Lookup getLookup(@RequestBody Lookup lookup){
		return dao.get(lookup.getLkpID());
	}
	/*public Lookup getLookup(String[] pkeys){
		return dao.get(pkeys);
	}*/
	
	@GetMapping("/lkp/getall")
	public List<Lookup> getCategories(){
		return dao.getall();
	}

	@PostMapping("/lkp/delete")
	public void delete(@RequestBody Lookup lookup) {
		dao.deleteById(lookup.getLkpID());
	}
	/*public void delete(@RequestBody Lookup lookup) {
		String[] pkeys = {lookup.getLkClass(), lookup.getLkKey()};
		dao.deleteById(pkeys);
	}*/

	@PostMapping("/lkp/update")
	public Lookup update(@RequestBody Lookup lookup) {
		return dao.update(lookup);
	}

}
