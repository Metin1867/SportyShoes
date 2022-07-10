package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.macik.myapp.pojo.PersonComm;

@Repository
public interface PersonCommRepo extends JpaRepository<PersonComm, Integer> {

}
