import Services.LibraryService;
import Services.SpringConfig;
import Services.TestContentProducer;
import DAO.*;
import Model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.*;
import static org.junit.Assert.*;

/**
 * Created by aldm on 12.04.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
public class LibraryServiceImplTest {

    @Resource
    private LibraryService libraryService;

    @Autowired
    private LibraryRepository libraryRepository;

    @Resource
    private TestContentProducer testContentProducer;

    @Test
    public void isRegistered() throws Exception {
        libraryService.register("dmitryaleshkevich@gmail.com","aldm","fhvfutljy");
        assertTrue(libraryService.isRegistered("aldm","fhvfutljy"));
    }

    @Test
    public void isNotRegistered() throws Exception {
        assertTrue(!libraryService.isRegistered("ivan","ivan"));
    }

    @Test
    public void bookExists() throws Exception {
        Author author = testContentProducer.getAuthor();
        Book book = testContentProducer.getBook(author);
        assertTrue(libraryService.bookExists("myBook","Unknown"));
        testContentProducer.deleteAuthorBook(author, book);
    }

    @Test
    public void bookNotExists() throws Exception {
        assertTrue(!libraryService.bookExists("123","456"));
    }

    @Test
    public void findBooks() throws Exception {
        Author author = testContentProducer.getAuthor();
        Book book = testContentProducer.getBook(author);
        Map<String,String> map = new HashMap<>();
        map.put("myBook","Unknown");
        assertTrue(!libraryService.findBooks(map).isEmpty());
        map.remove("myBook");
        map.put("123","456");
        assertTrue(libraryService.findBooks(map).isEmpty());
        testContentProducer.deleteAuthorBook(author, book);
    }

    @Test
    public void rentBooks() throws Exception {
        libraryService.register("dmitryaleshkevich@gmail.com","aldm","fhvfutljy");
        Author author = testContentProducer.getAuthor();
        Book book = testContentProducer.getBook(author);
        Set<Book> books = new HashSet<>();
        books.add(book);
        Card card = testContentProducer.getNewCard();
        Library library = testContentProducer.getLibrary(book);
        libraryService.rentBooks(books,new Date(),"aldm","fhvfutljy");
        library = libraryRepository.findOne(library.getId());
        assertTrue(library.getCard() != null);
        libraryService.pushBooks(books);
        library = libraryRepository.findOne(library.getId());
        assertTrue(library.getCard() == null);
        testContentProducer.DeleteAll(author, book, card, library);
    }

    @Test
    public void getRentedBooks() throws Exception {
        libraryService.register("dmitryaleshkevich@gmail.com","aldm","fhvfutljy");
        Author author = testContentProducer.getAuthor();
        Book book = testContentProducer.getBook(author);
        Set<Book> books = new HashSet<>();
        books.add(book);
        Card card = testContentProducer.getNewCard();
        Library library = testContentProducer.getLibrary(book);
        libraryService.rentBooks(books,new Date(),"aldm","fhvfutljy");
        assertTrue(!libraryService.getRentedBooks("aldm","fhvfutljy").isEmpty());
        libraryService.pushBooks(books);
        assertTrue(libraryService.getRentedBooks("aldm","fhvfutljy").isEmpty());
        testContentProducer.DeleteAll(author, book, card, library);
    }

}