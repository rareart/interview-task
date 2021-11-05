package ru.sberbank.interview.task.utils.preloader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.interview.task.dao.model.EntityDao;
import ru.sberbank.interview.task.dao.repository.EntityRepository;

import java.util.Date;

@Component
public class PreloadWithRandomData implements Preload {

    private final EntityRepository entityRepository;

    @Autowired
    public PreloadWithRandomData(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void execute() {
        entityRepository.save(
                new EntityDao(0L,
                        111,
                        "orange",
                        new Date(1629049060L),
                        "some description",
                        "some data")
        );
        entityRepository.save(
                new EntityDao(0L,
                        222,
                        "green",
                        new Date(1634275021L),
                        "some description2",
                        "some data2")
        );
        entityRepository.save(
                new EntityDao(0L,
                        333,
                        "red",
                        null,
                        "some description3",
                        "some data3")
        );
        entityRepository.save(
                new EntityDao(0L,
                        444,
                        "purple",
                        new Date(1606805144L),
                        "some description4",
                        "some data4")
        );
        entityRepository.save(
                new EntityDao(0L,
                        444,
                        "yellow",
                        null,
                        "some description5",
                        "some data5")
        );
        entityRepository.save(
                new EntityDao(0L,
                        555,
                        "purple",
                        new Date(1620975041L),
                        "some description6",
                        "some data6")
        );
        entityRepository.save(
                new EntityDao(0L,
                        333,
                        "red",
                        new Date(1610652798L),
                        "some description7",
                        "some data7")
        );
        entityRepository.save(
                new EntityDao(0L,
                        123,
                        "red",
                        null,
                        "some description8",
                        "some data8")
        );
    }
}
