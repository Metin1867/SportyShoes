package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.macik.myapp.pojo.PersonComm;

public interface PersonCommRepo extends JpaRepository<PersonComm, Integer> {

}
