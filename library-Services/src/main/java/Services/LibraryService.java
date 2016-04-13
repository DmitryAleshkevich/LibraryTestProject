package Services;

import libraryprojectmodel.Book;
import libraryprojectmodel.Card;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by aldm on 01.03.2016.
 */
public interface LibraryService {
    void register(String eMail, String login, String password);
    boolean isRegistered(String login, String password);
    void login(String login, String password);
    boolean bookExists(String title, String author);
    Set<Book> findBooks(Map<String,String> query);
    void rentBooks(Set<Book> books, Date returnDate, String login, String password);
    void pushBooks(Set<Book> books);
    Set<Book> getRentedBooks(String login, String password);
}
