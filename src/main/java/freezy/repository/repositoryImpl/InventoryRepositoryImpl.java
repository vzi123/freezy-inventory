package freezy.repository.repositoryImpl;

import freezy.entities.Inventory;
import freezy.repository.InventoryRepository;
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
public class InventoryRepositoryImpl implements InventoryRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Inventory> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Inventory> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Inventory> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Inventory getOne(String s) {
        return null;
    }

    @Override
    public Inventory getById(String s) {
        return null;
    }

    @Override
    public Inventory getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends Inventory> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Inventory> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Inventory> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Inventory> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Inventory> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Inventory> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Inventory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Inventory> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Inventory> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Inventory> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Inventory> findAll() {
        return null;
    }

    @Override
    public List<Inventory> findAllById(Iterable<String> strings) {
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
    public void delete(Inventory entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Inventory> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Inventory> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Inventory> findAll(Pageable pageable) {
        return null;
    }
}
