package libraryDAO;

import libraryprojectmodel.Client;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by aldm on 24.02.2016.
 */
public interface ClientRepository extends JpaRepository<Client,Integer> {
}
