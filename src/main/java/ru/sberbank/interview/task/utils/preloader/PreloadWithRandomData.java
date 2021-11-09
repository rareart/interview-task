package ru.sberbank.interview.task.utils.preloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.interview.task.dao.model.EntityDao;
import ru.sberbank.interview.task.dao.repository.EntityRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class PreloadWithRandomData implements Preload {

    private final EntityRepository entityRepository;

    @Autowired
    public PreloadWithRandomData(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void execute() throws ParseException {
        entityRepository.save(
                new EntityDao(null,
                        111,
                        "orange",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2019-12-25 07:49:35"),
                        "some description",
                        "some data")
        );
        entityRepository.save(
                new EntityDao(null,
                        222,
                        "green",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2020-10-15 17:22:59"),
                        "some description2",
                        "some data2")
        );
        entityRepository.save(
                new EntityDao(null,
                        333,
                        "red",
                        null,
                        "some description3",
                        "some data3")
        );
        entityRepository.save(
                new EntityDao(null,
                        444,
                        "purple",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2021-01-07 12:19:19"),
                        "some description4",
                        "some data4")
        );
        entityRepository.save(
                new EntityDao(null,
                        444,
                        "yellow",
                        null,
                        "some description5",
                        "some data5")
        );
        entityRepository.save(
                new EntityDao(null,
                        555,
                        "purple",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2021-05-12 20:41:01"),
                        "some description6",
                        "some data6")
        );
        entityRepository.save(
                new EntityDao(null,
                        333,
                        "red",
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                                .parse("2021-08-03 05:11:39"),
                        "some description7",
                        "some data7")
        );
        entityRepository.save(
                new EntityDao(null,
                        123,
                        "red",
                        null,
                        "some description8",
                        "some data8")
        );
    }
}
