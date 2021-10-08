package com.freelancer.service;

import com.freelancer.model.Job;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface InJobService {
    List<Job> findAll();

    List<Job> findAll(Sort sort);

    List<Job> findAllById(Iterable<Long> iterable);

    <S extends Job> List<S> saveAll(Iterable<S> iterable);

    void flush();

    <S extends Job> S saveAndFlush(S s);

    void deleteInBatch(Iterable<Job> iterable);

    void deleteAllInBatch();

    Job getOne(Long aLong);

    <S extends Job> List<S> findAll(Example<S> example);

    <S extends Job> List<S> findAll(Example<S> example, Sort sort);

    Page<Job> findAll(Pageable pageable);

    <S extends Job> S save(S s);

    Optional<Job> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Job job);

    void deleteAll(Iterable<? extends Job> iterable);

    void deleteAll();

    <S extends Job> Optional<S> findOne(Example<S> example);

    <S extends Job> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Job> long count(Example<S> example);

    <S extends Job> boolean exists(Example<S> example);
}
