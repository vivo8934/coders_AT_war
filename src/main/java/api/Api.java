package api;

import com.google.gson.Gson;
import spark.Route;
import users.User;
import users.UserInterface;


public class Api {

    private UserInterface user;

    public Api(UserInterface user){
        this.user = user;
    }



   public Route addUsers(){
        return (req, res) ->{
            res.type("application/json");

            User userInputs = new Gson().fromJson(req.body(), User.class);

            String add = user.addUsers(userInputs.getFullname(),  userInputs.getCodewarsUserName());

            return null;
        };

    }

    public Route getSingleUser(){
        return (req, res)->{


            return null;


        };
    }

   public Route getAllUsers(){
        return (req, res) -> user.getUsersByCodewarUsername();
    }




}
