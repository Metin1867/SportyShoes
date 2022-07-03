package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.macik.myapp.pojo.CommunicationType;

public interface CommunicationTypeRepo extends JpaRepository<CommunicationType, String> {

}
