package freezy.repository.repositoryImpl;

import freezy.entities.CategoryUOMMap;
import freezy.repository.CategoryUOMMapRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class CategoryUOMMapRepositoryImpl implements CategoryUOMMapRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends CategoryUOMMap> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends CategoryUOMMap> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<CategoryUOMMap> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public CategoryUOMMap getOne(String s) {
        return null;
    }

    @Override
    public CategoryUOMMap getById(String s) {
        return null;
    }

    @Override
    public CategoryUOMMap getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends CategoryUOMMap> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends CategoryUOMMap> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends CategoryUOMMap> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends CategoryUOMMap> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends CategoryUOMMap> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends CategoryUOMMap> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends CategoryUOMMap, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends CategoryUOMMap> S save(S entity) {
        return null;
    }

    @Override
    public <S extends CategoryUOMMap> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<CategoryUOMMap> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<CategoryUOMMap> findAll() {
        return null;
    }

    @Override
    public List<CategoryUOMMap> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(CategoryUOMMap entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends CategoryUOMMap> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<CategoryUOMMap> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<CategoryUOMMap> findAll(Pageable pageable) {
        return null;
    }
}
