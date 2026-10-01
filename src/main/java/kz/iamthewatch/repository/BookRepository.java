package kz.iamthewatch.repository;

import kz.iamthewatch.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
        SELECT b FROM Book b
        WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :query, '%'))
        OR LOWER(b.description) LIKE LOWER(CONCAT('%', :query, '%'))
        ORDER BY b.ratingBall DESC
    """)
    Page<Book> searchByQuery(@Param("query") String query, Pageable pageable);
}