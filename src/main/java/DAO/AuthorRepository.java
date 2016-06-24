package DAO;

import Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Set;

/**
 * Created by aldm on 24.02.2016.
 */
public interface AuthorRepository extends JpaRepository<Author,Integer> {
    Set<Author> findByNameLike(String author);
}
