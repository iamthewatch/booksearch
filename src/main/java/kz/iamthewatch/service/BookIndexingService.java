package kz.iamthewatch.service;

import kz.iamthewatch.entity.Book;
import kz.iamthewatch.entity.BookDoc;
import kz.iamthewatch.repository.BookElasticsearchRepository;
import kz.iamthewatch.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookIndexingService {

    private final BookRepository bookRepository;
    private final BookElasticsearchRepository bookElasticsearchRepository;

    @Transactional(readOnly = true)
    public void reindexAllBooks() {
        List<Book> movies = bookRepository.findAll();

        bookElasticsearchRepository.saveAll(
                movies.stream().map(
                        movie -> new BookDoc(
                                movie.getId(),
                                movie.getName(),
                                movie.getDescription()
                        )
                ).toList()
        );
    }
}