package freezy.repository.repositoryImpl;

import freezy.entities.Procurement;
import freezy.repository.ProcurementRepository;
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
public class ProcurementRepositoryImpl implements ProcurementRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Procurement> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Procurement> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Procurement> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Procurement getOne(String s) {
        return null;
    }

    @Override
    public Procurement getById(String s) {
        return null;
    }

    @Override
    public Procurement getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends Procurement> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Procurement> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Procurement> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Procurement> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Procurement> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Procurement> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Procurement, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Procurement> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Procurement> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Procurement> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Procurement> findAll() {
        return null;
    }

    @Override
    public List<Procurement> findAllById(Iterable<String> strings) {
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
    public void delete(Procurement entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Procurement> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Procurement> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Procurement> findAll(Pageable pageable) {
        return null;
    }
}
