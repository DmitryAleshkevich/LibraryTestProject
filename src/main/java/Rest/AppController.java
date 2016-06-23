package Rest;

import Services.LibraryService;
import Model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/** * Created by aldm on 14.04.2016.
 */
@RestController
public class AppController {

    private static final Logger logger = LogManager.getLogger(AppController.class);
    public static final String SUCCESS = "success";

    @Autowired
    private LibraryService libraryService;

    @RequestMapping(path = "/existbook",method = RequestMethod.GET)
    public Map<String, Boolean> bookExists(@RequestParam(value = "title") String title, @RequestParam(value = "author") String author) {
        final boolean bookExists = libraryService.bookExists(title, author);
        return Collections.singletonMap(SUCCESS, bookExists);
    }

    @RequestMapping(path = "/getrentedbooks",method = RequestMethod.GET)
    public Set<BookResponse> getRentedBooks(@RequestParam(value= "login") String login, @RequestParam(value = "password") String password) {
        final Set<Book> books = libraryService.getRentedBooks(login, password);
        return books.stream().map(BookResponse::new).collect(Collectors.toSet());
    }

    @RequestMapping(path = "/getbooks",method = RequestMethod.POST)
    public Set<BookResponse> getBooks(@RequestBody Map<String,String> query)
    {
        /* params logging */
        query.forEach((k,v) -> logger.info("Request URL: /getbooks ; Request params: Title-" + k + ", Author-" + v));
        final Set<BookResponse> responseSet = libraryService.findBooks(query).stream().map(BookResponse::new).collect(Collectors.toSet());
        responseSet.forEach(it->logger.info("Responce: Book title-" + it.getTitle()));
        return responseSet;
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public Map<String, Boolean> registerUser(@RequestBody String email, @RequestBody String login, @RequestBody String password) {
        libraryService.register(email, login, password);
        final boolean isRegistered = libraryService.isRegistered(login, password);
        return Collections.singletonMap(SUCCESS, isRegistered);
    }

    @RequestMapping(path = "/rentbooks",method = RequestMethod.POST)
    public Map<String, Boolean> rentBooks(@RequestBody RentRequest rentRequest) {
        final Set<BookResponse> bookResponseSet = rentRequest.getBooks();
        final Set<Book> books = getSetBooks(bookResponseSet);
        libraryService.rentBooks(books, rentRequest.getDate(), rentRequest.getLogin(), rentRequest.getPassword());
        return Collections.singletonMap(SUCCESS, true);
    }

    @RequestMapping(path = "/pushbooks",method = RequestMethod.POST)
    public Map<String, Boolean> pushBooks(@RequestBody Set<BookResponse> books) {
        final Set<Book> setBooks = getSetBooks(books);
        libraryService.pushBooks(setBooks);
        return Collections.singletonMap(SUCCESS, true);
    }

    private Set<Book> getSetBooks(Set<BookResponse> books) {
        Map<String,String> query = new HashMap<>();
        books.forEach(it->query.put(it.getTitle(),it.getAuthors()));
        return libraryService.findBooks(query);
    }

}
