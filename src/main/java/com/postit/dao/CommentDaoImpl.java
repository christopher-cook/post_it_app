package com.postit.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.postit.entity.Comment;
import com.postit.entity.User;
import com.postit.exception.EntityNotFoundException;
import com.postit.entity.Post;

@Repository
public class CommentDaoImpl implements CommentDao {

  @Autowired
  private SessionFactory sessionFactory;

  @Autowired
  private UserDao userDao;

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
  public Long deleteComment(String username, Long commentId) throws EntityNotFoundException {

    Session session = sessionFactory.getCurrentSession();

    Comment comment = null;

    try {
      session.beginTransaction();

//      comment = (Comment) session.createQuery("FROM Comment where comment_id=" + commentId + "")
//          .uniqueResult();
       comment = session.get(Comment.class, commentId);
      if (comment.getUser().getUsername().equals(username)) {
        // remove association with post and user
        // solve deleted object would be re-saved by cascade (remove deleted object from
        // associations)
        comment.getPost().getCommentList().remove(comment);
        comment.setPost(null);
        comment.getUser().getCommentList().remove(comment);
        comment.setUser(null);
        // end
        session.delete(comment);
        session.getTransaction().commit();
      } else {
        throw new EntityNotFoundException("this comment not owned by the user");
      }
    } catch (EntityNotFoundException | NullPointerException e) {
      throw new EntityNotFoundException("this comment not exist/not owned by the user");
    } finally {

      session.close();
    }
    return commentId;
  }

}
