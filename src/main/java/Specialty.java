import java.util.List;
import org.sql2o.*;


public class Specialty {
  private int id;
  private String specialty;

  public int getId() {
    return id;
  }
  public String getSpecialty() {
    return specialty;
  }

  public Specialty(String specialty) {
    this.specialty = specialty;
  }

  @Override
  public boolean equals(Object otherSpecialty) {
    if(!(otherSpecialty instanceof Specialty )) {
      return false;
    }
    else {
      Specialty newSpecialty = (Specialty) otherSpecialty;
      return this.getSpecialty().equals(newSpecialty.getSpecialty());
    }
  }

  public static List<Specialty> all() {
    String sql ="SELECT id, specialty FROM specialties";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Specialty.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql ="INSERT INTO specialties (specialty) values (:specialty)";
      this.id = (int) con.createQuery(sql,true)
      .addParameter("specialty", this.specialty)
      .executeUpdate()
      .getKey();
    }
  }

  public static Specialty find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql ="select * from specialties where id=:id";
      Specialty specialty = con.createQuery(sql)
      .addParameter("id",id)
      .executeAndFetchFirst(Specialty.class);
      return specialty;
    }
  }

  public List<Doctor> getDoctors() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM doctors where specialty_id=:id";
      return con.createQuery(sql)
       .addParameter("id", this.id)
       .executeAndFetch(Doctor.class);
    }
  }
}
