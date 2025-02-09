package hellojpa;

import hellojpa.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class MemberTest {
    @Autowired
    private EntityManagerFactory emf;

//    // 잘못된 코드다. em을 직접 다루는 것은 허용되지 않음. 항상 emf에서 생성해야함
//    @Autowired
//    private  EntityManager em;

    @Test
    void testEm () {

    }

}