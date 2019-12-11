package users;

import java.util.List;

public interface UserInterface {

    String getSingleUser(String codewarUsername);
    String addUsers(String fullname, String code_wars_username);
    List<String> getUsersByCodewarUsername();

}
