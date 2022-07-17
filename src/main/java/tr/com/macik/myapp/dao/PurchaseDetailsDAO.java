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

import tr.com.macik.myapp.pojo.PurchaseDetails;
import tr.com.macik.myapp.repo.PurchaseDetailsRepo;
import tr.com.macik.utils.ServletHTMLUtil;

//all dao operations are written under @Service 
@Service
public class PurchaseDetailsDAO {
	@Autowired
	PurchaseDetailsRepo repo;
	@Autowired
	DataSource dataSource;

	// DB INSERT operation
	public PurchaseDetails insert(PurchaseDetails s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<PurchaseDetails> getall(){
		return repo.findAll();
	}

	// DB SELECT operation, retrieve all founded objects/entities
	public List<PurchaseDetails> search(PurchaseDetails purchaseDetails) {
		// throw new IllegalCallerException("Search function isn't yet implemented!");

		List<PurchaseDetails> list = new ArrayList<>();
	    String sql = "SELECT pcdid, pchid, prdid, enabled, count, price"
	    		+ " FROM purchase_details";
		String whereClause = "";
		ArrayList<Integer> colPositions = new ArrayList<>();
		if (purchaseDetails.getPcdID() >0) {
			whereClause += ServletHTMLUtil.andWhere(whereClause, "pcdid", purchaseDetails.getPcdID());
			//colPositions.add(1);
		} else {
			if (purchaseDetails.getPchID() >0) {
				whereClause += ServletHTMLUtil.andWhere(whereClause, "pchid", purchaseDetails.getPchID());
				//colPositions.add(2);
			}
			if (purchaseDetails.getPrdID() >0) {
				whereClause += ServletHTMLUtil.andWhere(whereClause, "prdid", purchaseDetails.getPrdID());
				//colPositions.add(3);
			}
			/*if (purchaseDetails.isEnabled()) {
				whereClause += ServletHTMLUtil.andWhere(whereClause, "enabled", purchaseDetails.isEnabled());
				//colPositions.add(4);
			}*/
			if (purchaseDetails.getCount() >=0) {
				whereClause += ServletHTMLUtil.andWhere(whereClause, "count", purchaseDetails.getCount());
				//colPositions.add(5);
			}
			if (purchaseDetails.getPrice() >=0.0) {
				whereClause += ServletHTMLUtil.andWhere(whereClause, "price", purchaseDetails.getPrice());
				//colPositions.add(6);
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
						case 1: p.setInt(pos, purchaseDetails.getPcdID()); System.out.println(purchaseDetails.getPcdID()); break;
						case 2: p.setInt(pos, purchaseDetails.getPchID()); System.out.println(purchaseDetails.getPchID()); break;
						case 3: p.setInt(pos, purchaseDetails.getPrdID()); System.out.println(purchaseDetails.getPrdID()); break;
						case 4: p.setBoolean(pos, purchaseDetails.isEnabled()); System.out.println(purchaseDetails.isEnabled()); break;
						case 5: p.setInt(pos, purchaseDetails.getCount()); System.out.println(purchaseDetails.getCount()); break;
						case 6: p.setFloat(pos, purchaseDetails.getPrice()); System.out.println(purchaseDetails.getPrice()); break;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pos++;
				}
			    ResultSet rs = p.executeQuery(sql);
			    
			    PurchaseDetails newPurchaseDetails;
			    while(rs.next()) {
			    	newPurchaseDetails = new PurchaseDetails(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4), rs.getInt(5), rs.getFloat(6));
			    	list.add(newPurchaseDetails);
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	// DB UPDATE operation by object
	public PurchaseDetails update(PurchaseDetails obj) {
		PurchaseDetails db=repo.findById(obj.getPcdID()).orElse(null);
		db.setPchID(obj.getPchID());
		db.setPrdID(obj.getPrdID());
		db.setEnabled(obj.isEnabled());
		db.setCount(obj.getCount());
		db.setPrice(obj.getPrice());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<PurchaseDetails> insertAll(List<PurchaseDetails> pcdList) {
		return repo.saveAll(pcdList);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public PurchaseDetails get(int pcdID){
		return repo.findById(pcdID).orElse(null);
	}
}
