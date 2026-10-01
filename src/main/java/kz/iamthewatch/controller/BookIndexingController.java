package kz.iamthewatch.controller;

import kz.iamthewatch.service.BookIndexingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookIndexingController {

    private final BookIndexingService bookIndexingService;

    @GetMapping("/reindex")
    public String reindexBooks() {
        bookIndexingService.reindexAllBooks();
        return "Переиндексация завершена!";
    }
}