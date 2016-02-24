package libraryDAO;

import libraryprojectmodel.Service;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by aldm on 24.02.2016.
 */
public interface ServiceRepository extends JpaRepository<Service,Integer> {
}
