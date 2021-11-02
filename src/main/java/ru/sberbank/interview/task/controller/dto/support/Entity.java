package ru.sberbank.interview.task.controller.dto.support;

import java.util.Date;

public class Entity {

    private Long id; // В json это поле должно называться entityId
    private Integer code;
    private String sysname; // В json этого поля не должно быть
    private Date watchedDttm; // В json это поле должно называться watched
    private String description;
    private String data;

}
