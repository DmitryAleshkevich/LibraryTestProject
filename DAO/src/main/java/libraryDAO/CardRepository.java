package libraryDAO;

import libraryprojectmodel.Card;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by aldm on 24.02.2016.
 */
public interface CardRepository extends JpaRepository<Card,Integer> {
}
