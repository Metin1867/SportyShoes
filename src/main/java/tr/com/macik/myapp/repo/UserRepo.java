package tr.com.macik.myapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.macik.myapp.pojo.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	List<User> findByUsrLogin(String usrLogin);
}
