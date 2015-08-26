import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctor_office_test", null, null);
   }

  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteDoctorQuery = "DELETE FROM doctors *;";
      String deletePatientQuery = "DELETE FROM patients *;";
      String deleteSpecialtyQuery = "DELETE FROM specialties *;";
      con.createQuery(deleteDoctorQuery).executeUpdate();
      con.createQuery(deletePatientQuery).executeUpdate();
      con.createQuery(deleteSpecialtyQuery).executeUpdate();
    }
  }
}
