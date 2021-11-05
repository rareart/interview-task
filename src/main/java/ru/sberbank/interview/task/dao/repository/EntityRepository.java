package ru.sberbank.interview.task.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberbank.interview.task.dao.model.EntityDao;

import java.util.List;

@Repository
public interface EntityRepository extends JpaRepository<EntityDao, Long> {

    List<EntityDao> findEntityDaoByCode(int code);

    List<EntityDao> findEntityDaoBySysname(String sysname);

    List<EntityDao> findEntityDaoByCodeAndSysname(int code, String sysname);
}
