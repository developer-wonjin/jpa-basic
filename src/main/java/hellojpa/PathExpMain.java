package hellojpa;

import hellojpa.domain.Member;
import hellojpa.domain.MemberType;
import hellojpa.domain.Team;
import hellojpa.domain.item.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collection;
import java.util.List;

@SpringBootApplication
@Slf4j
public class PathExpMain {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PathExpMain.class, args);

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
//		member.setName("member");
		member.setAge(10);
		member.changeTeam(team);
		member.setMemberType(MemberType.ADMIN);
		em.persist(member);


		System.out.println("===================================");
		em.flush();
		em.clear();
		System.out.println("===================================");

//		path1(em);
//		path2(em);
//		path3(em);
//		path4(em);
		path5(em);
	}



	private static void path1(EntityManager em) {
		System.out.println("PathExpMain.path1 - 상태필드는 경로탐색의 끝");

		String query = "select m.name from Member m";
		List<String> resultList = em.createQuery(query, String.class).getResultList();
		for (String s : resultList) {
			System.out.println("s = " + s);
		}
	}

	// JPQL에 드러나지 않는 조인이 발생하니 비추천함
	private static void path2(EntityManager em) {
		System.out.println("PathExpMain.path2 - 단일 값 연관 경로 - 묵시적 내부조인발생 - 탐색O");
		String query1 = "select m.team from Member m";
//		String query2 = "select m.team.name from Member m";// 추가 탐색가능

		List<Team> resultList = em.createQuery(query1, Team.class).getResultList();
		for (Team team : resultList) {
			System.out.println("team = " + team);
		}
	}

	private static void path3(EntityManager em) {
		System.out.println("PathExpMain.path3");
		String query1 = "select t.members from Team t";

		List<Member> result = em.createQuery(query1, Member.class).getResultList();
		for (Member member : result) {
			System.out.println("member = " + member);
		}
	}

	private static void path4(EntityManager em) {
		System.out.println("PathExpMain.path4");
		String query1 = "select size(t.members) from Team t";

		Integer result = em.createQuery(query1, Integer.class).getSingleResult();
		System.out.println("result = " + result);
	}

	private static void path5(EntityManager em) {
		System.out.println("PathExpMain.path5 - 명시적 조인 - 굉장히 좋은 방법");
		String query1 = "select m.name from Team t join t.members m";
	}

	private static void logic(EntityManager em) {
		System.out.println("HellojpaApplication.logic");

	}
}
