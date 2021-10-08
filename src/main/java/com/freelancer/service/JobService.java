package com.freelancer.service;

import com.freelancer.model.Job;
import com.freelancer.repository.ComplexityRepository;
import com.freelancer.repository.ExpectedDurationRepository;
import com.freelancer.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService implements InJobService{
    @Autowired
    private ExpectedDurationRepository expectedDurationRepository;
    @Autowired
    private ComplexityRepository complexityRepository;
    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public List<Job> findAll(Sort sort) {
        return jobRepository.findAll(sort);
    }

    @Override
    public List<Job> findAllById(Iterable<Long> iterable) {
        return jobRepository.findAllById(iterable);
    }

    @Override
    public <S extends Job> List<S> saveAll(Iterable<S> iterable) {
        return jobRepository.saveAll(iterable);
    }

    @Override
    public void flush() {
        jobRepository.flush();
    }

    @Override
    public <S extends Job> S saveAndFlush(S s) {
        return jobRepository.saveAndFlush(s);
    }

    @Override
    public void deleteInBatch(Iterable<Job> iterable) {
        jobRepository.deleteInBatch(iterable);
    }

    @Override
    public void deleteAllInBatch() {
        jobRepository.deleteAllInBatch();
    }

    @Override
    public Job getOne(Long aLong) {
        return jobRepository.getOne(aLong);
    }

    @Override
    public <S extends Job> List<S> findAll(Example<S> example) {
        return jobRepository.findAll(example);
    }

    @Override
    public <S extends Job> List<S> findAll(Example<S> example, Sort sort) {
        return jobRepository.findAll(example, sort);
    }

    @Override
    public Page<Job> findAll(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @Override
    public <S extends Job> S save(S s) {
        return jobRepository.save(s);
    }

    @Override
    public Optional<Job> findById(Long aLong) {
        return jobRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return jobRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return jobRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        jobRepository.deleteById(aLong);
    }

    @Override
    public void delete(Job job) {
        jobRepository.delete(job);
    }

    @Override
    public void deleteAll(Iterable<? extends Job> iterable) {
        jobRepository.deleteAll(iterable);
    }

    @Override
    public void deleteAll() {
        jobRepository.deleteAll();
    }

    @Override
    public <S extends Job> Optional<S> findOne(Example<S> example) {
        return jobRepository.findOne(example);
    }

    @Override
    public <S extends Job> Page<S> findAll(Example<S> example, Pageable pageable) {
        return jobRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Job> long count(Example<S> example) {
        return jobRepository.count(example);
    }

    @Override
    public <S extends Job> boolean exists(Example<S> example) {
        return jobRepository.exists(example);
    }
}
