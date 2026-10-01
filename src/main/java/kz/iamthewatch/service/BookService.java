package kz.iamthewatch.service;

import kz.iamthewatch.entity.Book;
import kz.iamthewatch.entity.BookDoc;
import kz.iamthewatch.repository.BookElasticsearchRepository;
import kz.iamthewatch.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Comparator.comparingInt;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookElasticsearchRepository bookElasticsearchRepository;

    public Page<Book> searchBooks(String query, int page, int size) {
        var pageable = PageRequest.of(page, size);
        return bookRepository.searchByQuery(query, pageable);
    }

    public Page<Book> searchBooksViaElastic(String searchText, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookDoc> searchResults = bookElasticsearchRepository.searchByQuery(searchText, pageable);

        Map<Long, Integer> idsMap = new HashMap<>();
        List<BookDoc> bookDocs = searchResults.getContent();
        for (int i = 0; i < bookDocs.size(); i++) {
            idsMap.put(bookDocs.get(i).getId(), i);
        }

        Set<Long> ids = idsMap.keySet();

        List<Book> booksFromDb = bookRepository.findAllById(ids);
        booksFromDb.sort(comparingInt(movie -> idsMap.get(movie.getId())));

        return new PageImpl<>(booksFromDb, pageable, searchResults.getTotalElements());
    }
}