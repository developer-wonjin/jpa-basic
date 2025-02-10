package hellojpa;

import hellojpa.domain.Member;
import hellojpa.domain.Team;
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
public class DirectUseEntityMain {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DirectUseEntityMain.class, args);

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

	private static void init(EntityManager em) {
		System.out.println("HellojpaApplication.init");
		// 비영속
		Team teamA = Team.builder().name("teamA").build();
		em.persist(teamA);
		Team teamB = Team.builder().name("teamB").build();
		em.persist(teamB);

		Member member1 = new Member();
		member1.setName("member1");
		member1.changeTeam(teamA);
		em.persist(member1);

		Member member2 = new Member();
		member2.setName("member2");
		member2.changeTeam(teamA);
		em.persist(member2);

		Member member3 = new Member();
		member3.setName("member3");
		member3.changeTeam(teamB);
		em.persist(member3);


		System.out.println("===================================");
		em.flush();
		em.clear();
		System.out.println("===================================");

		useDefaultIdentityKey(em);
	}

	private static void useDefaultIdentityKey(EntityManager em) {
		System.out.println("DirectUseEntityMain.useDefaultIdentityKey");

		String query = "select count(m.id) from Member m";
//		String query = "select count(m) from Member m";

		List<Member> memberList = em.createQuery(query, Member.class).getResultList();
		for (Member member : memberList) {
			System.out.println("member = " + member);
		}
	}

	private static void logic(EntityManager em) {
		System.out.println("HellojpaApplication.logic");

	}
}
