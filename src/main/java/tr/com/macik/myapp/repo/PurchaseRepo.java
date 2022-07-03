package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.macik.myapp.pojo.Purchase;

public interface PurchaseRepo extends JpaRepository<Purchase, Integer> {

}
