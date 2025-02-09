package hellojpa;

import hellojpa.domain.Member;
import hellojpa.domain.Team;
import hellojpa.value.Address;
import jakarta.persistence.*;
import jpql.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
@Slf4j
public class PagingMain {

	private static void init(EntityManager em) {
		System.out.println("HellojpaApplication.init");
		// 비영속

		IntStream.range(0, 100).forEach(
			i -> {
				Member member = new Member();
				member.setName("member" + i);
				member.setAge(i);
				em.persist(member);
			}
		);


		System.out.println("===================================");
		em.flush();
		em.clear();
		System.out.println("===================================");

		List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
				.setFirstResult(1)
				.setMaxResults(10)
				.getResultList();

		result.forEach(System.out::println);


	}

	private static void method(EntityManager em) {

	}



	private static void logic(EntityManager em) {
		System.out.println("HellojpaApplication.logic");

	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PagingMain.class, args);

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
