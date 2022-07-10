package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.macik.myapp.pojo.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

}
