package ec.mulsetect.learning.springboot.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String brand;

    private Car(Long id, String color, String brand) {
        this.id = id;
        this.color = color;
        this.brand = brand;
    }

    private Car(String color, String brand) {
        this.color = color;
        this.brand = brand;
    }

    public static Car getInstance(String color, String brand) {
        return new Car(color, brand);
    }

    public static Car getInstance(Long id, String color, String brand) {
        return new Car(id, color, brand);
    }

}
