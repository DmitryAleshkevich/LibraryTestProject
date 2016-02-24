package libraryDAO;

import libraryprojectmodel.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by aldm on 24.02.2016.
 */
public interface BookRepository extends JpaRepository<Book,Integer> {
}
