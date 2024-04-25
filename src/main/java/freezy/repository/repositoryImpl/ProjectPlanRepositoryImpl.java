package freezy.repository.repositoryImpl;

import freezy.entities.ProjectPlan;
import freezy.repository.ProjectPlanRepository;
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
public class ProjectPlanRepositoryImpl implements ProjectPlanRepository {
    @Override
    public void flush() {

    }

    @Override
    public <S extends ProjectPlan> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ProjectPlan> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ProjectPlan> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ProjectPlan getOne(String s) {
        return null;
    }

    @Override
    public ProjectPlan getById(String s) {
        return null;
    }

    @Override
    public ProjectPlan getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends ProjectPlan> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ProjectPlan> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ProjectPlan> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ProjectPlan> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ProjectPlan> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ProjectPlan> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ProjectPlan, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends ProjectPlan> S save(S entity) {
        return null;
    }

    @Override
    public <S extends ProjectPlan> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ProjectPlan> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<ProjectPlan> findAll() {
        return null;
    }

    @Override
    public List<ProjectPlan> findAllById(Iterable<String> strings) {
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
    public void delete(ProjectPlan entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends ProjectPlan> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ProjectPlan> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ProjectPlan> findAll(Pageable pageable) {
        return null;
    }
}
