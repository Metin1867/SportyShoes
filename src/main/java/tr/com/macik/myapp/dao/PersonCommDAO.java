package tr.com.macik.myapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.Person;
import tr.com.macik.myapp.pojo.PersonComm;
import tr.com.macik.myapp.repo.PersonCommRepo;
import tr.com.macik.utils.SqlUtil;

//all dao operations are written under @Service 
@Service
public class PersonCommDAO {
	@Autowired
	PersonCommRepo repo;
	@Autowired
	DataSource dataSource;

	// DB INSERT operation
	public PersonComm insert(PersonComm s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<PersonComm> getall(){
		return repo.findAll();
	}
	
	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	// DB UPDATE operation by object
	public PersonComm update(PersonComm obj) {
		PersonComm db=repo.findById(obj.getPcoID()).orElse(null);
		db.setPrsID(obj.getPrsID());
		db.setCmmCd(obj.getCmmCd());
		db.setSubinfo(obj.getSubinfo());
		db.setLabel(obj.getLabel());
		db.setInfo(obj.getInfo());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<PersonComm> insertAll(List<PersonComm> catList) {
		return repo.saveAll(catList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public PersonComm get(int pcoID){
		return repo.findById(pcoID).orElse(null);
	}

	// DB SELECT operation, retrieve all founded objects/entities
	public List<PersonComm> search(PersonComm personComm) {
		List<PersonComm> list = new ArrayList<>();
	    String sql = "SELECT pcoid, prsid, cmm_cd, subinfo, label, info"
	    		+ " FROM person_comm";
		String whereClause = "";
		ArrayList<Integer> colPositions = new ArrayList<>();
		if (personComm.getPcoID() !=0) {
			whereClause += SqlUtil.andWhere(whereClause, "pcoid", personComm.getPcoID());
			//colPositions.add(1);
		} else {
			if (personComm.getPrsID()!= 0) {
				whereClause += SqlUtil.andWhere(whereClause, "prsid", personComm.getPrsID());
				//colPositions.add(2);
			}
			if (personComm.getCmmCd()!= null && !"".equals(personComm.getCmmCd())) {
				whereClause += SqlUtil.andWhere(whereClause, "cmm_cd", personComm.getCmmCd());
				//colPositions.add(3);
			}
			if (personComm.getSubinfo()!= null && !"".equals(personComm.getSubinfo())) {
				whereClause += SqlUtil.andWhere(whereClause, "subinfo", personComm.getSubinfo());
				//colPositions.add(4);
			}
			if (personComm.getLabel()!= null && !"".equals(personComm.getLabel())) {
				whereClause += SqlUtil.andWhere(whereClause, "label", personComm.getLabel());
				//colPositions.add(4);
			}
			if (personComm.getInfo()!= null && !"".equals(personComm.getInfo())) {
				whereClause += SqlUtil.andWhere(whereClause, "info", personComm.getInfo());
				//colPositions.add(4);
			}
		}
		if (!"".equals(whereClause.trim()))
			sql += " where" + whereClause;
		System.out.println("Person Communication search " + sql);

		try {
			Connection c = dataSource.getConnection();
			PreparedStatement p = c.prepareStatement(sql);
			int pos = 1;
			for (int colPos : colPositions) {
				System.out.print("Search attribute at Position " + colPos + "/");
				try {
					switch (colPos) {
					case 1: p.setInt(pos, personComm.getPcoID()); System.out.println(personComm.getPcoID()); break;
					case 2: p.setInt(pos, personComm.getPrsID()); System.out.println(personComm.getPrsID()); break;
					case 3: p.setString(pos, personComm.getCmmCd()); System.out.println(personComm.getCmmCd()); break;
					case 4: p.setString(pos, personComm.getSubinfo()); System.out.println(personComm.getSubinfo()); break;
					case 5: p.setString(pos, personComm.getLabel()); System.out.println(personComm.getLabel()); break;
					case 6: p.setString(pos, personComm.getInfo()); System.out.println(personComm.getInfo()); break;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pos++;
			}
		    ResultSet rs = p.executeQuery(sql);
		    
		    PersonComm newPersonComm;
		    while(rs.next()) {
		    	newPersonComm = new PersonComm(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
		    	list.add(newPersonComm);
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			    
		return list;
	}
}
