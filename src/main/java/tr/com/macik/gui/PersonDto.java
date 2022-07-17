package tr.com.macik.gui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.macik.client.NetClient;
import tr.com.macik.myapp.pojo.Person;
import tr.com.macik.myapp.pojo.User;

public class PersonDto {
	// CRUD
	// Create
	public static Person create(Person person) {
		if (person==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/prs/insert", person);
		client.read();
		String json = client.toString();
		client.end();
		return firstPerson(json);
	}

	public static User create(User user) {
		if (user==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/usr/insert", user);
		client.read();
		String json = client.toString();
		client.end();
		return firstUser(json);
	}

	// Read
	public static User readUserByLogin(String usrLogin) {
		if (usrLogin==null || "".equals(usrLogin))
			return null;
		
		Map<String,Object> params = new LinkedHashMap<>();
        params.put("usrLogin", usrLogin);

        NetClient client = new NetClient("http://localhost:8080");
		client.start(NetClient.POST, "/usr/byLogin", params);
		client.read();
		User user = firstUser(client.toString());
		client.end();
		return user;
	}

	public static User readByID(User user) {
		if (user==null)
			return null;
		//Map<String,Object> params = new LinkedHashMap<>();
        //params.put("usrID", user.getUsrID());

        NetClient client = new NetClient(NetClient.GET, "/usr/"+user.getUsrID());
		client.read();
		String json = client.toString();
		client.end();
		return firstUser(json);
	}

	public static List<User> readAllUser() {
		NetClient client = new NetClient(NetClient.GET, "/usr/getall");
		client.read();
		String json = client.toString();
		client.end();
		return toUserList(json);
	}

	public static List<Person> readAllPerson() {
		NetClient client = new NetClient(NetClient.GET, "/prs/getall");
		client.read();
		String json = client.toString();
		client.end();
		return toPersonList(json);
	}

	public static Person readByID(Person person) {
		if (person==null)
			return null;
		NetClient client = new NetClient(NetClient.GET, "/prs/"+person.getPrsID());
		client.read();
		String json = client.toString();
		client.end();
		return firstPerson(json);
	}

	// Update
	public static User update(User user) {
		if (user==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/usr/update", user);
		client.read();
		String json = client.toString();
		client.end();
		return firstUser(json);
	}

	public static Person update(Person person) {
		if (person==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/prs/update", person);
		client.read();
		String json = client.toString();
		client.end();
		return firstPerson(json);
	}

	// Delete
	public static User delete(User user) {
		if (user==null)
			return null;
		NetClient client = new NetClient(NetClient.DELETE, "/usr/delete", user);
		client.read();
		String json = client.toString();
		client.end();
		return firstUser(json);
	}

	public static Person delete(Person person) {
		if (person==null)
			return null;
		NetClient client = new NetClient(NetClient.DELETE, "/prs/delete", person);
		client.read();
		String json = client.toString();
		client.end();
		return firstPerson(json);
	}

	// Helper
	private static Person firstPerson(String json) {
		if (json == null || "".equals(json))
			return null;
		List<Person> persons = toPersonList(json);
		return (persons==null || persons.size()==1) ? persons.get(0) : null;
	}
	private static User firstUser(String json) {
		if (json == null || "".equals(json))
			return null;
		List<User> users = toUserList(json);
		return (users==null || users.size()==1) ? users.get(0) : null;
	}
	
	private static List<Person> toPersonList(String json) {
		/*System.out.println("JSON: " + json);
		MapperUtil<Person> mUtil = new MapperUtil<>(json);
		return mUtil.toList();*/
		// List<Person> persons = mUtil.toList();
		if (json == null || "".equals(json))
				return null;
		if (json.startsWith("{"))
			json = "["+json+"]";
		ObjectMapper mapper = new ObjectMapper();
		List<Person> persons = null;
		try {
			persons = mapper.readValue(json, new TypeReference<List<Person>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return persons;
	}
	private static List<User> toUserList(String json) {
		System.out.println("JSON: " + json);
		if (json == null || "".equals(json))
				return null;
		if (json.startsWith("{"))
			json = "["+json+"]";
		ObjectMapper mapper = new ObjectMapper();
		List<User> users = null;
		try {
			users = mapper.readValue(json, new TypeReference<List<User>>(){});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	public static List<User> search(User user) {
		if (user==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/usr/search", user);
		client.read();
		String json = client.toString();
		client.end();
		return toUserList(json);
	}

	public static List<Person> search(Person person) {
		if (person==null)
			return null;
		NetClient client = new NetClient(NetClient.POST, "/prs/search", person);
		client.read();
		String json = client.toString();
		client.end();
		return toPersonList(json);
	}

	public static int getPrsId(String userid) {
		User user = new User();
		user.setUsrLogin(userid);
		user.setCounterLogin(-1);
		user = PersonDto.search(user).get(0);
		return user.getPrsID();
	}
}
