package com.freelancer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long message_id;
    private String attachmentLink;
    @ManyToOne
    @JoinColumn(name = "message_id",referencedColumnName = "id", insertable=false, updatable=false)
    private Message message;
}
