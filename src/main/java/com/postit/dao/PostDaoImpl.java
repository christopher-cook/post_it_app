package com.postit.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.postit.entity.User;
import com.postit.exception.EntityNotFoundException;
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
  public Long deletePostByPostId(String username, Long postId) throws EntityNotFoundException {

    User user = userDao.getUserByUsername(username);

    Session session = sessionFactory.getCurrentSession();
    try {
    	
      session.beginTransaction();
      
      Post post = session.get(Post.class, postId);
      
      if (post.getUser().getUsername().equals(user.getUsername())) {
        // solve deleted object would be re-saved by cascade (remove deleted object from associations)
        post.getUser().getPostList().remove(post);
        post.setUser(null);
        //end
        session.delete(post);
        session.getTransaction().commit();
<<<<<<< HEAD
        
        return postId;
        
      } else {
        return 0L;
      }
    } finally {
=======
      }else{
        throw new EntityNotFoundException("post entity not found");
      }
    } catch(EntityNotFoundException | NullPointerException e){
      throw new EntityNotFoundException("post entity not found / not owned by this user");
    }catch(Exception e){
      System.out.println(e);
      throw e;
    }finally {
>>>>>>> f6736d314c9ce0eed5ad1a8c16219658458a32a1
      session.close();
    }
    return postId;
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
  public List<Comment> getCommentsByPostId(Long postId) throws EntityNotFoundException {

    List<Comment> commentList = null;

    Session session = sessionFactory.getCurrentSession();
    try {
      session.beginTransaction();
      Post post = session.get(Post.class, postId);
      if(post == null){
        throw new EntityNotFoundException("post entity doesn't exist");
      }
      commentList = post.getCommentList();
    } catch(EntityNotFoundException e){
      throw new EntityNotFoundException("post entity doesn't exist");
    }finally {
      session.close();
    }
    return commentList;
  }
}
