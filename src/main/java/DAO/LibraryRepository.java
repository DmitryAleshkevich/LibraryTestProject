package DAO;

import Model.Book;
import Model.Card;
import Model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

/**
 * Created by aldm on 24.02.2016.
 */
public interface LibraryRepository extends JpaRepository<Library,Integer> {
    Set<Library> findByCard(Card card);
    Set<Library> findByBookIn(Set<Book> books);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Library library set library.returnDate = null, library.card = null where library in :libraries")
    void updateBookStoresDateDelete(@Param("libraries") Set<Library> libraries);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Library library set library.returnDate = :returnDate, library.card = :card where library.card is null and library in :libraries")
    void updateBookStoresDateReturn(@Param("libraries") Set<Library> libraries, @Param("returnDate") Date returnDate, @Param("card") Card card);
}
