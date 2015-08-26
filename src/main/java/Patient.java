import java.util.*;
import org.sql2o.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Patient {
  private int id;
  private String patient_name;
  private String dob;
  //SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
  private int doctor_id;

  public int getId() {
    return id;
  }

  public int getDoctorId() {
    return doctor_id;
  }

  public String getDob() {
    return dob;
  }

  public String getPatientName() {
    return patient_name;
  }

  public Patient(String patient_name, int doctor_id, String dob) {
    this.patient_name = patient_name;
    this.dob = dob;
    this.doctor_id = doctor_id;
  }

  @Override
  public boolean equals(Object otherPatient) {
    if (!(otherPatient instanceof Patient)) {
      return false;
    } else {
      Patient newPatient = (Patient) otherPatient;
      return this.getPatientName().equals(newPatient.getPatientName()) &&
             this.getId() == newPatient.getId() &&
             this.getDob() == newPatient.getDob() &&
             this.getDoctorId() == newPatient.getDoctorId();
    }
  }

  public static List<Patient> all() {
  String sql = "SELECT * FROM patients";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Patient.class);
  }
 }

  public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO patients (patient_name, doctor_id, dob) VALUES (:patient_name, :doctor_id, :dob)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("patient_name", patient_name)
      .addParameter("doctor_id", doctor_id)
      .addParameter("dob", dob)
      .executeUpdate()
      .getKey();
   }
}
   public static Patient find(int id) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "SELECT * FROM patients where id=:id";
     Patient patient = con.createQuery(sql)
       .addParameter("id", id)
       .executeAndFetchFirst(Patient.class);
     return patient;
   }
 }

 public void delete() {
  try(Connection con = DB.sql2o.open()) {
  String sql = "DELETE FROM patients WHERE id = :id;";
  con.createQuery(sql)
    .addParameter("id", id)
    .executeUpdate();
  }
}

  public void updateName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patients SET patient_name = :name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

}
