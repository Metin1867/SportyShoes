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
import tr.com.macik.myapp.repo.PersonRepo;

//all dao operations are written under @Service 
@Service
public class PersonDAO {
	@Autowired
	PersonRepo repo;
	@Autowired
	DataSource dataSource;
	
	// DB INSERT operation
	public Person insert(Person s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<Person> getall(){
		return repo.findAll();
	}

	// DB SELECT operation, retrieve all founded objects/entities
	public List<Person> search(Person person) {
		// throw new IllegalCallerException("Search function isn't yet implemented!");

		List<Person> list = new ArrayList<>();
	    String sql = "SELECT prsid, salutation, firstname, lastname, birthday"
	    		+ " FROM person";
		String whereClause = "";
		ArrayList<Integer> colPositions = new ArrayList<>();
		if (person.getPrsID() >0) {
			whereClause += andWhere(whereClause, "prsid", person.getPrsID());
			//colPositions.add(1);
		} else {
			if (person.getSalutation()!= null && !"".equals(person.getSalutation())) {
				whereClause += andWhere(whereClause, "salutation", person.getSalutation());
				//colPositions.add(2);
			}
			if (person.getFirstname()!= null && !"".equals(person.getFirstname())) {
				whereClause += andWhere(whereClause, "firstname", person.getFirstname());
				//colPositions.add(3);
			}
			if (person.getLastname()!= null && !"".equals(person.getLastname())) {
				whereClause += andWhere(whereClause, "lastname", person.getLastname());
				//colPositions.add(4);
			}
			if (person.getBirthday()!= null) {
				whereClause += andWhere(whereClause, "birthday", person.getBirthday().toString());
				//colPositions.add(5);
			}
		}
		if (!"".equals(whereClause.trim())) {
			sql += " where" + whereClause;
			System.out.println("Person search " + sql);

			try ( Connection c = dataSource.getConnection();
				  PreparedStatement p = c.prepareStatement(sql) ) {
				int pos = 1;
				for (int colPos : colPositions) {
					System.out.print("Search attribute at Position " + colPos + "/");
					try {
						switch (colPos) {
						case 1: p.setInt(pos, person.getPrsID()); System.out.println(person.getPrsID()); break;
						case 2: p.setString(pos, person.getSalutation()); System.out.println(person.getSalutation()); break;
						case 3: p.setString(pos, person.getFirstname()); System.out.println(person.getFirstname()); break;
						case 4: p.setString(pos, person.getLastname()); System.out.println(person.getLastname()); break;
						case 5: p.setDate(pos, person.getBirthday()); System.out.println(person.getBirthday()); break;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pos++;
				}
			    ResultSet rs = p.executeQuery(sql);
			    
			    Person newPerson;
			    while(rs.next()) {
				    newPerson = new Person(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
			    	list.add(newPerson);
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	private String andWhere(String whereClauseOrig, String column, long value) {
		String whereClause ="";
		if (whereClauseOrig!=null && !"".equals(whereClauseOrig))
			whereClause += " and ";
		if (column==null || "".equals(column.trim()))
			return whereClauseOrig;
		whereClause += " " + column + " = " + value;
		
		return whereClause;		
	}

	private String andWhere(String whereClauseOrig, String column, String value) {
		String whereClause ="";
		if (whereClauseOrig!=null && !"".equals(whereClauseOrig))
			whereClause += " and ";
		if (column==null || "".equals(column.trim()))
			return whereClauseOrig;
		whereClause += " " + column + " = '" + value + "'";
		
		return whereClause;		
	}

	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	// DB UPDATE operation by object
	public Person update(Person obj) {
		Person db=repo.findById(obj.getPrsID()).orElse(null);
		db.setSalutation(obj.getSalutation());
		db.setFirstname(obj.getFirstname());
		db.setLastname(obj.getLastname());
		db.setBirthday(obj.getBirthday());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<Person> insertAll(List<Person> prsList) {
		return repo.saveAll(prsList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public Person get(int prsID){
		return repo.findById(prsID).orElse(null);
	}
}
