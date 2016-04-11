package libraryprojectmodel;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
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

    private Book book;

    @OneToOne(fetch = FetchType.LAZY)
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    private Card card;

    @OneToOne
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    private Date returnDate;

    @Temporal(TemporalType.DATE)
    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
