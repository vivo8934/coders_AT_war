package users;

import java.io.Serializable;

public class User implements Serializable {


    private String fullname;
    private String codewarsUserName;

    public User(String fullname, String codewarsUserName){
        this.fullname = fullname;
        this.codewarsUserName = codewarsUserName;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setCodewarsUserName(String codewarsUserName) {
        this.codewarsUserName = codewarsUserName;
    }

    public String getCodewarsUserName() {
        return codewarsUserName;
    }

}
