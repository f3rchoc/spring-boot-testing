package ec.mulsetect.learning.springboot.repository;

import ec.mulsetect.learning.springboot.model.Car;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

/**
 * Spring boot provides the @DataJpaTest annotation to test the persistence layer components
 * that will autoconfigure in-memory embedded database for testing purposes.
 */
@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    private Car car;

    @BeforeEach
    void setUp() {
        this.car = Car.getInstance("Red", "kia");
    }

    // junit test to save car operation
    @Test
    @DisplayName("junit test to save car operation")
    void givenCarObject_whenSave_thenReturnSavedCar() {

        // give - precondition or setup
//        var car = Car.getInstance("Red", "kia");

        // when - action or the behaviour that we are going to test
        var saved = this.carRepository.save(car);

        // then - verify the output
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isNotNull();
        Assertions.assertThat(saved.getId()).isPositive();

    }

    // junit test to find all cars
    @Test
    @DisplayName("junit test to find all cars")
    void givenCarList_whenFindAll_thenCarsList() {

        // give - precondition or setup
//        var kia = Car.getInstance("Red", "kia");
        var audi = Car.getInstance("Blue", "audi");
        this.carRepository.saveAll(List.of(car, audi));

        // when - action or the behaviour that we are going to test
        var cars = this.carRepository.findAll();

        // then - verify the output
        Assertions.assertThat(cars).isNotNull();
        Assertions.assertThat(cars.size()).isEqualTo(2);

    }

    // junit test to get employee by id operation
    @Test
    @DisplayName("Junit test to get car by id.")
    void given_CarObject_whenFindById_thenReturnCarObject() {

        // give - precondition or setup
        var car = Car.getInstance("Red", "kia");
        this.carRepository.save(car);

        // when - action or the behaviour that we are going to test
        var saved = this.carRepository.findById(car.getId()).orElse(null);

        // then - verify the output
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isEqualTo(car.getId());

    }

    // junit test to get car by brand
    @Test
    @DisplayName("Junit test to get car by brand")
    void givenBrandCar_whenFindByBrand_thenReturnCarObject() {

        // give - precondition or setup
//        var car = Car.getInstance("Red", "kia");
        this.carRepository.save(car);

        // when - action or the behaviour that we are going to test
        var saved = this.carRepository.findByBrand(car.getBrand()).orElse(null);

        // then - verify the output
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isEqualTo(car.getId());

    }

    // junit test to update car operation.
    @Test
    @DisplayName("junit test to update car operation.")
    void givenCarObject_whenUpdateCar_thenReturnUpdatedCar() {

        // give - precondition or setup
//        var car = Car.getInstance("Red", "kia");
        this.carRepository.save(car);

        // when - action or the behaviour that we are going to test
        var updatedCar = this.carRepository.save(Car.getInstance(car.getId(),"black", "mazda"));

        // then - verify the output
        Assertions.assertThat(updatedCar).isNotNull();
        Assertions.assertThat(updatedCar.getId()).isEqualTo(car.getId());
        Assertions.assertThat(updatedCar.getBrand()).isEqualTo("mazda");
        Assertions.assertThat(updatedCar.getColor()).isEqualTo("black");

    }

    // junit test to delete car.
    @Test
    @DisplayName("junit test to delete car")
    void givenCarObject_whenDeleteCar_thenRemoveCar() {

        // give - precondition or setup
//        var car = Car.getInstance("Red", "kia");
        this.carRepository.save(car);

        // when - action or the behaviour that we are going to test
        this.carRepository.delete(car);
        var carSearch = this.carRepository.findById(car.getId()).orElse(null);

        // then - verify the output
        Assertions.assertThat(carSearch).isNull();

    }

    // junit test to use custom query with jpql
    @Test
    void givenBrandAndColor_whenFindByJpql_thenReturnCarObject() {

        // give - precondition or setup
//        var car = Car.getInstance(color, brand);
        this.carRepository.save(car);

        // when - action or the behaviour that we are going to test
        var saved = this.carRepository.findByJpql(car.getBrand(), car.getColor());

        // then - verify the output
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isEqualTo(car.getId());
        Assertions.assertThat(saved.getBrand()).isEqualTo(car.getBrand());
        Assertions.assertThat(saved.getColor()).isEqualTo(car.getColor());

    }

    // junit test to use custom query with jpql named params
    @Test
    void givenBrandAndColor_whenFindByJpqlNamed_thenReturnCarObject() {

//        final var color = "Red";
//        final var brand = "kia";

        // give - precondition or setup
//        var car = Car.getInstance(color, brand);
        this.carRepository.save(car);

        // when - action or the behaviour that we are going to test
        var saved = this.carRepository.findByJpqlNamed(this.car.getBrand(), this.car.getColor());

        // then - verify the output
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getId()).isEqualTo(car.getId());
        Assertions.assertThat(saved.getBrand()).isEqualTo(this.car.getBrand());
        Assertions.assertThat(saved.getColor()).isEqualTo(this.car.getColor());

    }

    // junit test for custom query using native query
    @Test
    void givenColorAndBrand_whenFindByNativeQuery_thenReturnCarObject() {

        // give - precondition or setup
//        final var color = "Red";
//        final var brand = "kia";
//        var car = Car.getInstance(color, brand);
        this.carRepository.save(car);


        // when - action or the behaviour that we are going to test
        var saved = this.carRepository.findByNativeQuery(this.car.getColor(), this.car.getBrand());

        // then - verify the output
        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getBrand()).isEqualTo(this.car.getBrand());
        Assertions.assertThat(saved.getColor()).isEqualTo(this.car.getColor());

    }

}