package pl.sda.jpa.starter.basic;

import javax.persistence.*;
import java.util.List;

public class JpaBasic {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter");
            entityManager = entityManagerFactory.createEntityManager();

            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            StudentEntity jan = new StudentEntity("Jan", 3);
            StudentEntity maria = new StudentEntity("Maria", 4);
            
            entityManager.persist(jan);
            entityManager.persist(maria);

            TypedQuery<StudentEntity> query = entityManager.createQuery("from StudentEntity", StudentEntity.class);
            List<StudentEntity> students = query.getResultList();
            System.out.println("coaches = " + students);

            transaction.commit();
        } finally {

            if (entityManager != null) {
                entityManager.close();
                entityManagerFactory.close();
            }
        }
    }
}
