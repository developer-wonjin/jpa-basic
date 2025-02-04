package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

@SpringBootApplication
public class HellojpaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(HellojpaApplication.class, args);

		EntityManagerFactory emf = (EntityManagerFactory)context.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		Member member = Member.builder()
				.id(1L)
				.name("ㅁㅁㅁㅁㅁ")
				.build();
		em.persist(member);

		tx.commit();
		em.close();
		emf.close();

	}

}
