import dao.Sql2oRangerDao;
import models.Animal;
import models.Endangered;
import models.Ranger;
import dao.Sql2oAnimalDao;
import dao.Sql2oEndangeredDao;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App{

    public static void main(String[] args) {
        ProcessBuilder process = new ProcessBuilder();
        Integer port;

        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        port(port);

        staticFileLocation("/public");
        String connectionString = "jdbc:postgresql://localhost:5432/wildlife-tracker";
        Sql2o sql2o = new Sql2o(connectionString, "ray", "12345");
//        String connectionString = "jdbc:postgresql://ec2-35-168-54-239.compute-1.amazonaws.com:5432/d10ev3qbim5c34";
//        Sql2o sql2o = new Sql2o(connectionString, "gyanrlsrwskpxf", "4ce772d245a0bc8be5897edfef7dba5e389d30fbd5145568f13a493bcb6aace0");
        Sql2oAnimalDao animalDao = new Sql2oAnimalDao(sql2o);
        Sql2oEndangeredDao endangeredDao = new Sql2oEndangeredDao(sql2o);
        Sql2oRangerDao rangerDao = new Sql2oRangerDao(sql2o);

//        get: show all rangers
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Ranger> rangers = rangerDao.getAll();
            model.put("rangers", rangers);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

//    get: show all sighted animals
        get("/all/animals", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Animal> animals = animalDao.getAll();
            List<Endangered> endangers = endangeredDao.getAll();
            model.put("animals", animals);
            model.put("endangers", endangers);
            return new ModelAndView(model, "all-animals.hbs");
        }, new HandlebarsTemplateEngine());

//    get: show new Ranger from
        get("/rangers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "ranger-form.hbs");
        }, new HandlebarsTemplateEngine());

//     post: process new Ranger form
        post("/rangers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String rangerName = req.queryParams("rangerName");
            String rangerLocation = req.queryParams("rangerLocation");
            Ranger newRanger = new Ranger(rangerName, rangerLocation);
            rangerDao.add(newRanger);
            model.put("ranger", newRanger);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

//      get: show all animals sighted by a Ranger
        get("/rangers/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfRangerToView = Integer.parseInt(req.params("id"));
            Ranger foundRanger = rangerDao.findById(idOfRangerToView);
            model.put("ranger", foundRanger);
            List<Animal> allAnimalsByThisRanger = rangerDao.getAllAnimalsScoutedByRanger(idOfRangerToView);
            List<Endangered> allEndangeredByThisRanger = rangerDao.getAllEndangeredSightedByRanger(idOfRangerToView);
            model.put("endangers", allEndangeredByThisRanger);
            model.put("animals", allAnimalsByThisRanger);
            model.put("rangers", rangerDao.getAll());
            return new ModelAndView(model, "ranger-detail.hbs");
        }, new HandlebarsTemplateEngine());

//        show new animal-form

        get("/animals/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Ranger> allRangers = rangerDao.getAll();
            model.put("rangers", allRangers);
            return new ModelAndView(model, "animal-form.hbs");
        }, new HandlebarsTemplateEngine());

        //        post: process new animal-form
        post("/animals/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Ranger> allRangers = rangerDao.getAll();
            model.put("rangers", allRangers);
            String animalName = req.queryParams("animalName");
            int rangerId = Integer.parseInt(req.queryParams("rangerId"));
            Animal newAnimal = new Animal(animalName, rangerId );
            animalDao.add(newAnimal);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

//        show new endangered-animal form

        get("/endangers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Ranger> allRangers = rangerDao.getAll();
            model.put("rangers", allRangers);
            return new ModelAndView(model, "endangered-form.hbs");
        }, new HandlebarsTemplateEngine());

        //        post: process endangered animal form

        post("/endangers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Ranger> allRangers = rangerDao.getAll();
            model.put("rangers", allRangers);
            String endangeredName = req.queryParams("endangeredName");
            String endangeredHealth = req.queryParams("endangeredHealth");
            int rangerId = Integer.parseInt(req.queryParams("rangerId"));
            Endangered newEndangered = new Endangered(endangeredName, endangeredHealth, rangerId);
            endangeredDao.add(newEndangered);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update Ranger
        get("/rangers/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("editRanger", true);
            Ranger ranger = rangerDao.findById(Integer.parseInt(req.params("id")));
            model.put("ranger", ranger);
            return new ModelAndView(model, "ranger-form.hbs");
        }, new HandlebarsTemplateEngine());


//post: process a form to update Ranger
        post("/rangers/:id/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfRanger = Integer.parseInt(req.params("id"));
            String newRangerName = req.queryParams("newRangerName");
            String newRangerLocation = req.queryParams("newRangerLocation");
            rangerDao.update(idOfRanger, newRangerName, newRangerLocation);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());
    }
}