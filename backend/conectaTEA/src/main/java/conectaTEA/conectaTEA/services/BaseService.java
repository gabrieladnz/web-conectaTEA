package conectaTEA.conectaTEA.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BaseService<T, K> {

    void create(T object);
    K getById(Long id);
    List<K> getAll();
    void update(Long id, T object);
    void delete(Long id);
}
