package tr.com.macik.gui;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.macik.client.NetClient;
import tr.com.macik.myapp.pojo.Category;

public class CategoryDto {
	private static CategoryDto thisSingleInstance;
	
	private CategoryDto() {
	}
	
	public static CategoryDto getInstance() {
		if (thisSingleInstance == null);
			thisSingleInstance = new CategoryDto();
			
		return thisSingleInstance;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	// CRUD
	// Create
	public static Category create(Category category) {
		if (category==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/cat/insert", category);
		client.read();
		String json = client.toString();
		client.end();
		return firstCategory(json);
	}

	// Read
	public static Category readByID(Category category) {
		if (category==null)
			return null;
		NetClient client = new NetClient(NetClient.GET, "/cat/"+category.getCatID());
		client.read();
		String json = client.toString();
		client.end();
		return firstCategory(json);
	}

	public static List<Category> readAll() {
		NetClient client = new NetClient(NetClient.GET, "/cat/getall");
		client.read();
		String json = client.toString();
		client.end();
		return toCategoryList(json);
	}

	// Update
	public static Category update(Category category) {
		if (category==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/cat/update", category);
		client.read();
		String json = client.toString();
		client.end();
		return firstCategory(json);
	}

	// Delete
	public static Category delete(Category category) {
		if (category==null)
			return null;
		NetClient client = new NetClient(NetClient.DELETE, "/cat/delete", category);
		client.read();
		String json = client.toString();
		client.end();
		return firstCategory(json);
	}

	// Helper
	private static Category firstCategory(String json) {
		if (json == null || "".equals(json))
			return null;
		List<Category> category = toCategoryList(json);
		return (category==null || category.size()==1) ? category.get(0) : null;
	}
	
	private static List<Category> toCategoryList(String json) {
		System.out.println("JSON: " + json);
		if (json == null || "".equals(json))
				return null;
		if (json.startsWith("{"))
			json = "["+json+"]";
		ObjectMapper mapper = new ObjectMapper();
		List<Category> categorys = null;
		try {
			categorys = mapper.readValue(json, new TypeReference<List<Category>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categorys;
	}

	public static List<Category> search(Category category) {
		if (category==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/cat/search", category);
		client.read();
		String json = client.toString();
		client.end();
		return toCategoryList(json);
	}
}
