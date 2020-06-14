package com.bookstore.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("select book from Book book")
	List<Book> getBooks();

	@Query("select book from Book book where book.createdTime >= :date")
	List<Book> getBooksByFromDate(@Param("date") ZonedDateTime dateFrom);

	@Query("select book from Book book where lower(book.title) like :searchName or lower(book.author) like :searchName")
	Page<Book> searchBook(@Param("searchName") String searchName, Pageable pageable);

	@Query("select book from Book book where book.title= :title")
	List<Book> getBookByTitle(@Param("title") String title);

}
