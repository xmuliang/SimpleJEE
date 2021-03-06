package com.wickedhobo.DAO.hibernateDAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wickedhobo.object.User;

/*
 * Simple direct implementation.  It makes the bad assumption of transactions only happening 
 * within the scope of method.  But it's a good example of the DAO generic implementation.
 */
public class UserDAOHibOnlyImpl extends CommonDAOHibernateImpl<User> implements UserDAO {

	private static Logger log = LoggerFactory.getLogger(UserDAOHibOnlyImpl.class);
	
	@Override
	public void saveUser(User user) {
		try {
			HibUtil.beginTx();
			this.save(user);
			HibUtil.commitTx();
		}
		catch (Exception e) {
			log.error("Exception happened in DAO.hibernateDAO.UserDAOImpl.saveUser: " + e);
			HibUtil.rollBackTx();
		}
	}

	@Override
	public void updateUser(User user) {
		try {
			HibUtil.beginTx();
			this.update(user);
			HibUtil.commitTx();
		}
		catch (Exception e) {
			log.error("Exception happened in DAO.hibernateDAO.UserDAOImpl.updateUser: " + e);
			HibUtil.rollBackTx();
		}
	}

	@Override
	public User findUserByUsername(String username) {
		User user = null;
		try {
			HibUtil.beginTx();
			user = (User) this.find(User.class, username);
			HibUtil.commitTx();
		}
		catch (Exception e) {
			log.error("Exception happened in DAO.hibernateDAO.UserDAOImpl.findUserByUsername: " + e);
			HibUtil.rollBackTx();
		}
		return user;
	}

	@Override
	public void deleteUser(User user) {
		try {
			HibUtil.beginTx();
			this.delete(user);
			HibUtil.commitTx();
		}
		catch (Exception e) {
			log.error("Exception happened in DAO.hibernateDAO.UserDAOImpl.deleteUser: " + e);
			HibUtil.rollBackTx();
		}
	}

	@Override
	public List<User> listUsers() {
		List<User> users = new ArrayList<User>();
		try {
			HibUtil.beginTx();
			users = this.findAll(User.class);
			HibUtil.commitTx();
		}
		catch (Exception e) {
			log.error("Exception happened in DAO.hibernateDAO.UserDAOImpl.listUsers: " + e);
			HibUtil.rollBackTx();
		}
		return users;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> listUsersByRoles(String role) {
		List<User> users = new ArrayList<User>();
		try {
			String sql = "select new User(user.userName, user.firstName, user.lastName, user.password) "
								 + "from User as user inner join user.roles role where role.role = :role";
			HibUtil.beginTx();
			Query query = HibUtil.getSession().createQuery(sql);
			query.setParameter("role", role);
			users = (List<User>) query.list();
			HibUtil.commitTx();
		}
		catch (Exception e) {
			log.error("Exception happened in DAO.hibernateDAO.UserDAOImpl.listUsersByRoles: " + e);
			HibUtil.rollBackTx();
		}
		return users;
	}
}
