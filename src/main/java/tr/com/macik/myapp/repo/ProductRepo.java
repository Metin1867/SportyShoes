package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tr.com.macik.myapp.pojo.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
