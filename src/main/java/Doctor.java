import java.util.List;
import org.sql2o.*;


public class Doctor {
  private int id;
  private String name;
  private int specialty_id;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getSpecialtyId() {
    return specialty_id;
  }

  public Doctor(String name, int specialty_id) {
    this.name = name;
    this.specialty_id = specialty_id;
  }

  @Override
  public boolean equals(Object otherDoctor) {
    if(!(otherDoctor instanceof Doctor )) {
      return false;
    }
    else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getName().equals(newDoctor.getName());
    }
  }

  public static List<Doctor> all() {
    String sql ="SELECT id, name, specialty_id FROM doctors ORDER BY name";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Doctor.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql ="INSERT INTO doctors (name) values (:name)";
      this.id = (int) con.createQuery(sql,true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
  }

  public static Doctor find(int id ) {
    try(Connection con = DB.sql2o.open()) {
      String sql ="SELECT * FROM doctors WHERE id=:id";
      Doctor doctor = con.createQuery(sql)
      .addParameter("id",id)
      .executeAndFetchFirst(Doctor.class);
      return doctor;
    }
  }

  public List<Specialty> getSpecialty() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM specialties where id=:specialty_id";
      return con.createQuery(sql)
       .addParameter("specialty_id", this.specialty_id)
       .executeAndFetch(Specialty.class);
    }
  }

  public String getSpecialtyDescription() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT specialty FROM specialties where id=:specialty_id";
      return con.createQuery(sql)
       .addParameter("specialty_id", this.specialty_id)
       .executeScalar(String.class);
    }
  }

  public List<Patient> getPatients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients where doctor_id=:id";
      return con.createQuery(sql)
       .addParameter("id", this.id)
       .executeAndFetch(Patient.class);
    }
  }

  public int count(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT count(*) FROM patients where doctor_id=:id";
      return (int) con.createQuery(sql)
        .addParameter("id",id)
        .executeScalar(Integer.class);
    }
 }

 public void updateName(String docName) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "UPDATE doctors SET name=:name WHERE id=:id";
     con.createQuery(sql)
       .addParameter("name", docName)
       .addParameter("id", this.id)
       .executeUpdate();
   }
 }

 public void updateSpecialty(int specialty_id) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "UPDATE doctors SET specialty_id=specialty_id WHERE id=:id";
     con.createQuery(sql)
       .addParameter("specialty_id", specialty_id)
       .addParameter("id", this.id)
       .executeUpdate();
   }
 }

}
