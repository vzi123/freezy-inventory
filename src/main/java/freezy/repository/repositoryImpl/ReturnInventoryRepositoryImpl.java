package freezy.repository.repositoryImpl;

import freezy.entities.ReturnInventory;
import freezy.repository.ReturnInventoryRepository;
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
public class ReturnInventoryRepositoryImpl implements ReturnInventoryRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends ReturnInventory> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ReturnInventory> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ReturnInventory> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ReturnInventory getOne(String s) {
        return null;
    }

    @Override
    public ReturnInventory getById(String s) {
        return null;
    }

    @Override
    public ReturnInventory getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends ReturnInventory> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ReturnInventory> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ReturnInventory> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ReturnInventory> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ReturnInventory> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ReturnInventory> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ReturnInventory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ReturnInventory> S save(S entity) {
        return null;
    }

    @Override
    public <S extends ReturnInventory> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ReturnInventory> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<ReturnInventory> findAll() {
        return null;
    }

    @Override
    public List<ReturnInventory> findAllById(Iterable<String> strings) {
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
    public void delete(ReturnInventory entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends ReturnInventory> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ReturnInventory> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ReturnInventory> findAll(Pageable pageable) {
        return null;
    }
}
