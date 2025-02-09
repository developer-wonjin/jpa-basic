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
public class JoinMain {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(JoinMain.class, args);

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
		Team team = Team.builder().name("teamA").build();
		em.persist(team);

		Member member = new Member();
		member.setName("member");
		member.setAge(10);
		member.changeTeam(team);
		em.persist(member);


		System.out.println("===================================");
		em.flush();
		em.clear();
		System.out.println("===================================");

		join0(em);
		join1(em);
		join2(em);
		join3(em);
		join4(em);
	}

	private static void join0(EntityManager em) {
		System.out.println("JoinMain.join0 - cross join(세타 조인)");
		String query = "select m from Member m, Team t";
		List<Member> result = em.createQuery(query, Member.class).getResultList();
	}


	private static void join1(EntityManager em) {
		System.out.println("JoinMain.join1 - inner join - on절 자동으로 생성");
		String query = "select m from Member m join m.team t";
		List<Member> result = em.createQuery(query, Member.class).getResultList();
	}

	private static void join2(EntityManager em) {
		System.out.println("JoinMain.join2 - outter join - on절 자동으로 생성");
		String query = "select m from Member m left join m.team t";
		List<Member> result = em.createQuery(query, Member.class).getResultList();
	}

	private static void join3(EntityManager em) {
		System.out.println("JoinMain.join3 - outter join - on절 자동으로 생성 - on절 조인대상 필터링 추가");
		String query = "select m from Member m left join m.team t on t.name = 'teamA'";
		List<Member> result = em.createQuery(query, Member.class).getResultList();
	}

	private static void join4(EntityManager em) {
		System.out.println("JoinMain.join4 - outter join - on절 자동으로 생성X - on절 조인대상 필터링 추가");
		String query = "select m from Member m left join Team t on t.name = 'teamA'";
		List<Member> result = em.createQuery(query, Member.class).getResultList();
	}



	private static void logic(EntityManager em) {
		System.out.println("HellojpaApplication.logic");

	}

//	@AllArgsConstructor
//	@Getter
//	@Setter
//	static class MemberDTO {
//		private String name;
//		private int age;
//	}
}
