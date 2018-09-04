package ua.motorent.demo.common.dto;

public class MotoDto {

    private String name;

    private int volume;

    private Double price;

    public MotoDto() {
    }

    public MotoDto(String name, int volume, Double price) {
        this.name = name;
        this.volume = volume;
        this.price = price;
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
