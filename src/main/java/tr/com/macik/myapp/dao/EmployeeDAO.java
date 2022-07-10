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

import tr.com.macik.myapp.pojo.Employee;
import tr.com.macik.myapp.repo.EmployeeRepo;
import tr.com.macik.utils.SqlUtil;

//all dao operations are written under @Service 
@Service
public class EmployeeDAO {
	@Autowired
	EmployeeRepo repo;
	@Autowired
	DataSource dataSource;

	// DB INSERT operation
	public Employee insert(Employee s) {
		return repo.save(s);
	}

	// DB SELECT operation, retrieve all the objects/entities
	public List<Employee> getall() {
		return repo.findAll();
	}

	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}

	// DB UPDATE operation by object
	public Employee update(Employee obj) {
		Employee db = repo.findById(obj.getEmpID()).orElse(null);
		db.setPrsID(obj.getPrsID());
		db.setSocialSecurityNumber(obj.getSocialSecurityNumber());
		db.setCivilStatus(obj.getCivilStatus());
		db.setNumberOfChild(obj.getNumberOfChild());
		db.setPhone(obj.getPhone());
		db.setEmail(obj.getEmail());
		db.setEmergencyContact(obj.getEmergencyContact());
		db.setEmergencyNumber(obj.getEmergencyNumber());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<Employee> insertAll(List<Employee> empList) {
		return repo.saveAll(empList);
	}

	// DB SELECT operation, retrieve by id the object/entity
	public Employee get(int empID) {
		return repo.findById(empID).orElse(null);
	}

	// DB SELECT operation, retrieve all founded objects/entities
	public List<Employee> search(Employee employee) {
		List<Employee> list = new ArrayList<>();
		String sql = "SELECT empid, prsid," + " social_security_number, civil_status, number_of_child,"
				+ " phone, email," + " emergency_contact, emergency_number" + " FROM employee";
		String whereClause = "";
		ArrayList<Integer> colPositions = new ArrayList<>();
		if (employee.getEmpID() != 0) {
			whereClause += SqlUtil.andWhere(whereClause, "empid", employee.getEmpID());
			// colPositions.add(1);
		} else {
			if (employee.getPrsID() != 0) {
				whereClause += SqlUtil.andWhere(whereClause, "prsid", employee.getPrsID());
				// colPositions.add(2);
			}
			if (employee.getSocialSecurityNumber() != null && !"".equals(employee.getSocialSecurityNumber())) {
				whereClause += SqlUtil.andWhere(whereClause, "social_security_number",
						employee.getSocialSecurityNumber());
				// colPositions.add(3);
			}
			if (employee.getCivilStatus() != null && !"".equals(employee.getCivilStatus())) {
				whereClause += SqlUtil.andWhere(whereClause, "civil_status", employee.getCivilStatus());
				// colPositions.add(4);
			}
			if (employee.getNumberOfChild() != 0 && !(employee.getNumberOfChild() == -1)) {
				whereClause += SqlUtil.andWhere(whereClause, "number_of_child", employee.getNumberOfChild());
				// colPositions.add(5);
			}
			if (employee.getPhone() != null && !"".equals(employee.getPhone())) {
				whereClause += SqlUtil.andWhere(whereClause, "phone", employee.getPhone());
				// colPositions.add(6);
			}
			if (employee.getEmail() != null && !"".equals(employee.getEmail())) {
				whereClause += SqlUtil.andWhere(whereClause, "email", employee.getEmail());
				// colPositions.add(7);
			}
			if (employee.getEmergencyContact() != null && !"".equals(employee.getEmergencyContact())) {
				whereClause += SqlUtil.andWhere(whereClause, "emergency_contact", employee.getEmergencyContact());
				// colPositions.add(8);
			}
			if (employee.getEmergencyNumber() != null && !"".equals(employee.getEmergencyNumber())) {
				whereClause += SqlUtil.andWhere(whereClause, "emergency_number", employee.getEmergencyNumber());
				// colPositions.add(9);
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
					case 1:
						p.setInt(pos, employee.getEmpID());
						System.out.println(employee.getEmpID());
						break;
					case 2:
						p.setInt(pos, employee.getPrsID());
						System.out.println(employee.getPrsID());
						break;
					case 3:
						p.setString(pos, employee.getSocialSecurityNumber());
						System.out.println(employee.getSocialSecurityNumber());
						break;
					case 4:
						p.setString(pos, employee.getCivilStatus());
						System.out.println(employee.getCivilStatus());
						break;
					case 5:
						p.setInt(pos, employee.getNumberOfChild());
						System.out.println(employee.getNumberOfChild());
						break;
					case 6:
						p.setString(pos, employee.getPhone());
						System.out.println(employee.getPhone());
						break;
					case 7:
						p.setString(pos, employee.getEmail());
						System.out.println(employee.getEmail());
						break;
					case 8:
						p.setString(pos, employee.getEmergencyContact());
						System.out.println(employee.getEmergencyContact());
						break;
					case 9:
						p.setString(pos, employee.getEmergencyNumber());
						System.out.println(employee.getEmergencyNumber());
						break;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pos++;
			}
			ResultSet rs = p.executeQuery(sql);

			Employee newEmployee;
			while (rs.next()) {
				newEmployee = new Employee(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
				list.add(newEmployee);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
}
