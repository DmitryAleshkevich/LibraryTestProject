package Rest;

import Model.Book;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by aldm on 14.06.2016.
 */
@Component
class BookResponse {

    private String title;
    private String authors;
    private int id;
    private Date releaseYear;

    BookResponse(Book book) {
        this.title = book.getTitle();
        StringBuilder authorsTitles = new StringBuilder();
        book.getAuthors().forEach(it-> authorsTitles.append(it.getName()).append(", "));
        this.authors = authorsTitles.delete(authorsTitles.length()-2,authorsTitles.length()-1).toString();
        this.id = book.getId();
        this.releaseYear = book.getReleaseYear();
    }

    BookResponse() {}

    public String getTitle() {
        return this.title;
    }

    public String getAuthors() {
        return this.authors;
    }

    public int getId() {
        return this.id;
    }

    public Date getReleaseYear() {
        return this.releaseYear;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "BookResponse{" +
                "title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", id=" + id +
                ", releaseYear=" + releaseYear +
                '}';
    }
}
