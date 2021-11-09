package ru.sberbank.interview.task.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;
import ru.sberbank.interview.task.dao.model.EntityDao;
import ru.sberbank.interview.task.dao.repository.EntityRepository;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("local")
public class EntityRepositoryTest {

    private final TestEntityManager entityManager;
    private final EntityRepository entityRepository;
    private final DataSource dataSource;

    @Autowired
    public EntityRepositoryTest(TestEntityManager entityManager,
                                EntityRepository entityRepository,
                                DataSource dataSource) {
        this.entityManager = entityManager;
        this.entityRepository = entityRepository;
        this.dataSource = dataSource;
    }

    @AfterEach
    public void clear(){
        JdbcTestUtils.deleteFromTables(new JdbcTemplate(dataSource),"entity");
    }

    @Test
    public void findEntityDaoByCodeTest() throws ParseException {
        setUp();
        List<EntityDao> daoList = entityRepository
                .findEntityDaoByCode(444);

        Assertions.assertEquals(
                daoList.get(0).getSysname(), "purple");
        Assertions.assertEquals(
                daoList.get(1).getSysname(), "yellow");
    }

    @Test
    public void findEntityDaoBySysnameTest() throws ParseException {
        setUp();
        List<EntityDao> daoList = entityRepository
                .findEntityDaoBySysname("purple");

        Assertions.assertEquals(
                daoList.get(0).getCode(), 444);
        Assertions.assertEquals(
                daoList.get(1).getCode(), 555);
    }

    @Test
    public void findEntityDaoByCodeAndSysnameTest() throws ParseException {
        setUp();
        List<EntityDao> daoList = entityRepository
                .findEntityDaoByCodeAndSysname(333, "red");

        Assertions.assertEquals(
                daoList.get(0).getDescription(), "some description3"
        );
        Assertions.assertEquals(
                daoList.get(1).getDescription(), "some description7"
        );
    }


    //init
    private void setUp() throws ParseException {
        entityManager.persist(
                new EntityDao(null,
                        111,
                        "orange",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2019-12-25 07:49:35"),
                        "some description",
                        "some data")
        );
        entityManager.persist(
                new EntityDao(null,
                        222,
                        "green",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2020-10-15 17:22:59"),
                        "some description2",
                        "some data2")
        );
        entityManager.persist(
                new EntityDao(null,
                        333,
                        "red",
                        null,
                        "some description3",
                        "some data3")
        );
        entityManager.persist(
                new EntityDao(null,
                        444,
                        "purple",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2021-01-07 12:19:19"),
                        "some description4",
                        "some data4")
        );
        entityManager.persist(
                new EntityDao(null,
                        444,
                        "yellow",
                        null,
                        "some description5",
                        "some data5")
        );
        entityManager.persist(
                new EntityDao(null,
                        555,
                        "purple",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2021-05-12 20:41:01"),
                        "some description6",
                        "some data6")
        );
        entityManager.persist(
                new EntityDao(null,
                        333,
                        "red",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2021-08-03 05:11:39"),
                        "some description7",
                        "some data7")
        );
        entityManager.persist(
                new EntityDao(null,
                        123,
                        "red",
                        null,
                        "some description8",
                        "some data8")
        );
        entityManager.flush();
    }

}
