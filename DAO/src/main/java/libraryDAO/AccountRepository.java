package libraryDAO;

import libraryprojectmodel.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by aldm on 24.02.2016.
 */
public interface AccountRepository extends JpaRepository<Account,Integer> {
}
