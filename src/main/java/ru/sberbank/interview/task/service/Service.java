package ru.sberbank.interview.task.service;

import java.util.List;
import ru.sberbank.interview.task.controller.dto.res.GetListRes;
import ru.sberbank.interview.task.controller.dto.support.Entity;
import ru.sberbank.interview.task.exception.MissingIdException;

public interface Service {
    List<Entity> getEntitiesByIds(List<Long> ids) throws MissingIdException;
    List<Entity> getEntitiesByCodeAndSysname(Integer code, String sysname);
    GetListRes getList(String sysname);
}
