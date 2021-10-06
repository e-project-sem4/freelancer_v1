package com.freelancer.repository.proposalAndContract;

import com.freelancer.model.proposalAndContract.Attachment;
import com.freelancer.model.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
