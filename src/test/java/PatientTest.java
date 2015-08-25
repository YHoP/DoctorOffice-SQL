import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;

public class PatientTest {

     @Rule
     public DatabaseRule database = new DatabaseRule();

     @Test
     public void all_emptyAtFirst() {
       assertEquals(Patient.all().size(), 0);
     }

     @Test
     public void equals_returnsTrueIfPatientNameAreTheSame() {
       Patient firstPatient = new Patient("John", 1, "01-01-2000");
       Patient secondPatient = new Patient("John", 1, "01-01-2000");
       assertTrue(firstPatient.equals(secondPatient));
     }

     @Test
      public void save_returnsTrueIfPatientNameAretheSame() {
       Patient myPatient = new Patient("John", 1, "01-01-2000");
       myPatient.save();
       assertTrue(Patient.all().get(0).equals(myPatient));
      }

    //   @Test
    //   public void save_assignsIdToObject() {
    //     Patient myPatient = new Patient("Mow the lawn", 1);
    //     myPatient.save();
    //     Patient savedPatient = Patient.all().get(0);
    //     assertEquals(myPatient.getId(), savedPatient.getId());
    //   }
     //
    //   @Test
    //   public void find_findsPatientInDatabase_true() {
    //     Patient myPatient = new Patient("Mow the lawn", 1);
    //     myPatient.save();
    //     Patient savedPatient = Patient.find(myPatient.getId());
    //     assertTrue(myPatient.equals(savedPatient));
    //   }
 }
