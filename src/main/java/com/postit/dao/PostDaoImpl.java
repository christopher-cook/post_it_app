package com.postit.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.postit.entity.User;
import com.postit.entity.Comment;
import com.postit.entity.Post;

@Repository
public class PostDaoImpl implements PostDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private UserDao userDao;

  @Override
  public Post createPost(String username, Post post) {

    User user = userDao.getUserByUsername(username);
    post.setUser(user);

    Session session = sessionFactory.getCurrentSession();
    try {
      session.beginTransaction();
      session.save(post);
      session.getTransaction().commit();
    } finally {
      session.close();
    }

    return post;
  }

  @Override
  public Long deletePostByPostId(String username, Long postId) {

    User user = userDao.getUserByUsername(username);

    Session session = sessionFactory.getCurrentSession();
    try {
    	
      session.beginTransaction();
      
      Post post = session.get(Post.class, postId);
      
      if (post.getUser().getUsername().equals(user.getUsername())) {
        session.delete(post);
        session.getTransaction().commit();
        
        return postId;
        
      } else {
        return 0L;
      }
    } finally {
      session.close();
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Post> listPosts() {

    List<Post> postList = null;

    Session session = sessionFactory.getCurrentSession();
    try {
       session.beginTransaction();
       postList = session.createQuery("From Post").getResultList();
    } finally {
      session.close();
    }

    return postList;
  }

	@Override
	public List<Comment> getCommentsByPostId(Long postId) {
		List<Comment> commentList = null;
		
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Post post = session.get(Post.class,  postId);
			commentList = post.getCommentList();
		} finally {
			session.close();
		}
		return commentList;
	}
}
