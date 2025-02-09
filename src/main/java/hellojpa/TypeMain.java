package hellojpa;

import hellojpa.domain.Member;
import hellojpa.domain.MemberType;
import hellojpa.domain.Team;
import hellojpa.domain.item.Item;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
@Slf4j
public class TypeMain {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TypeMain.class, args);

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
		member.setMemberType(MemberType.ADMIN);
		em.persist(member);


		System.out.println("===================================");
		em.flush();
		em.clear();
		System.out.println("===================================");

//		type1(em);
		type2(em);
	}

	private static void type1(EntityManager em) {
		System.out.println("TypeMain.type");
		String query = "select m.name, 'Hello', TRUE from Member m where m.memberType = :memberType";
		List<Object[]> result = em.createQuery(query)
				.setParameter("memberType", MemberType.ADMIN)
				.getResultList();

		for (Object[] objects : result) {
			System.out.println("objects = " + objects[0]);
			System.out.println("objects = " + objects[1]);
			System.out.println("objects = " + objects[2]);
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
