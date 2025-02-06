package hellojpa;

import hellojpa.cascade.Child;
import hellojpa.cascade.Parent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
@Slf4j
public class HellojpaApplication {

	private static void logic(EntityManager em) {
		Parent parent = new Parent();
		Child child1 = Child.builder()
				.parent(parent)
				.build();
		Child child2 = Child.builder()
				.parent(parent)
				.build();

		em.persist(parent);
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(HellojpaApplication.class, args);

		EntityManagerFactory emf = (EntityManagerFactory)context.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();
		try {
			// 비영속 상태
			logic(em);
			// 영속 상태
			tx.commit();
		} catch (Exception e) {
			log.error("{예외 발생}", e);
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
