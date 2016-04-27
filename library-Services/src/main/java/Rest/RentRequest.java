package Rest;

import Model.Book;

import java.util.Date;
import java.util.Set;

/**
 * Created by aldm on 20.04.2016.
 */
class RentRequest {
    private Set<Book> books;
    private Date date;
    private String login;
    private String password;

    Set<Book> getBooks() {
        return books;
    }

    Date getDate() {
        return date;
    }

    String getLogin() {
        return login;
    }

    String getPassword() {
        return password;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
