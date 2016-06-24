package DAO;

import Model.Card;
import Model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by aldm on 24.02.2016.
 */
public interface CardRepository extends JpaRepository<Card,Integer> {
    Card findOneByCredential(Credential credential);
}
