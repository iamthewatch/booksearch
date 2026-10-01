package kz.iamthewatch.controller;

import kz.iamthewatch.entity.Book;
import kz.iamthewatch.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final BookService bookService;

    @GetMapping("/")
    public String home() {
        return "search";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam("query") String query,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model
    ) {
        Page<Book> booksPage = bookService.searchBooksViaElastic(query, page, size);
        model.addAttribute("booksPage", booksPage);
        model.addAttribute("query", query);
        return "search";
    }
}