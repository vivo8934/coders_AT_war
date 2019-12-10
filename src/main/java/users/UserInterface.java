package users;

import java.util.List;

public interface UserInterface {

    List<User> getUsers();
    String addUsers(String fullname, String code_wars_username);
    List<String> getSingleUser();

}
