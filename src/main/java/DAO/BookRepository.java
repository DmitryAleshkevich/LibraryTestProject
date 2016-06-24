package DAO;

import Model.Author;
import Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Created by aldm on 24.02.2016.
 */
public interface BookRepository extends JpaRepository<Book,Integer> {
    Set<Book> findByTitleLikeAndAuthorsIn(String title, Set<Author> authors);
}
