package tr.com.macik.myapp;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tr.com.macik.myapp.pojo.Category;
import tr.com.macik.myapp.repo.CategoryRepo;

// @RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryTests {

	@Autowired
	private CategoryRepo categoryRep;
	private List<Category> testData = new ArrayList<>();
	
	@Before
	public void setUp() {
		testData.add(new Category(-901, "TestCategory 1", "This entry is used for tests", null));
		testData.add(new Category(-902, "TestCategory 2", "This entry is used for tests", null));
		testData.add(new Category(-903, "TestCategory 3", "This entry is used for tests", null));
		testData.add(new Category(-904, "TestCategory 4", "This entry is used for tests", null));

		// Save 4 Person objects into the database
		categoryRep.saveAll(testData);
	}

	@After
	public void cleanUp() {
		// Delete all test data
		categoryRep.deleteAll(testData);
	}

	@Test
	public void testCRUD() {
		System.out.println("testCRUD");
		// Create
		Category origCategory = new Category(-900, "TestCategory 0", "This entry is used for tests", null);
		categoryRep.save(origCategory);
		Category createdCategory = categoryRep.findById(-900).orElse(null);

		assertEquals(origCategory.getCategoryLabel(), createdCategory.getCategoryLabel());
		assertEquals(origCategory.getCategoryDescription(), createdCategory.getCategoryDescription());
		assertEquals(origCategory.getCategoryImage(), createdCategory.getCategoryImage());

		// Update
		createdCategory.setCategoryLabel("Update TestCategory");
		createdCategory.setCategoryDescription("Update successful");
		categoryRep.save(createdCategory);
		Category updatedCategory = categoryRep.findById(-900).orElse(null);

		assertEquals(createdCategory.getCategoryLabel(), updatedCategory.getCategoryLabel());
		assertEquals(createdCategory.getCategoryDescription(), updatedCategory.getCategoryDescription());
		assertEquals(createdCategory.getCategoryImage(), updatedCategory.getCategoryImage());

		// Delete
		categoryRep.delete(createdCategory);

		assertNull(categoryRep.findById(-900).orElse(null));
	}

	@Test
	public void testFindAll() {
		// Get all the people
		List<Category> categories = categoryRep.findAll();

		assertEquals(4, categories.size());
	}

	/*@Test
	public void testFindByFirstName() {
		// Get all the people with a specific first name
		List<Person> people = categoryRep.findByFirstName("Leonard");

		assertEquals(2, people.size());
	}

	@Test
	public void testFindByLastName() {
		// Get all the people with a specific last name
		List<Person> people = categoryRep.findByLastName("Cooper");

		assertEquals(2, people.size());
	}*/
}
