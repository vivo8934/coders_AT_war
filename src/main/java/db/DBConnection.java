package db;

import users.User;
import users.UserInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DBConnection implements UserInterface {

    private  String name;
    String code_wars_username;

    //database connection
    Connection conn;

    final String INSERT_STUDENT  =   "INSERT INTO users(codewars_username, counter) VALUES(?, ?)";
    final String CHECK_USER =        " SELECT codewarsusername FROM users WHERE codewarsusersname = ?";
    final String GET_SINGLE_USER =   "SELECT codewarsusername FROM users";
    final String GET_ALL_USERS =     "SELECT * FROM users";

    PreparedStatement insert_user;
    PreparedStatement check_users;
    PreparedStatement get_all_users;
    PreparedStatement get_single_user;

    public DBConnection(){


//        this.conn = conn;
        try{
            String username = "anele";
             String password = "coder123";
            final String url = "jdbc:postgresql://localhost/students_table?user=anele&password=coder123&ssl=true";

            conn = DriverManager.getConnection(url, username, password);

        insert_user = conn.prepareStatement(INSERT_STUDENT);

        } catch ( SQLException e) {
        System.out.println("Failed to connect to the database: " + e);
        }

    }

    @Override
    public String addUsers(String fullname, String codewarUserName) {

        if (!fullname.isEmpty() && !codewarUserName.isEmpty()){
            this.name = fullname;
            this.code_wars_username = codewarUserName;
        }

        try {
            //prepared statements
            check_users = conn.prepareStatement(CHECK_USER);
            check_users.setString(1, this.code_wars_username);

            ResultSet rs = check_users.executeQuery();

            //if user doen't exists add user
            if (!rs.next()){
                insert_user.setString(1, this.name);
                insert_user.setString(2, this.code_wars_username);

                insert_user.execute();
            }else
                return "user already exists";

        }catch (Exception e){

        }


        return null;
    }


    @Override
    public List<User> getUsers() {

        List<User> getAllUsers = new ArrayList<>();
        try {
            get_all_users = conn.prepareStatement(GET_ALL_USERS);

            ResultSet rs = get_all_users.executeQuery();

            while (rs.next()){
                getAllUsers.add(new User(rs.getString("fullname"), rs.getString("codewarsusername") ));
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        System.out.println(getAllUsers);

        return getAllUsers;

    }

    @Override
    public List<String> getSingleUser() {
        List<String> storeUsername = new ArrayList<>();
        try {
            get_single_user = conn.prepareStatement(GET_SINGLE_USER);
//            get_single_user.setString(1, codewarsusername);
            ResultSet rs = get_single_user.executeQuery();
            //if user exists get its code wars user name
            while (rs.next()) { storeUsername.add(rs.getString("codewarsusername"));}

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        System.out.println(storeUsername);
//        String username;
        return storeUsername;
    }
}
