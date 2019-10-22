package com.postit.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.postit.entity.Comment;


@Repository
public class CommentDaoImpl implements CommentDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private CommentDao commentDao;
	
	@Override
	public List<Comment> listComments() {
		List<Comment> comment = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.save(comment);
			
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return comment;
	}

	@Override
	public Comment createComment(Comment comment) {
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			session.save(comment);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return comment;
	}

	@Override
	public Long deleteComment(Long commentId) {
		Session session = sessionFactory.getCurrentSession();
		
		Comment comment = null;
		
		try {
			session.beginTransaction();
			
			comment = session.get(Comment.class, commentId);
			session.delete(comment);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return commentId;
	}

}
