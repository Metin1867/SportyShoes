package tr.com.macik.myapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.macik.myapp.pojo.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

}
