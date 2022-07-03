package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.macik.myapp.pojo.User;

public interface UserRepo extends JpaRepository<User, String> {

}
