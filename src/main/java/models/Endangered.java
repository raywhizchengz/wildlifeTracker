package models;

import java.util.Objects;

public class Endangered {
    private String endangeredName;
    private String endangeredHealth;
    private int id;
    private int rangerId;

    public Endangered(String endangeredName, String endangeredHealth, int rangerId){
        this.endangeredName = endangeredName;
        this.endangeredHealth = endangeredHealth;
        this.rangerId = rangerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endangered that = (Endangered) o;
        return id == that.id &&
                rangerId == that.rangerId &&
                Objects.equals(endangeredName, that.endangeredName) &&
                Objects.equals(endangeredHealth, that.endangeredHealth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endangeredName, endangeredHealth, id, rangerId);
    }

    public String getEndangeredName() {
        return endangeredName;
    }

    public void setEndangeredName(String endangeredName) {
        this.endangeredName = endangeredName;
    }

    public String getEndangeredHealth() {
        return endangeredHealth;
    }

    public void setEndangeredHealth(String endangeredHealth) {
        this.endangeredHealth = endangeredHealth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRangerId() {
        return rangerId;
    }

    public void setRangerId(int rangerId) {
        this.rangerId = rangerId;
    }
}
