import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
  staticFileLocation("/public");
  String layout = "templates/layout.vtl";

  get("/", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("template", "templates/administrator.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/doctors", (request, response) -> {
    HashMap<String,Object> model = new HashMap<String, Object>();
    //get categories
    String name = request.queryParams("name");
    int specialty_id = Integer.parseInt(request.queryParams("specialty_id"));
    Doctor newDoctor = new Doctor(name, specialty_id);
    newDoctor.save();


    //model.put("categories",newCategory);
    model.put("doctors", Doctor.all());
    //put arraylist of categories on page
    model.put("template", "templates/administrator.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/doctors/:number", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    Doctor currentDoc = Doctor.find(Integer.parseInt(request.params(":number")));
    model.put("doctor", currentDoc);
    model.put("patients", currentDoc.getPatients());
    model.put("template", "templates/doctor.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/doctors/:docId/patients/:id", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    Doctor currentDoc = Doctor.find(Integer.parseInt(request.params(":docId")));
    model.put("doctor", currentDoc);
    String doctorId = request.params(":docId");

    Patient currentPatient = Patient.find(Integer.parseInt(request.params(":id")));
    currentPatient.delete();
    // model.put("template", "templates/doctor.vtl");
    response.redirect("/doctors/" +doctorId);
    return null;
  });

  post("/doctors/:id/new", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    String patient_name = request.queryParams("patient_name");
    int doctor_id = Integer.parseInt(request.params(":id"));
    String dob = request.queryParams("dob");

    Patient newPatient = new Patient(patient_name, doctor_id, dob);
    newPatient.save();

    Doctor currentDoc = Doctor.find(Integer.parseInt(request.params(":id")));
    model.put("doctor", currentDoc);
    model.put("patients", currentDoc.getPatients());
    model.put("template", "templates/doctor.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());


  // post("/patients", (request, response) -> {
  //   HashMap<String, Object> model = new HashMap<String, Object>();
  //
  //
  //   String patient_name = request.queryParams("patient_name");
  //   int doctor_id = Integer.parseInt(request.queryParams("doctor_id"));
  //   String dob = request.queryParams("dob");
  //
  //   Patient newPatient = new Patient(patient_name, doctor_id, dob);
  //   newPatient.save();
  //
  //   model.put("patients",Patient.all());
  //   model.put("template", "templates/patient_form.vtl");
  //
  //   return new ModelAndView(model, layout);
  // }, new VelocityTemplateEngine());


   }
}
