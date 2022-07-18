package tr.com.macik.myapp.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseByDateReportRepo extends JpaRepository<PurchaseByDateReport, Integer> {

}
