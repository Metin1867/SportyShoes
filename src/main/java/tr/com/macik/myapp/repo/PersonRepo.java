package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.macik.myapp.pojo.Person;

public interface PersonRepo extends JpaRepository<Person, Integer> {

}
