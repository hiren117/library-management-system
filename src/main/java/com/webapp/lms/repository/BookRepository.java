package com.webapp.lms.repository;
import com.webapp.lms.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
	// No need to write any methods, JpaRepository provides CRUD operations automatically
}
