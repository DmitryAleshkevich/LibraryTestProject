package libraryDAO;

import libraryprojectmodel.Author;
import libraryprojectmodel.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

/**
 * Created by aldm on 24.02.2016.
 */
public interface BookRepository extends JpaRepository<Book,Integer> {
    Set<Book> findByTitleLikeAndAuthorsIn(String title, Set<Author> authors);
}
