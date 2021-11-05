package ru.sberbank.interview.task.dao.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Entity")
@Getter
@Setter
@ToString
public class EntityDao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    private Integer code;
    private String sysname;

    @Column(name = "watched_dttm")
    private Date watchedDttm;
    private String description;
    private String data;

}
