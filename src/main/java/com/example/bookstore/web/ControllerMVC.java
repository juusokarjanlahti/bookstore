package com.example.bookstore.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.CategoryRepository;

@Controller
public class ControllerMVC {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Show all books MVC
    @GetMapping(value = "/booklist")
    public String bookList(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "booklist";
    }

    // RESTful service to get all books
    @RequestMapping(value = "/books")
    public @ResponseBody List<Book> bookListRest() {
        return (List<Book>) bookRepository.findAll();
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Book> findAll(@PathVariable("id") Long bookId) {
        return bookRepository.findById(bookId);
    }

    // Add new book MVC
    @RequestMapping(value = "/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("category", categoryRepository.findAll());
        return "addbook";
    }

    // Save book MVC
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Book book) {
        bookRepository.save(book);
        return "redirect:booklist";
    }

    // Delete book MVC
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
        bookRepository.deleteById(bookId);
        return "redirect:../booklist";
    }
}