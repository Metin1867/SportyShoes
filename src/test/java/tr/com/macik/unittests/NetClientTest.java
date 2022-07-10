package tr.com.macik.unittests;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.mock.web.MockHttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.macik.client.NetClient;
import tr.com.macik.gui.CommunicationTypeDto;
import tr.com.macik.gui.EmployeeDto;
import tr.com.macik.gui.PersonCommDto;
import tr.com.macik.gui.PersonDto;
import tr.com.macik.myapp.pojo.Employee;
import tr.com.macik.myapp.pojo.Person;
import tr.com.macik.myapp.pojo.PersonComm;
import tr.com.macik.myapp.pojo.User;

public class NetClientTest {
	// private static ObjectMapper mapper = new ObjectMapper();
	private static HttpSession oneSession;
	private static HttpSession getSession() {
		if (oneSession == null)
			oneSession = new MockHttpServletRequest().getSession();
		return oneSession;
	}

	
	public static void main(String[] args) throws IOException {
		// doWorkByLogin();

		// doTest();
		
		// doWorkPerson();

		// doWorkCommunicationType();

		// doWorkPersonComm();

		// doWorkRegister();

		doWorkEmployee();
	}

	private static void doWorkEmployee() {
		String[][] personTypes = {{"Employee", "metin.acikalin@sportyshoes.com"}
								, {"Customer", "customer@hotmail.com"}
								, {"EmpAndCust", "metinacikalin@hotmail.com"}
								, {"Anonym", "anonym@world.com"}};
		
		User user = PersonDto.readUserByLogin("mmmmmetinacikalin@hotmail.comm");
		Employee employee = new Employee(0, user.getPrsID(), "756.6718.1398.50", "married", 3, "+41-44-2368507", "metin.acikalin@sportyshoes.com", "Miss Emine Acikalin", "+41-76-5745664");
		// EmployeeDto.create(employee);
		
		if (user == null) {
			System.out.println("Employee not found");
			getSession().invalidate();
		}
		else {
			getSession().setAttribute("userid", user.getUsrLogin());
			System.out.println("metinacikalin@hotmail.com, is it an user? " + EmployeeDto.isEmployee(user.getPrsID()));
			if (EmployeeDto.isEmployee(user.getPrsID())) {
				System.out.println("You are not allowed to login as employee" + EmployeeDto.isEmployee(user.getPrsID()));
				HttpSession hsemployee = getSession();
				hsemployee.setAttribute("employee", true);
			}
			else {
				HttpSession hsemployee = getSession();
				hsemployee.setAttribute("userid", user.getUsrLogin());
				hsemployee.setAttribute("employee", false);

			}
		}
		
		HttpSession hs = getSession();
		System.out.println("userid "+hs.getAttribute("userid"));
		System.out.println("employee "+hs.getAttribute("employee"));
	}

	private static void doWorkRegister() {
		String salutation = "Metin";
		String firstname = "Metin";
		String lastname = "Acikalin";
		String birthday = "1967-08-01";
		String email = "metinacikalin@hotmail.comm";
		String phone = "077 666 1122";
		String password = "sporty";
		

		// search the User
		User user = PersonDto.readUserByLogin(email);
		
		// search the Person
		Person person = new Person(0, salutation, firstname, lastname, Date.valueOf(birthday));
		List<Person> persons = PersonDto.search(person);
		PersonComm comm;
		List<PersonComm> comms = null;
		if (persons.size()>0) {
			person = persons.get(0);

			// search the Communications for existing Person
			comm = new PersonComm(0, person.getPrsID(), null, null, null, null);
			comms = PersonCommDto.search(comm);
		}
		

		System.out.println("User: " + user);
		if (persons.size()>0) {
			System.out.println("Person: " + person);
			if (comms != null)
				for (PersonComm commsDetail : comms)
					System.out.println("Communication: " + commsDetail);
			System.out.println("This user is already registered!");
		} else {
			if (user != null) {
				System.out.println("This user is reverved for internal usage, please contact the system administrator.");
			} else {
				System.out.println("Register the person without avctivation.");
				// create person
				person = PersonDto.create(person);
				// create user
				user = new User(0, email, password, person.getPrsID(), false, (Timestamp) null, (Timestamp) null, 0);
				PersonDto.create(user);
				// create comms
				comm = new PersonComm(0, person.getPrsID(), "EMAIL", null, "Email", email);
				PersonCommDto.create(comm);
				if (phone != null && !"".equals(phone)) {
					comm = new PersonComm(0, person.getPrsID(), "PHONE", null, "Phone", phone);
					PersonCommDto.create(comm);
				}
			}
		}

		
	}

	private static void doWorkPersonComm() {
		PersonComm pco_delete = new PersonComm(64, -1, "EMAIL", null, "Email", "eminepaac@gmail.com"); 

		System.out.println("Delete Communication Entry " 
							+ PersonCommDto.delete(pco_delete));

		System.out.println(PersonCommDto.readAll());

		Person person = new Person(0, "Frau", "Emine", "Palaz", (Date) null);
		List<Person> persons = PersonDto.search(person);
		person = persons.get(0);

		PersonComm pco = new PersonComm(0, person.getPrsID(), "EMAIL", null, "Email", "eminepaac@gmail.com"); 
		List<PersonComm> pcos = PersonCommDto.search(pco);

		if (pcos.size() > 0 && pcos.get(0).getPcoID() != 0)
			System.out.println("Communication Entry already existing!");
		else
			System.out.println("Create Communication Entry " + PersonCommDto.create(pco));
	}

	private static void doWorkCommunicationType() {
		CommunicationTypeDto.getLookup();
		
		System.out.println(CommunicationTypeDto.getInstance().toString());

	}

	private static void doTest() {
		User user = new User(54, "Metin", "Emine", -1, false, null, null, -1);
		//System.out.println(PersonDto.readAllUser());
		// System.out.println(PersonDto.readByID(user));

		//Person person = new Person(17, null, null, null, null);
		//Person newPerson = PersonDto.readByID(person);
		
		Person person = new Person();
		//System.out.println(PersonDto.readAllUser());
		System.out.println(PersonDto.search(person));

	}

	private static void doWorkPerson() {
		Person person;
		try {
			person = new Person(0, "Frau", "Emine", "Palaz", (Date) null);
			List<Person> persons = PersonDto.search(person);
			System.out.println("Found nothing or more Persons " + persons);
			System.out.println("Persons size() " + persons.size());
			if (persons.size() == 0) {
				person = new Person(0, "Frau", "Emine", "Palaz", Date.valueOf("1967-08-01"));
				person = PersonDto.create(person);
			} else {
				person = persons.get(0);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			person = new Person(17, null, null, null, null);
			Person newPerson = PersonDto.readByID(person);
			if (newPerson==null) {
				person = new Person(0, "Frau", "Emine", "Palaz", Date.valueOf("1967-08-01"));
				person = PersonDto.create(person);
				System.out.println("Create a Person " + person);
				System.out.println("Person ID " + person.getPrsID());
			} else {
				person = newPerson;
			}
			System.out.println("Person ID " + person.getPrsID());
		}
		
		// User
		User user = PersonDto.readUserByLogin("eminepaac@hotmail.com");
		System.out.println("doWorkRetrievUserByLogin " + user);
		// if (user==null)
		// System.out.println("User doesn't exists!");
		// else
		user = PersonDto.delete(user);
		System.out.println("doWorkDeleteUser " + user);

		user = new User(0, "eminepaac@hotmail.com", "12345", person.getPrsID(), false, null, null, 0);
		User newUser = PersonDto.create(user);
		System.out.println("doWorkCreateUser " + newUser);

		int usrID = PersonDto.readUserByLogin("eminepaac@hotmail.com").getUsrID();
		user = new User(usrID, "eminepaac@hotmail.com", "12345", person.getPrsID(), false, new java.sql.Timestamp(System.currentTimeMillis()),
				null, 1);
		newUser = PersonDto.update(user);
		System.out.println("doWorkUpdateUser " + newUser);
		
	}

	private static void doWorkByLogin() throws JsonMappingException, JsonProcessingException {
		NetClient client = new NetClient("http://localhost:8080");

		// String[][] queryParameters = {{"usrLogin", "metinacikalin@hotmail.com"}
		// /*, {"param2", "value2"}*/};
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("usrLogin", "metinacikalin@hotmail.com");
		// client.start(POST, "/usr/byLogin", params);
		client.start(NetClient.GET, "/usr/getall");
		client.read();
		String jason = client.toString();
		System.out.println(jason);

		ObjectMapper mapper = new ObjectMapper();
		List<User> users = mapper.readValue(jason, List.class);
		System.out.println(users);
		client.end();

	}
}
