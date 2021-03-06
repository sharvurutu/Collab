package com.niit.collaboration.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.collaboration.dao.UserDAO;
import com.niit.collaboration.model.User;


@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	
	
		@Autowired
		SessionFactory sessionFactory;
		
		public UserDAOImpl(SessionFactory sessionFactory)
		{
			this.sessionFactory = sessionFactory;
		}
		
		@Transactional
		public boolean save(User user) {
			
			try
			{
			sessionFactory.getCurrentSession().save(user);
			return true;
			}
			
			catch(Exception e)
			{
			e.printStackTrace();	
					return false;
		}
		}
		
			
		public boolean delete(User user) {
			try
			{
			sessionFactory.getCurrentSession().delete(user);
			return true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return  false;
			}
			}
		
		public boolean update(User user) {
			try
			{
				sessionFactory.getCurrentSession().update(user);
				return true;
			}
			catch(Exception e)
			{
				
			e.printStackTrace();
			return false;
		}
		}
		
		@Transactional
		public User get(String UserId) {
				return (User) sessionFactory.getCurrentSession().get(User.class, UserId) ;
			
		}

		@Transactional
		public List<User> list() {
			String hql = "FROM User";
			Query query =  sessionFactory.getCurrentSession().createQuery(hql);
			return query.list();
		}

	@Transactional
		public User IsValidUser(String id, String password) {
		try{
			String hql = "FROM User o where o.id= :id and o.password= :password";
			Query st  = sessionFactory.getCurrentSession().createQuery(hql);
			st.setString("id", id);
			st.setString("password", password);
		return (User) st.uniqueResult();
		
		}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}

	/*public User authenticate(String id, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOnline(String id) {
		// TODO Auto-generated method stub
		
	}

	public void setOffLine(String loggedInUserID) {
		// TODO Auto-generated method stub
		*/
	}
	



