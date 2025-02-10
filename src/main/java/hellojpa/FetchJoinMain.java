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
public class FetchJoinMain {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(FetchJoinMain.class, args);

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

//		notJoinLazyLoading(em);
//		fetchjoinManyToOne(em);
//		fetchjoinOneToMany(em);
//		joinOneToMany(em);
		fetchJoin한계를_Batch로_극복(em);
	}



	// N + 1 문제가 있는 예제
	// MEMBER -> TEAM
	private static void notJoinLazyLoading(EntityManager em) {
		System.out.println("FetchJoinMain.notJoinLazyLoading");

		String query = "select m from Member m";

		// 1번 쿼리 (다측 엔티티를 조회)
		List<Member> memberList = em.createQuery(query, Member.class).getResultList();

		// N번 쿼리 (1측 엔티티를 LAZY로딩)
		for (Member member : memberList) {
			// 팀A (SQL발생)
			// 팀A (1차캐시)
			// 팀B (SQL발생)
			System.out.println("member = " + member);
		}
	}

	// 해결책 - FETCH조인
	// MEMBER -> TEAM
	private static void fetchjoinManyToOne(EntityManager em) {
		System.out.println("FetchJoinMain.fetchjoin");

		String query = "select m from Member m join fetch m.team";
//		String query = "select m from Member m left join fetch m.team";

		List<Member> memberList = em.createQuery(query, Member.class).getResultList();

		for (Member member : memberList) {
			System.out.println("member = " + member);
		}
	}

	// 해결책 - FETCH조인 - 컬렉션 - (강의시간 : 13분27초)
	// TEAM -> MEMBERS (관계형DB의 집합은 뻥튀기가 됨)
	// Hibernate 6버전 부터 애플리케이션 차원에서 distinc처리됨
	private static void fetchjoinOneToMany(EntityManager em) {
		System.out.println("FetchJoinMain.fetchjoinOneToMany");

		String query = "select t from Team t join fetch t.members";

		List<Team> resultList = em.createQuery(query, Team.class).getResultList();

		for (Team team : resultList) {
			System.out.println("team = " + team);
			List<Member> members = team.getMembers();
			for (Member member : members) {
				System.out.println("--> member = " + member);
			}
		}
	}

	// 일반조인
	// WHERE절에서 조인한 Member에 대한 조건을 쓸 수 있다 뿐이지 N쿼리 발생함!!!!
	// 이유: fetch조인이 아니기 때문에 members가 로딩되지 않음
	private static void joinOneToMany(EntityManager em) {
		System.out.println("FetchJoinMain.joinOneToMany");

		String query = "select t from Team t join t.members";

		List<Team> resultList = em.createQuery(query, Team.class).getResultList();

		for (Team team : resultList) {
			System.out.println("team = " + team);
			List<Member> members = team.getMembers();
			for (Member member : members) {
				System.out.println("--> member = " + member);
			}
		}
	}

	private static void fetchJoin한계를_Batch로_극복(EntityManager em) {
		System.out.println("FetchJoinMain.fetchJoin한계를_Batch로_극복");

		String query = "select t from Team t";

		List<Team> resultList = em.createQuery(query, Team.class)
				.setFirstResult(0)
				.setMaxResults(2)
				.getResultList();

		for (Team team : resultList) {
			System.out.println("team = " + team);
			List<Member> members = team.getMembers();
			for (Member member : members) {
				System.out.println("--> member = " + member);
			}
		}
	}

	private static void logic(EntityManager em) {
		System.out.println("HellojpaApplication.logic");

	}
}
