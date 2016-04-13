import Services.LibraryService;
import Services.LibraryServiceImpl;
import Services.SpringConfig;
import libraryDAO.*;
import libraryprojectmodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;
import static org.junit.Assert.*;

/**
 * Created by aldm on 12.04.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class LibraryServiceImplTest {

    @Resource
    private LibraryService libraryService;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private AuthorRepository authorRepository;

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
        Author author = getAuthor();
        Book book = getBook(author);
        assertTrue(libraryService.bookExists("myBook","Unknown"));
        deleteAuthorBook(author, book);
    }

    @Test
    public void bookNotExists() throws Exception {
        assertTrue(!libraryService.bookExists("123","456"));
    }

    @Test
    public void findBooks() throws Exception {
        Author author = getAuthor();
        Book book = getBook(author);
        Map<String,String> map = new HashMap<>();
        map.put("myBook","Unknown");
        assertTrue(!libraryService.findBooks(map).isEmpty());
        map.remove("myBook");
        map.put("123","456");
        assertTrue(libraryService.findBooks(map).isEmpty());
        deleteAuthorBook(author, book);
    }

    @Test
    public void rentBooks() throws Exception {
        libraryService.register("dmitryaleshkevich@gmail.com","aldm","fhvfutljy");
        Author author = getAuthor();
        Book book = getBook(author);
        Set<Book> books = new HashSet<>();
        books.add(book);
        Card card = getNewCard();
        Library library = getLibrary(book);
        libraryService.rentBooks(books,new Date(),"aldm","fhvfutljy");
        library = libraryRepository.findOne(library.getId());
        assertTrue(library.getCard() != null);
        libraryService.pushBooks(books);
        library = libraryRepository.findOne(library.getId());
        assertTrue(library.getCard() == null);
        DeleteAll(author, book, card, library);
    }

    @Test
    public void getRentedBooks() throws Exception {
        libraryService.register("dmitryaleshkevich@gmail.com","aldm","fhvfutljy");
        Author author = getAuthor();
        Book book = getBook(author);
        Set<Book> books = new HashSet<>();
        books.add(book);
        Card card = getNewCard();
        Library library = getLibrary(book);
        libraryService.rentBooks(books,new Date(),"aldm","fhvfutljy");
        assertTrue(!libraryService.getRentedBooks("aldm","fhvfutljy").isEmpty());
        libraryService.pushBooks(books);
        assertTrue(libraryService.getRentedBooks("aldm","fhvfutljy").isEmpty());
        DeleteAll(author, book, card, library);
    }

    private Card getNewCard() {
        Card card = new Card();
        card.setNumber(15);
        Credential credential = credentialRepository.findOneByLoginAndPassword("aldm","fhvfutljy");
        card.setCredential(credential);
        cardRepository.saveAndFlush(card);
        return card;
    }

    private Author getAuthor() {
        Author author = new Author();
        author.setName("Unknown");
        authorRepository.saveAndFlush(author);
        return author;
    }

    private Book getBook(Author author) {
        Book book = new Book();
        book.setReleaseYear(new Date());
        book.setTitle("myBook");
        HashSet<Author> authors = new HashSet<>();
        authors.add(author);
        book.setAuthors(authors);
        bookRepository.saveAndFlush(book);
        return book;
    }

    private void deleteAuthorBook(Author author, Book book) {
        author.setBooks(null);
        book.setAuthors(null);
        authorRepository.saveAndFlush(author);
        bookRepository.saveAndFlush(book);
        authorRepository.delete(author);
        bookRepository.delete(book);
    }

    private Library getLibrary(Book book) {
        Library library = new Library();
        library.setBook(book);
        libraryRepository.saveAndFlush(library);
        return library;
    }

    private void DeleteAll(Author author, Book book, Card card, Library library) {
        author.setBooks(null);
        book.setAuthors(null);
        library.setBook(null);
        library.setCard(null);
        libraryRepository.saveAndFlush(library);
        authorRepository.saveAndFlush(author);
        bookRepository.saveAndFlush(book);
        authorRepository.delete(author);
        bookRepository.delete(book);
        libraryRepository.delete(library);
        cardRepository.delete(card);
    }

}