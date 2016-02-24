package libraryDAO;

import libraryprojectmodel.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by aldm on 24.02.2016.
 */
public interface AuthorRepository extends JpaRepository<Author,Integer> {
}
