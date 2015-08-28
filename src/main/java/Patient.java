import java.util.*;
import org.sql2o.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Patient {
  private int id;
  private String patient_name;
  private String dob;
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

  public Patient(String patient_name, String dob, int doctor_id) {
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
  String sql = "SELECT * FROM patients ORDER BY patient_name";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Patient.class);
  }
 }

  public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO patients (patient_name, dob, doctor_id) VALUES (:patient_name, :dob, :doctor_id)";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("patient_name", patient_name)
      .addParameter("dob", dob)
      .addParameter("doctor_id", doctor_id)
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

 public List<Doctor> getSpecialty() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "SELECT * FROM doctors where is=:doctor_id";
     return con.createQuery(sql)
      .addParameter("doctor_id", this.doctor_id)
      .executeAndFetch(Doctor.class);
   }
 }

 public String getDoctorName() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "SELECT name FROM doctors where id=:doctor_id";
     return (String) con.createQuery(sql)
      .addParameter("doctor_id", this.doctor_id)
      .executeAndFetchFirst(String.class);
   }
 }

 public void delete() {
  try(Connection con = DB.sql2o.open()) {
  String sql = "DELETE FROM patients WHERE id=:id;";
  con.createQuery(sql)
    .addParameter("id", id)
    .executeUpdate();
  }
}

  public void updateName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patients SET patient_name=:name WHERE id=:id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updateDob(String dob) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patients SET dob=:dob WHERE id=:id";
      con.createQuery(sql)
        .addParameter("dob", dob)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void updateDoctorId(int doctor_id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patients SET doctor_id=:doctor_id WHERE id=:id";
      con.createQuery(sql)
        .addParameter("doctor_id", doctor_id)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }


}
