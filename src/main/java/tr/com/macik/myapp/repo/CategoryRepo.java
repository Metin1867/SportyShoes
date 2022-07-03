package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.macik.myapp.pojo.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
