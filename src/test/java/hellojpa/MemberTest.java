package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DataJpaTest 기본적으로 H2 in-memory 데이터베이스를 사용하여 테스트를 실행합니다.
class MemberTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private EntityManager em;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Test
    void testEm () {

        System.out.println("datasourceUrl = " + datasourceUrl);
        System.out.println("datasourceUsername = " + datasourceUsername);

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                applicationContext.getBean(LocalContainerEntityManagerFactoryBean.class);

        String persistenceUnitName = entityManagerFactoryBean.getPersistenceUnitName();
        System.out.println("persistenceUnitName = " + persistenceUnitName);

        Member member = Member.builder()
                        .id(1L)
                        .name("HelloAA")
                        .build();
        em.persist(member);

    }

}