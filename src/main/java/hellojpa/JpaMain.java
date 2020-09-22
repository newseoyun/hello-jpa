package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        
        //persistence.xml 에서 persistence-unit name 을 가져옴. 애플리케이션에서 오직 하나만 생성해서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager(); //쓰레드 간에 공유X. 사용하고 버려야 한다.

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            /*
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");
            em.persist(member);
            */

            /*
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA"); //set 후 persist 안해줘도 알아서 업데이트 해줌. 자바 컬렉션 사용하듯이
            */

            List<Member> result = em.createQuery("select m from Member as m", Member.class) //JPQL
                    .setFirstResult(5)
                    .setMaxResults(8)  //페이징이 매우 간단
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();

    }
}
