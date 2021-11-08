package ru.sberbank.interview.task.dao.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "entity")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EntityDao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    private Integer code;
    private String sysname;

    @Column(name = "watched_dttm")
    @Temporal(TemporalType.TIMESTAMP)
    private Date watchedDttm;
    private String description;
    private String data;
}
