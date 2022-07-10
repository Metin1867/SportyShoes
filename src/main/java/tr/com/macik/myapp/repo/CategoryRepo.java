package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.macik.myapp.pojo.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
