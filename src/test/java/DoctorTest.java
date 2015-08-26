import java.util.Arrays;
import org.junit.*;
import static org.junit.Assert.*;

public class DoctorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Doctor.all().size(), 0);
  }


  @Test
  public void getPatientListMatchDoctorID() {
    Doctor newDoctor = new Doctor("Tom", 2);
    newDoctor.save();
    Patient patientOne = new Patient("Anna", newDoctor.getId(), "01-01-2001");
    patientOne.save();
    Patient patientTwo = new Patient("Bebe", newDoctor.getId(), "02-02-2002");
    patientTwo.save();
//    Patient newPatient = Patient.all().get(0);
    Patient doctorPatient = newDoctor.getPatients().get(0);

    assertTrue(doctorPatient.getPatientName().equals("Anna"));
  }

  @Test
  public void count_returnsCorrectCountsIfDoctorIdAreTheSame() {
    Doctor newDoctor = new Doctor("Banking", 1);
    newDoctor.save();
    Patient patientOne = new Patient("Anna", 1, "01-01-2001");
    patientOne.save();
    assertEquals(1, newDoctor.count(1));
  }

  @Test
  public void equals_returnsTrueIfDoctorsAreTheSame() {
      Doctor firstDoctor = new Doctor("Tom", 2);
      Doctor secondDoctor = new Doctor("Tom", 2);
      assertTrue(firstDoctor.equals(secondDoctor));
  }




  // @Test
  // public void save_savesIntoDatabase_true() {
  //   Category newCategory = new Category("Banking");
  //   newCategory.save();
  //   assertTrue(Category.all().get(0).equals(newCategory));
  // }

  // @Test
  // public void find_findsCategoryInDatabase_true() {
  //   Category myCategory = new Category("Banking");
  //   myCategory.save();
  //   Category savedCategory = Category.find(myCategory.getId());
  //   assertTrue(myCategory.equals(savedCategory));
  // }
  //
  // @Test
  // public void getTasks_retrievesAllTasksFromDatabase_tasksList() {
  //   Category myCategory = new Category("Banking");
  //   myCategory.save();
  //   Task firstTask = new Task("steal money", myCategory.getId());
  //   firstTask.save();
  //   Task secondTask = new Task("lots of money", myCategory.getId());
  //   secondTask.save();
  //   Task[] tasks = new Task[] { firstTask, secondTask };
  //   assertTrue(myCategory.getTasks().containsAll(Arrays.asList(tasks)));
  // }
}
