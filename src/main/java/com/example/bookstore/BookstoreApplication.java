package com.example.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {
	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookDemo(BookRepository bookRepository, CategoryRepository categoryRepository) {
		return (args) -> {
			log.info("save a couple of categories");
			Category fiction = new Category("Fiction");
			Category nonFiction = new Category("Non-Fiction");
			categoryRepository.save(fiction);
			categoryRepository.save(nonFiction);

			log.info("save a couple of books");
			Book book1 = new Book("Book Title 1", "Author 1", 2020, "1234567890", 19.99, fiction);
			Book book2 = new Book("Book Title 2", "Author 2", 2021, "0987654321", 29.99, nonFiction);
			bookRepository.save(book1);
			bookRepository.save(book2);

			log.info("fetch all books");
			for (Book book : bookRepository.findAll()) {
				log.info(book.toString());
			}
		};
	}
}