package tr.com.macik.myapp.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tr.com.macik.myapp.pojo.User;

//all dao operations are written under @Service 
@Service
public class PurchaseByCategoryReportDAO {
	@Autowired
	PurchaseByCategoryReportRepo repo;
	@Autowired
	DataSource dataSource;

	// DB SELECT operation, retrieve all the objects/entities
	public List<PurchaseByCategoryReport> getall(){
		return repo.findAll();
	}
	

	// DB SELECT operation, retrieve all founded objects/entities
	public List<User> search(User user) {
		// throw new IllegalCallerException("Search function isn't yet implemented!");

		List<User> list = new ArrayList<>();
	    String sql = "SELECT usrid, usr_login, usr_password, prsID, "
	    		+ "activated, last_success_login, last_failed_login, counter_login "
	    		+ "FROM user";
		String whereClause = "";
		ArrayList<Integer> colPositions = new ArrayList<>();
		if (user.getUsrID() > 0) {
			whereClause += andWhere(whereClause, "usrid", user.getUsrID());
			//colPositions.add(1);
		} else {
			if (user.getUsrLogin()!= null && !"".equals(user.getUsrLogin())) {
				whereClause += andWhere(whereClause, "usr_login", user.getUsrLogin());
				//colPositions.add(2);
			}
			if (user.getUsrPassword()!= null && !"".equals(user.getUsrPassword())) {
				whereClause += andWhere(whereClause, "usr_password", user.getUsrPassword());
				//colPositions.add(3);
			}
			if (user.getPrsID()!= 0 && user.getPrsID()!= -1) {
				whereClause += andWhere(whereClause, "prsid", user.getPrsID());
				//colPositions.add(4);
			}
			if (user.isActivated()) {
				whereClause += andWhere(whereClause, "activated", user.isActivated());
				//colPositions.add(5);
			}
			if (user.getLastSuccessLogin()!= null) {
				whereClause += andWhere(whereClause, "last_success_login", user.getLastSuccessLogin().toString());
				//colPositions.add(6);
			}
			if (user.getLastFailedLogin()!= null) {
				whereClause += andWhere(whereClause, "last_failed_login", user.getLastFailedLogin().toString());
				//colPositions.add(7);
			}
			if (user.getCounterLogin()>= 0) {
				whereClause += andWhere(whereClause, "counter_login", user.getCounterLogin());
				//colPositions.add(8);
			}
		}
		if (!"".equals(whereClause.trim())) {
			sql += " where" + whereClause;
			System.out.println("User search " + sql);

			try ( Connection c = dataSource.getConnection();
				  PreparedStatement p = c.prepareStatement(sql);) {
				int pos = 1;
				for (int colPos : colPositions) {
					System.out.print("Search attribute at Position " + colPos + "/");
					try {
						switch (colPos) {
						case 1: p.setInt(pos, user.getUsrID()); System.out.println(user.getUsrID()); break;
						case 2: p.setString(pos, user.getUsrLogin()); System.out.println(user.getUsrLogin()); break;
						case 3: p.setString(pos, user.getUsrPassword()); System.out.println(user.getUsrPassword()); break;
						case 4: p.setInt(pos, user.getPrsID()); System.out.println(user.getPrsID()); break;
						case 5: p.setBoolean(pos, user.isActivated()); System.out.println(user.isActivated()); break;
						case 6: p.setTimestamp(pos, user.getLastSuccessLogin()); System.out.println(user.getLastSuccessLogin()); break;
						case 7: p.setTimestamp(pos, user.getLastFailedLogin()); System.out.println(user.getLastFailedLogin()); break;
						case 8: p.setInt(pos, user.getCounterLogin()); System.out.println(user.getCounterLogin()); break;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pos++;
				}
			    ResultSet rs = p.executeQuery(sql);
			    
			    User newUser;
			    while(rs.next()) {
				    newUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getBoolean(5), rs.getTimestamp(6), rs.getTimestamp(7), rs.getInt(8));
			    	list.add(newUser);
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			    
		return list;
	}

	private String andWhere(String whereClauseOrig, String column, boolean value) {
		String whereClause ="";
		if (whereClauseOrig!=null && !"".equals(whereClauseOrig))
			whereClause += " and ";
		if (column==null || "".equals(column.trim()))
			return whereClauseOrig;
		whereClause += " " + column + " = " + value;
		
		return whereClause;		
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

	// DB SELECT operation, retrieve by id the object/entity
	public PurchaseByCategoryReport get(int prdID){
		return repo.findById(prdID).orElse(null);
	}
}
