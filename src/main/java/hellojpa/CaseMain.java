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

import java.util.List;

@SpringBootApplication
@Slf4j
public class CaseMain {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CaseMain.class, args);

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

//		case1(em);
//		coalesce(em);
		nullif(em);
	}



	private static void case1(EntityManager em) {
		System.out.println("CaseMain.case1");
		String query = "select " +
				"case when m.age <= 10 then '학생요금' " +
				"	  when m.age >= 60 then '경로요금' " +
				"     else '일반요금' " +
				"end " +
				"from Member m";

		List<String> result = em.createQuery(query, String.class)
				.getResultList();

		for (String s : result) {
			System.out.println("s = " + s);
		}
	}

	private static void coalesce(EntityManager em) {
		System.out.println("CaseMain.coalesce");
		String query = "select coalesce(m.name, '이름없는 회원') from Member m ";
		List<String> resultList = em.createQuery(query, String.class).getResultList();
		for (String s : resultList) {
			System.out.println("s = " + s);
		}
	}

	private static void nullif(EntityManager em) {
		System.out.println("CaseMain.nullif");
		String query = "select nullif(m.name, '관리자') from Member m ";
		List<String> resultList = em.createQuery(query, String.class).getResultList();
		for (String s : resultList) {
			System.out.println("s = " + s);
		}
	}
	private static void type2(EntityManager em) {
		System.out.println("TypeMain.type2");
		String query = "select i from Item i where type(i) = Book";
		List<Item> resultList = em.createQuery(query, Item.class)
				.getResultList();

	}

	private static void logic(EntityManager em) {
		System.out.println("HellojpaApplication.logic");

	}
}
