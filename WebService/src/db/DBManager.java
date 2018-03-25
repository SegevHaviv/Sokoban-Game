package db;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class DBManager {
	private SessionFactory factory;

	public DBManager() {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		factory = config.buildSessionFactory();
	}

	public void addSolution(Solution solution) {

		
		try {
			if (!searchSolution(solution.getLevelMap()).isEmpty())
				return;
		} catch (NoResultException e) {}

		Session session = null;
		Transaction tx = null;

		try {
			session = factory.openSession();
			tx = session.beginTransaction();

			session.save(solution);
			tx.commit();

		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			
			System.out.println(ex.getMessage());
		} finally {
			if (session != null)
				session.close();
					}
	}

	public String searchSolution(String queryLevelMap) {
		
		Session session = null;
		Solution result = null;

		try {
			session = factory.openSession();

			Query<Solution> solution = session.createQuery("from Solution s WHERE s.levelMap = :prefix");

			solution.setParameter("prefix", queryLevelMap);

			try {
				result = solution.getSingleResult();
			} catch (NoResultException e) {
			}

			if (result != null)
				return result.getActions();

	} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return "";
	}

}
