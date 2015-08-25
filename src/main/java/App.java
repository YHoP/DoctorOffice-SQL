// import java.util.HashMap;
// import spark.ModelAndView;
// import spark.template.velocity.VelocityTemplateEngine;
// import static spark.Spark.*;
// import java.util.ArrayList;
//
// public class App {
//   public static void main(String[] args) {
//   staticFileLocation("/public");
//   String layout = "templates/layout.vtl";
//
//   get("/", (request, response) -> {
//     HashMap<String, Object> model = new HashMap<String, Object>();
//     model.put("template", "templates/index.vtl");
//     return new ModelAndView(model, layout);
//   }, new VelocityTemplateEngine());
//
//   post("/categories", (request, response) -> {
//     HashMap<String,Object> model = new HashMap<String, Object>();
//     //get categories
//     String name = request.queryParams("name");
//     Category newCategory = new Category(name);
//     newCategory.save();
//
//
//     //model.put("categories",newCategory);
//     model.put("categories", Category.all());
//     //put arraylist of categories on page
//     model.put("template", "templates/index.vtl");
//     return new ModelAndView(model, layout);
//   }, new VelocityTemplateEngine());
//
//   get("/categories/:id", (request, response) -> {
//     HashMap<String, Object> model = new HashMap<String, Object>();
//     model.put("category", Category.find(Integer.parseInt(request.params(":id"))));
//     model.put("template", "templates/tasks.vtl");
//
//     return new ModelAndView(model, layout);
//   }, new VelocityTemplateEngine());
//
//   post("/tasks", (request, response) -> {
//     HashMap<String,Object> model = new HashMap<String, Object>();
//     //get categories
//
//     String name = request.queryParams("name");
//
//     //**** instead of declaring category here, you could also just put it in model.put like above
//
//     Category category = Category.find(Integer.parseInt(request.queryParams("categoryId")));
//
//     String description = request.queryParams("description");
//     Task newTask = new Task(description, category.getId());
//     newTask.save();
//
//     model.put("category", category);
//     //put arraylist of categories on page
//     model.put("template", "templates/tasks.vtl");
//     return new ModelAndView(model, layout);
//   }, new VelocityTemplateEngine());
//
//
//   }
// }
