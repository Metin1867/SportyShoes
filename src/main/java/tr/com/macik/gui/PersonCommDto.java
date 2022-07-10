package tr.com.macik.gui;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.macik.client.NetClient;
import tr.com.macik.myapp.pojo.PersonComm;
import tr.com.macik.myapp.pojo.User;

public class PersonCommDto {
	// CRUD
	// Create
	public static PersonComm create(PersonComm personComm) {
		if (personComm==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/pco/insert", personComm);
		client.read();
		String json = client.toString();
		client.end();
		return firstPersonComm(json);
	}

	// Read
	public static PersonComm readByID(PersonComm personComm) {
		if (personComm==null)
			return null;
		NetClient client = new NetClient(NetClient.GET, "/pco/get", personComm);
		client.read();
		String json = client.toString();
		client.end();
		return firstPersonComm(json);
	}

	public static List<PersonComm> readAll() {
		NetClient client = new NetClient(NetClient.GET, "/pco/getall");
		client.read();
		String json = client.toString();
		client.end();
		return toPersonCommList(json);
	}

	// Update
	public static PersonComm update(PersonComm personComm) {
		if (personComm==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/pco/update", personComm);
		client.read();
		String json = client.toString();
		client.end();
		return firstPersonComm(json);
	}

	// Delete
	public static PersonComm delete(PersonComm personComm) {
		if (personComm==null)
			return null;
		NetClient client = new NetClient(NetClient.DELETE, "/pco/delete", personComm);
		client.read();
		String json = client.toString();
		client.end();
		return firstPersonComm(json);
	}

	// Helper
	private static PersonComm firstPersonComm(String json) {
		if (json == null || "".equals(json))
			return null;
		List<PersonComm> personComms = toPersonCommList(json);
		return (personComms==null || personComms.size()==1) ? personComms.get(0) : null;
	}
	
	private static List<PersonComm> toPersonCommList(String json) {
		/*System.out.println("JSON: " + json);
		MapperUtil<PersonComm> mUtil = new MapperUtil<>(json);
		return mUtil.toList();*/
		// List<PersonComm> personComms = mUtil.toList();
		if (json == null || "".equals(json))
				return null;
		if (json.startsWith("{"))
			json = "["+json+"]";
		ObjectMapper mapper = new ObjectMapper();
		List<PersonComm> personComms = null;
		try {
			personComms = mapper.readValue(json, new TypeReference<List<PersonComm>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return personComms;
	}

	public static List<PersonComm> search(PersonComm personComm) {
		if (personComm==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/pco/search", personComm);
		client.read();
		String json = client.toString();
		client.end();
		return toPersonCommList(json);
	}
}
