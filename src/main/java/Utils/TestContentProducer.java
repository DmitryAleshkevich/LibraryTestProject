package Utils;

import DAO.*;
import Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by aldm on 19.04.2016.
 */
@Service
public class TestContentProducer {

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

    public Card getNewCard() {
        Card card = new Card();
        card.setNumber(15);
        Credential credential = credentialRepository.findOneByLoginAndPassword("aldm","fhvfutljy");
        card.setCredential(credential);
        cardRepository.saveAndFlush(card);
        return card;
    }

    public Author getAuthor() {
        Author author = new Author();
        author.setName("Unknown");
        authorRepository.saveAndFlush(author);
        return author;
    }

    public Book getBook(Author author) {
        Book book = new Book();
        book.setReleaseYear(new Date(1461069936812L));
        book.setTitle("myBook");
        HashSet<Author> authors = new HashSet<>();
        authors.add(author);
        book.setAuthors(authors);
        bookRepository.saveAndFlush(book);
        return book;
    }

    public void deleteAuthorBook(Author author, Book book) {
        author.setBooks(null);
        book.setAuthors(null);
        authorRepository.saveAndFlush(author);
        bookRepository.saveAndFlush(book);
        authorRepository.delete(author);
        bookRepository.delete(book);
    }

    public Library getLibrary(Book book) {
        Library library = new Library();
        library.setBook(book);
        libraryRepository.saveAndFlush(library);
        return library;
    }

    public void DeleteAll(Author author, Book book, Card card, Library library) {
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
