package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member member = new Member();
            member.setId(100L);
            member.setUsername("HelloJPA");

            Member findMember1 = em.find(Member.class, 100L);
            Member findMember2 = em.find(Member.class, 100L);

            System.out.println(findMember1 == findMember2);

            em.persist(member);
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }
}
