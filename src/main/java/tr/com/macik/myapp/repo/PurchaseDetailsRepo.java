package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.macik.myapp.pojo.PurchaseDetails;

@Repository
public interface PurchaseDetailsRepo extends JpaRepository<PurchaseDetails, Integer> {

}
