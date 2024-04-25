package freezy.repository.repositoryImpl;

import freezy.entities.Delivery;
import freezy.repository.DeliveryRepository;
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
public class DeliveryRepositoryImpl implements DeliveryRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Delivery> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Delivery> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Delivery> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Delivery getOne(String s) {
        return null;
    }

    @Override
    public Delivery getById(String s) {
        return null;
    }

    @Override
    public Delivery getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends Delivery> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Delivery> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Delivery> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Delivery> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Delivery> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Delivery> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Delivery, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Delivery> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Delivery> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Delivery> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Delivery> findAll() {
        return null;
    }

    @Override
    public List<Delivery> findAllById(Iterable<String> strings) {
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
    public void delete(Delivery entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Delivery> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Delivery> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Delivery> findAll(Pageable pageable) {
        return null;
    }
}
