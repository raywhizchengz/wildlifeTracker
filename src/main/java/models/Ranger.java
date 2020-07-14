package models;

import java.util.Objects;

public class Ranger {
    private String rangerName;
    private String rangerLocation;
    private int id;

    public Ranger(String rangerName, String rangerLocation){
        this.rangerName = rangerName;
        this.rangerLocation = rangerLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ranger ranger = (Ranger) o;
        return id == ranger.id &&
                Objects.equals(rangerName, ranger.rangerName) &&
                Objects.equals(rangerLocation, ranger.rangerLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rangerName, rangerLocation, id);
    }

    public String getRangerName() {
        return rangerName;
    }

    public void setRangerName(String rangerName) {
        this.rangerName = rangerName;
    }

    public String getRangerLocation() {
        return rangerLocation;
    }

    public void setRangerLocation(String rangerLocation) {
        this.rangerLocation = rangerLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
