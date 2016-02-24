package libraryDAO;

import libraryprojectmodel.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jca.cci.core.InteractionCallback;

/**
 * Created by aldm on 24.02.2016.
 */
public interface LibraryRepository extends JpaRepository<Library,Integer> {
}
