package Services;

import DAO.*;
import Model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by aldm on 12.04.2016.
 */
@Service
@Transactional
class LibraryServiceImpl implements LibraryService {

    private static final Logger logger = LogManager.getLogger(LibraryServiceImpl.class);

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

    @Override
    public void register(String eMail, String login, String password) {
        if (!isRegistered(login,password))
        {
            Credential credential = new Credential();
            credential.setEmail(eMail);
            credential.setLogin(login);
            credential.setPassword(password);
            credentialRepository.saveAndFlush(credential);
        }
        else
        {
            logger.info("User with login {} and password {} is already registered!",login,password);
        }
    }

    @Override
    public boolean isRegistered(String login, String password) {
        Credential credential = credentialRepository.findOneByLoginAndPassword(login,password);
        return (credential != null);
    }

    @Override
    public void login(String login, String password) {
        /* return when decide how to store connections */
    }

    @Override
    public boolean bookExists(String title, String author) {
        return (!findBook(title, author).isEmpty());
    }

    @Override
    /* in Map: key = title, value = author */
    public Set<Book> findBooks(Map<String, String> query) {
        Set<Book> books = new HashSet<>();
        query.forEach((k,v)-> books.addAll(findBook(k,v)));
        return books;
    }

    private Set<Book> findBook(String title, String author) {
        Set<Author> authors = authorRepository.findByNameLike(author);
        if (!authors.isEmpty())
        {
            return bookRepository.findByTitleLikeAndAuthorsIn(title, authors);
        }
        logger.info("Couldn`t find author {}!",author);
        return new HashSet<>();
    }

    @Override
    public void rentBooks(Set<Book> books, Date returnDate, String login, String password) {
        Credential oneByLoginAndPassword = credentialRepository.findOneByLoginAndPassword(login, password);
        Card card = cardRepository.findOneByCredential(oneByLoginAndPassword);
        libraryRepository.updateBookStoresDateReturn(libraryRepository.findByBookIn(books), returnDate, card);
    }

    @Override
    public void pushBooks(Set<Book> books) {
        libraryRepository.updateBookStoresDateDelete(libraryRepository.findByBookIn(books));
    }

    @Override
    public Set<Book> getRentedBooks(String login, String password) {
        Credential credential = credentialRepository.findOneByLoginAndPassword(login,password);
        if (credential != null)
        {
            Card card = cardRepository.findOneByCredential(credential);
            if (card != null)
            {
                return libraryRepository.findByCard(card).stream().map(Library::getBook).collect(Collectors.toSet());
            }
        }
        logger.info("Couldn`t find credential or card for login {} and password {}!",login,password);
        return new HashSet<>();
    }
}
