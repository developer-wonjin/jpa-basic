package hellojpa;

import hellojpa.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class Template {

	private static void init(EntityManager em) {
		System.out.println("HellojpaApplication.init");
		// 비영속
		Member member = new Member();
		member.setName("member1");
		member.setAge(10);

		System.out.println("===================================");
		em.persist(member);
		em.flush();
		em.clear();
		System.out.println("===================================");


	}

	private static void method(EntityManager em) {

	}



	private static void logic(EntityManager em) {
		System.out.println("HellojpaApplication.logic");

	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Template.class, args);

		EntityManagerFactory emf = (EntityManagerFactory)context.getBean("entityManagerFactory");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();
		try {
			init(em);
			logic(em);
			tx.commit();
		} catch (Exception e) {
			log.error("{예외 발생}", e);
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}

//	@AllArgsConstructor
//	@Getter
//	@Setter
//	static class MemberDTO {
//		private String name;
//		private int age;
//	}
}
