package libraryprojectmodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by aldm on 15.02.2016.
 */
@Entity
public class Credential {
    private int id;

    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String login;

    @Basic
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private String password;

    @Basic
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
