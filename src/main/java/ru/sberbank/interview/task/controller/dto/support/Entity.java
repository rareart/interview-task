package ru.sberbank.interview.task.controller.dto.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
@JsonPropertyOrder({"entityId", "code", "watched", "description", "data"})
public class Entity {

    @JsonProperty("entityId")
    private Long id; // В json это поле должно называться entityId

    private Integer code;

    @JsonIgnore
    private String sysname; // В json этого поля не должно быть

    @JsonProperty("watched")
    private Date watchedDttm; // В json это поле должно называться watched

    private String description;

    private String data;

}
