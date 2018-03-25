package model.db;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import commons.DBScore;

public class ScoresDBManager {

	private SessionFactory factory;

	public ScoresDBManager() {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		Configuration config = new Configuration();
		config.configure();
		factory = config.buildSessionFactory();
	}

	public SessionFactory getFactory() {
		return factory;
	}

	public void addUser(DBUser user) {

		Session session = null;
		Transaction tx = null;
		if (isUserRegistered(user))
			return;

		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();

		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}

	}

	public void addLevel(DBLevel level) {

		Session session = null;
		Transaction tx = null;

		if (isLevelRegistered(level))
			return;

		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.save(level);
			tx.commit();

		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}

	}

	private boolean isUserRegistered(DBUser user) {

		Session session = null;

		try {
			session = factory.openSession();

			@SuppressWarnings("unchecked")
			Query<DBUser> query = session.createQuery("from Users u where u.userName = :param");

			query.setParameter("param", user.getUsername());
			List<DBUser> list = query.list();

			if (list.size() == 0)
				return false;

			else
				return true;

		} finally {
			if (session != null)
				session.close();
		}

	}

	private boolean isLevelRegistered(DBLevel level) {

		Session session = null;

		try {
			session = factory.openSession();
			@SuppressWarnings("unchecked")
			Query<DBLevel> query = session.createQuery("from Levels l where l.levelName = :param");

			query.setParameter("param", level.getLevelName());
			List<DBLevel> list = query.list();

			if (list.size() == 0)
				return false;

			else
				return true;

		} finally {
			if (session != null)
				session.close();
		}

	}

	public void addScore(DBUser user, DBLevel level, int stepsTaken, int finalTime) {
		Session session = null;
		Transaction tx = null;

		try {
			session = factory.openSession();
			tx = session.beginTransaction();

			DBScore score = new DBScore(user.getUsername(), level.getLevelName(), stepsTaken, finalTime);

			session.save(score);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<DBScore> getScores(String levelName, String userName, String order, int firstIndex, int maxResults) {
		Session session = null;

		try {
			session = factory.openSession();

			Query<DBScore> query = null;

			String orderby =null;
			if(order.equals("finalTime"))
				orderby="order by levelName,finalTime,stepsTaken";
			
			else{
				orderby="order by levelName,stepsTaken,finalTime";
			}

			if (levelName != null && userName != null) {
				query = session.createQuery(
						"from Scores s where s.levelName = :levelparam and s.userName = :userparam " + orderby);
				query.setParameter("levelparam", levelName);
				query.setParameter("userparam", userName);

			}
			else if (levelName != null) {

				query = session.createQuery("from Scores s where s.levelName = :levelparam " + orderby);
				query.setParameter("levelparam", levelName);

			} else if (userName != null) {

				query = session.createQuery("from Scores s where s.userName = :username " + orderby);
				query.setParameter("username", userName);

			}
			else{
				
				query = session.createQuery("from Scores s " + orderby);
				
				
			}
			query.setFirstResult(firstIndex);
			query.setMaxResults(maxResults);
			
			List<DBScore> leaderboard = query.list();
			
			return leaderboard;

		} finally {
			if (session != null)
				session.close();
		}

	}
}
