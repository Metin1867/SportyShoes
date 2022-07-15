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

import tr.com.macik.myapp.pojo.Category;
import tr.com.macik.myapp.repo.CategoryRepo;

//all dao operations are written under @Service 
@Service
public class CategoryDAO {
	@Autowired
	CategoryRepo repo;
	@Autowired
	DataSource dataSource;

	// DB INSERT operation
	public Category insert(Category s) {
		return repo.save(s);
	}
	
	// DB SELECT operation, retrieve by id the object/entity
	public Category get(int catID){
		return repo.findById(catID).orElse(null);
	}
	
	// DB SELECT operation, retrieve all the objects/entities
	public List<Category> getall(){
		return repo.findAll();
	}
	
	// DB SELECT operation, retrieve all founded objects/entities
	public List<Category> search(Category category) {
		// throw new IllegalCallerException("Search function isn't yet implemented!");
	
		List<Category> list = new ArrayList<>();
	    String sql = "SELECT catid, category_label, category_description, category_image "
	    		+ "FROM category";
		String whereClause = "";
		ArrayList<Integer> colPositions = new ArrayList<>();
		if (category.getCatID() !=0) {
			whereClause += andWhere(whereClause, "catid", category.getCatID());
			//colPositions.add(1);
		} else {
			if (category.getCategoryLabel()!= null && !"".equals(category.getCategoryLabel())) {
				whereClause += andWhere(whereClause, "category_label", category.getCategoryLabel());
				//colPositions.add(2);
			}
			if (category.getCategoryDescription()!= null && !"".equals(category.getCategoryDescription())) {
				whereClause += andWhere(whereClause, "category_description", category.getCategoryDescription());
				//colPositions.add(3);
			}
			/*if (category.getCategoryImage()!= null) {
				whereClause += andWhere(whereClause, "category_image", category.getCategoryImage().toString());
				//colPositions.add(4);
			}*/
		}
		if (!"".equals(whereClause.trim()))
			sql += " where" + whereClause;
		System.out.println("Category search " + sql);
	
		try ( Connection c = dataSource.getConnection();
			  PreparedStatement p = c.prepareStatement(sql) ) {
			int pos = 1;
			for (int colPos : colPositions) {
				System.out.print("Search attribute at Position " + colPos + "/");
				try {
					switch (colPos) {
					case 1: p.setInt(pos, category.getCatID()); System.out.println(category.getCatID()); break;
					case 2: p.setString(pos, category.getCategoryLabel()); System.out.println(category.getCategoryLabel()); break;
					case 3: p.setString(pos, category.getCategoryDescription()); System.out.println(category.getCategoryDescription()); break;
					case 4: p.setString(pos, category.getCategoryImage().toString()); System.out.println(category.getCategoryImage().toString()); break;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pos++;
			}
		    ResultSet rs = p.executeQuery(sql);
		    
		    Category newCategory;
		    while(rs.next()) {
		    	newCategory = new Category(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBytes(4));
		    	list.add(newCategory);
		    }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	// DB DELETE operation by ID
	public void deleteById(int id) {
		repo.deleteById(id);
	}
	
	// DB UPDATE operation by object
	public Category update(Category obj) {
		Category db=repo.findById(obj.getCatID()).orElse(null);
		db.setCategoryLabel(obj.getCategoryLabel());
		db.setCategoryDescription(obj.getCategoryDescription());
		// db.setCategoryImage(obj.getCategoryImage());
		return repo.save(db);
	}

	// DB INSERT operation by list
	public List<Category> insertAll(List<Category> catList) {
		return repo.saveAll(catList);
	}
}
