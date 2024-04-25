package freezy.repository.repositoryImpl;

import freezy.entities.TransferInventory;
import freezy.repository.TransferInventoryRepository;
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
public class TransferInventoryRepositoryImpl implements TransferInventoryRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends TransferInventory> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends TransferInventory> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<TransferInventory> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public TransferInventory getOne(String s) {
        return null;
    }

    @Override
    public TransferInventory getById(String s) {
        return null;
    }

    @Override
    public TransferInventory getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends TransferInventory> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends TransferInventory> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends TransferInventory> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends TransferInventory> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends TransferInventory> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends TransferInventory> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends TransferInventory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends TransferInventory> S save(S entity) {
        return null;
    }

    @Override
    public <S extends TransferInventory> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<TransferInventory> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<TransferInventory> findAll() {
        return null;
    }

    @Override
    public List<TransferInventory> findAllById(Iterable<String> strings) {
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
    public void delete(TransferInventory entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends TransferInventory> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<TransferInventory> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<TransferInventory> findAll(Pageable pageable) {
        return null;
    }
}
