package libraryprojectmodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * Created by aldm on 09.02.2016.
 */
@Entity
public class Library {
    private int id;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Library() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Library)) return false;

        Library library = (Library) o;

        return getId() == library.getId();

    }

    @Override
    public int hashCode() {
        return getId();
    }

    private Account account;

    @OneToOne(optional = false,fetch = FetchType.LAZY)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    private Set<Card> cards;

    @OneToMany(fetch = FetchType.LAZY)
    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    private Set<Book> books;

    @OneToMany(fetch = FetchType.LAZY)
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    private Set<Service> services;

    @OneToMany(fetch = FetchType.LAZY)
    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    private Set<Credential> credentials;

    @OneToMany(fetch = FetchType.LAZY)
    public Set<Credential> getCredentials() {
        return credentials;
    }

    public void setCredentials(Set<Credential> credentials) {
        this.credentials = credentials;
    }
}
