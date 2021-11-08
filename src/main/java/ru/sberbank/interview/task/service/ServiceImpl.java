package ru.sberbank.interview.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.interview.task.controller.dto.res.GetListRes;
import ru.sberbank.interview.task.controller.dto.support.Entity;
import ru.sberbank.interview.task.dao.model.EntityDao;
import ru.sberbank.interview.task.dao.repository.EntityRepository;
import ru.sberbank.interview.task.exception.MissingIdException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceImpl implements ru.sberbank.interview.task.service.Service {

    private final EntityRepository entityRepository;

    @Autowired
    public ServiceImpl(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<Entity> getEntitiesByIds(List<Long> ids) throws MissingIdException {
        List<EntityDao> responseEntities = entityRepository.findAllById(ids);

        //reduce complexity to O(n), backed by HashSet O(1) comparing complexity:
        Set<Long> entitiesIds = responseEntities.stream()
                .map(EntityDao::getId).collect(Collectors.toSet());

        ids.removeAll(entitiesIds);

        if(ids.size() > 0){
            throw new MissingIdException("Some ids are missing in the database", ids);
        }
        return convertEntity(responseEntities);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public List<Entity> getEntitiesByCodeAndSysname(Integer code, String sysname) {
        if(code != null && sysname != null){
            return convertEntity(
                    entityRepository.findEntityDaoByCodeAndSysname(code, sysname));
        }
        if(code != null){
            return convertEntity(
                    entityRepository.findEntityDaoByCode(code));
        }
        if(sysname != null){
            return convertEntity(
                    entityRepository.findEntityDaoBySysname(sysname));
        }
        return convertEntity(entityRepository.findAll());
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public GetListRes getList(String sysname) {
        List<EntityDao> responseEntities =
                entityRepository.findEntityDaoBySysname(sysname);

        if(responseEntities.size() == 0){
            return null;
        }

        long unread = responseEntities
                .stream()
                .map(EntityDao::getWatchedDttm)
                .filter(Objects::isNull).count();

        if(unread == 0L){
            return new GetListRes(new ArrayList<>(), 0);
        }

        return new GetListRes(convertEntity(responseEntities), (int) unread);
    }

    private List<Entity> convertEntity(List<EntityDao> inputList){
        return inputList.stream().map((e) -> new Entity(
                e.getId(),
                e.getCode(),
                e.getSysname(),
                e.getWatchedDttm(),
                e.getDescription(),
                e.getData())).collect(Collectors.toList());
    }
}
