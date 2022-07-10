package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.macik.myapp.pojo.Lookup;

@Repository
//public interface LookupRepo extends JpaRepository<Lookup, String[]> {
public interface LookupRepo extends JpaRepository<Lookup, Integer> {

}
