package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.macik.myapp.pojo.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

}
