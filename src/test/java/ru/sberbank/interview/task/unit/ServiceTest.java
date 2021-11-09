package ru.sberbank.interview.task.unit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.sberbank.interview.task.controller.dto.res.GetListRes;
import ru.sberbank.interview.task.controller.dto.support.Entity;
import ru.sberbank.interview.task.dao.model.EntityDao;
import ru.sberbank.interview.task.dao.repository.EntityRepository;
import ru.sberbank.interview.task.exception.MissingIdException;
import ru.sberbank.interview.task.service.Service;
import ru.sberbank.interview.task.service.ServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ServiceTest {

    private final EntityRepository entityRepository;
    private final Service service;
    private static List<EntityDao> preparedList;

    public ServiceTest() {
        entityRepository = mock(EntityRepository.class);
        this.service = new ServiceImpl(entityRepository);
    }

    @Test
    public void getEntitiesByIdsTest() throws MissingIdException {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(3L);
        ids.add(3L);
        ids.add(7L);
        List<EntityDao> responseEntities = new ArrayList<>();
        responseEntities.add(ServiceTest.preparedList.get(1));
        responseEntities.add(ServiceTest.preparedList.get(3));
        responseEntities.add(ServiceTest.preparedList.get(7));
        when(entityRepository.findAllById(ids)).thenReturn(responseEntities);

        List<Entity> entities = service.getEntitiesByIds(ids);

        verify(entityRepository, times(1)).findAllById(ids);
        Assertions.assertEquals(responseEntities.get(0).getCode(), entities.get(0).getCode());
        Assertions.assertEquals(responseEntities.get(1).getCode(), entities.get(1).getCode());
        Assertions.assertEquals(responseEntities.get(2).getCode(), entities.get(2).getCode());
    }

    @Test
    public void getEntitiesByIdsExceptionTest() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(3L);
        ids.add(3L);
        ids.add(7L);
        ids.add(0L);
        ids.add(5L);
        List<EntityDao> responseEntities = new ArrayList<>();
        responseEntities.add(ServiceTest.preparedList.get(1));
        responseEntities.add(ServiceTest.preparedList.get(3));
        responseEntities.add(ServiceTest.preparedList.get(7));
        when(entityRepository.findAllById(ids)).thenReturn(responseEntities);

        MissingIdException thrown = Assertions.assertThrows(
                MissingIdException.class, () -> service.getEntitiesByIds(ids));

        verify(entityRepository, times(1)).findAllById(ids);
        Assertions.assertEquals(List.of(0L, 5L), thrown.getMissingIds());
    }

    @Test
    public void getEntitiesByCodeAndSysnameTest(){
        int code = 333;
        String sysname = "red";
        when(entityRepository
                .findEntityDaoByCodeAndSysname(code, sysname))
                .thenReturn(List.of(
                                ServiceTest.preparedList.get(2),
                                ServiceTest.preparedList.get(6)
                ));

        List<Entity> entities = service.getEntitiesByCodeAndSysname(code, sysname);

        verify(entityRepository, times(1))
                .findEntityDaoByCodeAndSysname(code, sysname);
        Assertions.assertEquals(
                ServiceTest.preparedList.get(2).getDescription(),
                entities.get(0).getDescription());
        Assertions.assertEquals(
                ServiceTest.preparedList.get(6).getDescription(),
                entities.get(1).getDescription());
    }

    @Test
    public void getListTest(){
        String sysname = "red";
        when(entityRepository
                .findEntityDaoBySysname(sysname))
                .thenReturn(List.of(
                        ServiceTest.preparedList.get(2),
                        ServiceTest.preparedList.get(6),
                        ServiceTest.preparedList.get(7)
                ));

        GetListRes getListRes = service.getList(sysname);

        verify(entityRepository, times(1))
                .findEntityDaoBySysname(sysname);
        Assertions.assertEquals(
                ServiceTest.preparedList.get(2).getCode(),
                getListRes.getItems().get(0).getCode());
        Assertions.assertEquals(
                ServiceTest.preparedList.get(6).getCode(),
                getListRes.getItems().get(1).getCode());
        Assertions.assertEquals(
                ServiceTest.preparedList.get(7).getCode(),
                getListRes.getItems().get(2).getCode());
        Assertions.assertEquals(2, getListRes.getUnread());
    }


    //init
    @BeforeAll
    static void setUp() throws ParseException {
        preparedList = new ArrayList<>();
        preparedList.add(
                new EntityDao(0L,
                        111,
                        "orange",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2019-12-25 07:49:35"),
                        "some description",
                        "some data"));
        preparedList.add(
                new EntityDao(1L,
                        222,
                        "green",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2020-10-15 17:22:59"),
                        "some description2",
                        "some data2")
        );
        preparedList.add(
                new EntityDao(2L,
                        333,
                        "red",
                        null,
                        "some description3",
                        "some data3")
        );
        preparedList.add(
                new EntityDao(3L,
                        444,
                        "purple",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2021-01-07 12:19:19"),
                        "some description4",
                        "some data4")
        );
        preparedList.add(
                new EntityDao(4L,
                        444,
                        "yellow",
                        null,
                        "some description5",
                        "some data5")
        );
        preparedList.add(
                new EntityDao(5L,
                        555,
                        "purple",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2021-05-12 20:41:01"),
                        "some description6",
                        "some data6")
        );
        preparedList.add(
                new EntityDao(6L,
                        333,
                        "red",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2021-08-03 05:11:39"),
                        "some description7",
                        "some data7")
        );
        preparedList.add(
                new EntityDao(7L,
                        123,
                        "red",
                        null,
                        "some description8",
                        "some data8")
        );
    }
}
