package Rest;

import Services.LibraryService;
import Model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/** * Created by aldm on 14.04.2016.
 */
@RestController
public class AppController {

    @Autowired
    private LibraryService libraryService;

    @RequestMapping(path = "/existbook",method = RequestMethod.GET)
    public Map<String, Boolean> bookExists(@RequestParam(value = "title") String title, @RequestParam(value = "author") String author) {
        final boolean bookExists = libraryService.bookExists(title, author);
        return Collections.singletonMap("success", bookExists);
    }

    @RequestMapping(path = "/getrentedbooks",method = RequestMethod.GET)
    public Set<Book> getRentedBooks(@RequestParam(value= "login") String login, @RequestParam(value = "password") String password) {
        return libraryService.getRentedBooks(login, password);
    }

    @RequestMapping(path = "/getbooks",method = RequestMethod.POST)
    public Set<Book> getBooks(@RequestBody Map<String,String> query)
    {
        return libraryService.findBooks(query);
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public Map<String, Boolean> registerUser(@RequestBody String email, @RequestBody String login, @RequestBody String password) {
        libraryService.register(email, login, password);
        final boolean isRegistered = libraryService.isRegistered(login, password);
        return Collections.singletonMap("success", isRegistered);
    }

    @RequestMapping(path = "/rentbooks",method = RequestMethod.POST)
    public Map<String, Boolean> rentBooks(@RequestBody RentRequest rentRequest) {
        libraryService.rentBooks(rentRequest.getBooks(), rentRequest.getDate(), rentRequest.getLogin(), rentRequest.getPassword());
        return Collections.singletonMap("success", true);
    }

    @RequestMapping(path = "/pushbooks",method = RequestMethod.POST)
    public Map<String, Boolean> pushBooks(@RequestBody Set<Book> books) {
        libraryService.pushBooks(books);
        return Collections.singletonMap("success", true);
    }

}
