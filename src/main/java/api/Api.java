package api;

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

            return null;
        };
    }

   public Route getAllUsers(){
        return (req, res) -> user.getUsers();
    }




}
