package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.macik.myapp.pojo.Lookup;

//public interface LookupRepo extends JpaRepository<Lookup, String[]> {
public interface LookupRepo extends JpaRepository<Lookup, Integer> {

}
