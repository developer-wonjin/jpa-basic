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
public class SubQueryMain {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SubQueryMain.class, args);

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

		subQuery(em);
	}

	private static void subQuery(EntityManager em) {
		System.out.println("SubQueryMain.subQuery");
		// 중첩서브쿼리: 나이가 평균보다 많은 회원
		String query1 = "select m from Member m where m.age > (select avg(m2.age) from Member m2)";
		// 중첩서브쿼리: 한 건이라도 주문한 고객
		String query2 = "select m from Member m where (select count(o) from Order o where m = o.member) > 0";

		// exists: 팀A 소속인 회원
		String query3 = "select m from Member m where exists (select t from m.team t where t.name = 'teamA')";

		// ALL: 전체 상품 각각의 재고보다 주문량이 많은 주문들
		String query4 = "select o from Order o where o.orderAmount > ALL(select p.stockAmount from Product p)";

		// ANY: 어떤 팀이든 팀에 소속된 회원
		String query5 = "select m from Member m where m.team = ANY(select t from Team t)";

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
