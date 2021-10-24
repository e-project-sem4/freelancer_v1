package com.freelancer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.freelancer.model.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long>,JpaSpecificationExecutor<Attachment> {
}
