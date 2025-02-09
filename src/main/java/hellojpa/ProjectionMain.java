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

@SpringBootApplication
@Slf4j
public class ProjectionMain {

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

//		parameterBindingAndResult(em);
//		projection1(em);
//		projection2(em);
//		projection3(em);
//		projection4(em);
//		projection5(em);
//		projection6_1(em);
//		projection6_2(em);
		projection6_3(em);

	}

	// 파라미터 바인딩, result (single, list)
	private static void parameterBindingAndResult(EntityManager em) {
		System.out.println("HellojpaApplication.parameterBindingAndResult");
		Query queryWithNoType = em.createQuery("select m.name, m.age from Member m where m.name = :name");
		TypedQuery<Member> query = em.createQuery("select m from Member m where m.name = :name", Member.class);


		query.setParameter("name", "member1");

		Member findMember = query.getSingleResult();
		List<Member> memberList = query.getResultList();

		System.out.println("findMember = " + findMember);
		System.out.println("memberList = " + memberList);
	}

	// 엔티티 프로젝션
	private static void projection1(EntityManager em) {
		System.out.println("HellojpaApplication.projection1");
		List<Member> result = em.createQuery("select m from Member m", Member.class)
				.getResultList();
	}

	// 엔티티 프로젝션
	private static void projection2(EntityManager em) {
		System.out.println("HellojpaApplication.projection2");
		List<Member> result = em.createQuery("select m from Member m", Member.class)
				.getResultList();
		Member findMember = result.get(0);
		findMember.setAge(20);

	}

	// 조인
	private static void projection3(EntityManager em) {
		System.out.println("HellojpaApplication.projection3");

		// Team과 조인한 형태의 쿼리가 생성됨 (안 좋은 표기)
		List<Team> resultList1 = em.createQuery("select m.team from Member m", Team.class).getResultList();

		// 개선된 표기
		List<Team> resultList2 = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();
	}

	// 값 타입 프로젝션
	private static void projection4(EntityManager em) {
		System.out.println("HellojpaApplication.projection4");
		// 값타입은 반드시 소속된 엔티티의 참조를 타고들어간다. o.address 와 같이
		List<Address> resultList = em.createQuery("select o.address from Order o", Address.class).getResultList();

	}

	// 스칼라 타입 프로젝션
	private static void projection5(EntityManager em) {
		System.out.println("HellojpaApplication.projection5");
		em.createQuery("select distinct m.name, m.age from Member m").getResultList();

	}

	private static void projection6_1(EntityManager em) {
		System.out.println("HellojpaApplication.projection6");

		List resultList = em.createQuery("select m.name, m.age from Member m").getResultList();

		Object o = resultList.get(0);
		Object[] result = (Object[]) o;
		System.out.println("result = " + result[0]);
		System.out.println("result = " + result[1]);
	}

	private static void projection6_2(EntityManager em) {
		System.out.println("HellojpaApplication.projection6_2");
		List<Object[]> resultList = em.createQuery("select m.name, m.age from Member m").getResultList();

		Object[] result = resultList.get(0);
		System.out.println("result = " + result[0]);
		System.out.println("result = " + result[1]);

	}

	// 제일 좋은 방법 (MemberDTO의 패키지 경로까지 적어야한다)
	private static void projection6_3(EntityManager em) {
		System.out.println("HellojpaApplication.projection6_3");
		List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.name, m.age) from Member m", MemberDTO.class)
				.getResultList();
		MemberDTO result = resultList.get(0);
		System.out.println("result = " + result.getName());
		System.out.println("result = " + result.getAge());
	}

	private static void logic(EntityManager em) {
		System.out.println("HellojpaApplication.logic");

	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ProjectionMain.class, args);

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
