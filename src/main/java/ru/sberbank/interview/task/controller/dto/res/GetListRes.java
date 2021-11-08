package ru.sberbank.interview.task.controller.dto.res;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.sberbank.interview.task.controller.dto.support.Entity;

@AllArgsConstructor
@Getter
public class GetListRes {
    private List<Entity> items;
    private Integer unread;
}
