package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.macik.myapp.pojo.Purchase;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Integer> {

}
