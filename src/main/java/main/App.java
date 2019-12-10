package main;
import db.DBConnection;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import users.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public  static void getCodewars(){

        DBConnection connection = new DBConnection();

        List<String> user = connection.getSingleUser();
        for (String code_wars_names: user){

            try {
             URL url = new URL("https://www.codewars.com/api/v1/users/"+code_wars_names);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {

                Map <String,String> map = new HashMap<>();


                map.put("output",output);
                System.out.println(map);
//                System.out.println(map.get("output"));
            }
            conn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

//    static Connection getDatabaseConnection(String defualtJdbcUrl) throws URISyntaxException, SQLException {
//        ProcessBuilder processBuilder = new ProcessBuilder();
//        String database_url = processBuilder.environment().get("DATABASE_URL");
//        if (database_url != null) {
//
//            URI uri = new URI(database_url);
//            String[] hostParts = uri.getUserInfo().split(":");
//            String username = "coder";
//            String password = "coder123";
//            String host = uri.getHost();
//
//            int port = uri.getPort();
//
//            String path = uri.getPath();
//            String url = String.format("jdbc:postgresql://%s:%s%s", host, port, path);
//
//            return DriverManager.getConnection(url, username, password);
//
//        }
//
//        return DriverManager.getConnection(defualtJdbcUrl);
//
//    }

    public static void main(String[] args) {


        staticFiles.location("/public");

        DBConnection db = new DBConnection();

        try {

            getCodewars();

//            get("https://www.codewars.com/api/v1/users/Theophelus", api);

            //Add users in the database
            get("/api/codewars/users/:username", (req, res)->{

                return null;
            }, new HandlebarsTemplateEngine());



            get("/api/codewars/users", (req, res)->{

                Map<String, Object> addUsers = new HashMap<>();

                addUsers.put("username", db.getUsers());

                return new ModelAndView(addUsers, "index.hbs");
            }, new HandlebarsTemplateEngine());


//            get("/api/codewars/users:users", (req, res) -> {}, new HandlebarsTemplateEngine());

//            System.out.println(e.getMessage());

            post("/api/codewars/users/", (req, res)->{
                //get from values
                String fullname = req.queryParams("fullname");
                String code_wars_username = req.queryParams("code_wars_username");

                Map<String, String> addUsers = new HashMap<>();

                String add = db.addUsers(fullname, code_wars_username);

                addUsers.put("usename", add);

                return new ModelAndView(addUsers, "index.hbs");

            }, new HandlebarsTemplateEngine());


//            get("/api/codewars/users:users", (req, res) -> {}, new HandlebarsTemplateEngine());


        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }



}
