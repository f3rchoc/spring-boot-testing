package ec.mulsetect.learning.springboot.repository;

import ec.mulsetect.learning.springboot.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// not use @Repository because we use JpaRepository, and it classes has this implementation SimpleJpaRepository.
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByBrand(String brand);

    // custom query using jpql.
    @Query("select c from Car c where c.brand = ?1 and c.color = ?2")
    Car findByJpql(String brand, String color);

    // custom query using jpql with named params.
    @Query("select c from Car c where c.brand = :brand and c.color = :colors")
    Car findByJpqlNamed(String brand, @Param("colors") String color);

    // we can index params or named params
    @Query(value = "select * from cars where color = :color and brand = :brand", nativeQuery = true)
    Car findByNativeQuery(String color, String brand);

}
