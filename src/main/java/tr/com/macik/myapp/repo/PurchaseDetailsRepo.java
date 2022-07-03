package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.macik.myapp.pojo.PurchaseDetails;

public interface PurchaseDetailsRepo extends JpaRepository<PurchaseDetails, Integer> {

}
