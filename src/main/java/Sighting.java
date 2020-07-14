import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.List;

public class Sighting implements DatabaseManagement{
  private int id;
  private int animal_id;
  private String location;
  private String ranger_name;
  private Timestamp last_sighted;

  public Sighting(int animal_id, String location, String ranger_name) {
    if (ranger_name.equals("")){
      throw new IllegalArgumentException("Please enter Ranger name.");
    }
    this.animal_id = animal_id;
    this.location = location;
    this.ranger_name = ranger_name;
    this.save();
  }

  public static void clearAll() {
    String sql = "DELETE from sightings";
    try(Connection con = DB.sql2o.open()){
      con.createQuery(sql).executeUpdate();
    }catch(Sql2oException ex) {
      System.out.println(ex);
    }
  }

//  public Sighting(int animal_id, String zone_b, String ole_nkrumah, Timestamp rightNow) {
//
// }

    public int getId(){
    return id;
  }

  public int getAnimalId(){
    return animal_id;
  }

  public String getLocation(){
    return location;
  }

  public String getRangerName(){
    return ranger_name;
  }

  public String getTimeSeen(){
    return String.format("%1$TD %1$TR", last_sighted);
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setRangerName(String rangerName) {
    this.ranger_name = rangerName;
  }

  @Override
  public void save() {
    String sql = "INSERT INTO sightings (animal_id, location, ranger_name, last_sighted) VALUES (:animal_id, :location, :ranger_name, now());";

    try (Connection con = DB.sql2o.open()) {
        this.id = (int) con.createQuery(sql, true)
            .addParameter("animal_id", this.animal_id)
            .addParameter("location", this.location)
            .addParameter("ranger_name", this.ranger_name)
            .executeUpdate()
            .getKey();
    }
  }

  public static List<Sighting> all() {
    String sql = "SELECT * FROM sightings ORDER BY last_sighted DESC;";

    try (Connection con = DB.sql2o.open()) {
        return con.createQuery(sql)
            .throwOnMappingFailure(false)
            .executeAndFetch(Sighting.class);
    }
  }

  public static List<Sighting> allByAnimal(int animalId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE animal_id = :animalId ORDER BY last_sighted DESC";
      return con.createQuery(sql)
        .addParameter("animalId", animalId)
        .executeAndFetch(Sighting.class);
    }
  }

  public boolean equals(Object otherSighting){
    if(!(otherSighting instanceof Sighting)){
      return false;
    }else{
      Sighting newSighting = (Sighting) otherSighting;
      return this.getAnimalId()==newSighting.getAnimalId() && this.getRangerName().equals(newSighting.getRangerName());
    }
  }

  public static Sighting find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM sightings WHERE id=:id;";
      Sighting sighting = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Sighting.class);
      return sighting;
    } catch (IndexOutOfBoundsException exception) {
      return null;
    }
  }

  public void delete(){
    try(Connection con = DB.sql2o.open()) {
      con.createQuery("DELETE FROM sightings WHERE id=:id")
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void update() {
      String sql = "UPDATE sightings SET location = :location, ranger_name = :rangername WHERE id = :id";

    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("location", location)
        .addParameter("rangername", ranger_name)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeUpdate();
    }
  }

}
