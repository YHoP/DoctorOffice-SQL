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
    model.put("template", "templates/index.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/specialties", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    model.put("specialties", Specialty.all());
    model.put("template", "templates/specialties_form.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/doctors", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("doctors", Doctor.all());
    model.put("specialties", Specialty.all());
    model.put("template", "templates/doctors_form.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/patients", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();
    model.put("patients", Patient.all());
    model.put("template", "templates/patients.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());


  post("/specialties/new", (request, response) -> {
    HashMap<String,Object> model = new HashMap<String, Object>();

    String specialty = request.queryParams("specialty");
    Specialty newSpecialty = new Specialty(specialty);
    newSpecialty.save();

    model.put("specialties", Specialty.all());
    response.redirect("/specialties");
    return null;
  });

  get("/specialties/:id", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    Specialty thisSpecialty = Specialty.find(Integer.parseInt(request.params(":id")));

    model.put("specialty", thisSpecialty);
    model.put("doctors", thisSpecialty.getDoctors());
    model.put("specialties", Specialty.all());

    model.put("template", "templates/specialty.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/specialties/:id/new", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    int specialty_id = Integer.parseInt(request.queryParams(":id"));
    Specialty thisSpecialty = Specialty.find(specialty_id);
    String name = request.queryParams("name");
    Doctor newDoctor = new Doctor(name, specialty_id);
    newDoctor.save();

    model.put("specialty", thisSpecialty);
    model.put("doctors", thisSpecialty.getDoctors());

    response.redirect("/specialties/" + specialty_id);
    return null;
  });


  post("/doctors/new", (request, response) -> {
    HashMap<String,Object> model = new HashMap<String, Object>();

    int specialty_id = Integer.parseInt(request.queryParams("specialty_id"));
    String name = request.queryParams("name");
    Doctor newDoctor = new Doctor(name, specialty_id);
    newDoctor.save();

    model.put("doctors", Doctor.all());
    model.put("specialties", Specialty.all());
    model.put("template", "templates/doctors_form.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  get("/doctors/:id", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    Doctor thisDoctor = Doctor.find(Integer.parseInt(request.params(":id")));

    model.put("doctor", thisDoctor);
    model.put("patients", thisDoctor.getPatients());
    model.put("template", "templates/patient_form.vtl");
    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/doctors/:id/new", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    int doctor_id = Integer.parseInt(request.params(":id"));
    Doctor thisDoctor = Doctor.find(doctor_id);
    String patient_name = request.queryParams("patient_name");
    String dob = request.queryParams("dob");
    Patient newPatient = new Patient (patient_name, dob, doctor_id);
    newPatient.save();

    model.put("doctor", thisDoctor);
    model.put("patients", thisDoctor.getPatients());
    model.put("template", "templates/patient_form.vtl");
    response.redirect("/doctors/" + doctor_id);
    return null;
  });

  get("/doctors/:docId/patients/:id/delete", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    Doctor thisDoctor = Doctor.find(Integer.parseInt(request.params(":docId")));
    model.put("doctor", thisDoctor);
    String doctorId = request.params(":docId");

    Patient currentPatient = Patient.find(Integer.parseInt(request.params(":id")));
    currentPatient.delete();

    response.redirect("/doctors/" +doctorId);
    return null;
  });

  get("/doctors/:docId/patients/:id/update", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    Patient patient = Patient.find(Integer.parseInt(request.params(":id")));
    // Doctor currentDoc = Doctor.find(Integer.parseInt(request.params(":docId")));

    model.put("patient", patient);
    model.put("template", "templates/patient_form.vtl");

    return new ModelAndView(model, layout);
  }, new VelocityTemplateEngine());

  post("/doctors/:docId/patients/:id/updateName", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    Doctor thisDoctor = Doctor.find(Integer.parseInt(request.params(":docId")));

    String doctorId = request.params(":docId");
    String patientId = request.params(":id");
    String patientName = request.queryParams("patient_name");

    Patient currentPatient = Patient.find(Integer.parseInt(request.params(":id")));
    currentPatient.updateName(patientName);

    model.put("doctor", thisDoctor);
    response.redirect("/doctors/" + doctorId);
    return null;
  });


   }
}
