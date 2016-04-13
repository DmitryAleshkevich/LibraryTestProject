package libraryDAO;

import libraryprojectmodel.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by aldm on 24.02.2016.
 */
public interface CredentialRepository extends JpaRepository<Credential,Integer> {
    Credential findOneByLoginAndPassword(String login, String password);
}
