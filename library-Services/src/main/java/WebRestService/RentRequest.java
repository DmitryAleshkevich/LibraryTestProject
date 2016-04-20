package WebRestService;

import libraryprojectmodel.Book;

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

    public RentRequest(Set<Book> books, Date date, String login, String password) {
        this.books = books;
        this.date = date;
        this.login = login;
        this.password = password;
    }

    public RentRequest() {}

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
}
