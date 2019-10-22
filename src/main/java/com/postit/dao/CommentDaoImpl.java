package com.postit.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.postit.entity.Comment;
import com.postit.entity.User;
import com.postit.entity.Post;


@Repository
public class CommentDaoImpl implements CommentDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserDao userDao;
	
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
	public Comment createComment(String username, Comment comment, Long postId) {
		User user = userDao.getUserByUsername(username);
		
		comment.setUser(user);

		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			Post post = session.get(Post.class, postId);
			comment.setPost(post);
			session.save(comment);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return comment;
	}

	@Override
	public Long deleteComment(String username, Long commentId) {
		Session session = sessionFactory.getCurrentSession();
		
		Comment comment = null;
		
		try {
			session.beginTransaction();
			
			comment = session.get(Comment.class, commentId);
			if(comment.getUser().getUsername().equals(username)) {
				session.delete(comment);
				session.getTransaction().commit();
			}else {
				commentId = 0l;
			}
			
		} finally {
			session.close();
		}
		return commentId;
	}

}
