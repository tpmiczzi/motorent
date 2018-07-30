package ua.motorent.demo.common.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "moto")
public class Moto {

    @Id
    @SequenceGenerator(allocationSize = 1, sequenceName = "moto_id_seq", name = "motoSeq")
    @GeneratedValue(generator = "motoSeq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "volume")
    @NotNull
    private int volume;

    @Column(name = "price")
    private Double price;

    public Moto() {
    }

    public Moto(String name, int volume, Double price) {
        this.name = name;
        this.volume = volume;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
