package com.freelancer.repository;

import com.freelancer.model.Attachment;
import com.freelancer.model.Complexity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long>,JpaSpecificationExecutor<Attachment> {
}
