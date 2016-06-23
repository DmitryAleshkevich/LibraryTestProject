package Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by aldm on 15.02.2016.
 */
@Entity
public class Credential {

    private int id;
    private String login;
    private String password;
    private String email;

    public Credential() {
    }

    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Credential)) {
            return false;
        }
        Credential credential = (Credential) o;
        return getId() == credential.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
