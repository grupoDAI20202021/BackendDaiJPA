package daibackend.demo.model.custom;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static daibackend.demo.util.ConstantUtils.CHAR_PATTERN;

public class DataPreferenceDashboard {

    private long idActivityType;
    private String name;
    private long total;
    private String color;

    public DataPreferenceDashboard() {
    }

    public DataPreferenceDashboard(long idActivityType, String name, String color , long total) {
        this.idActivityType = idActivityType;
        this.name = name;
        this.total = total;
        this.color=color;
    }

    public long getIdActivityType() {
        return idActivityType;
    }

    public void setIdActivityType(long idActivityType) {
        this.idActivityType = idActivityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotal() {
        return total;
    }


    public void setTotal(long total) {
        this.total = total;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
