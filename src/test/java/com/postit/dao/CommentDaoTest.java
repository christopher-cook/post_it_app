package com.postit.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;
import com.postit.exception.EntityNotFoundException;

public class CommentDaoTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @InjectMocks
  private CommentDaoImpl commentDao;

  @InjectMocks
  private User user;

  @InjectMocks
  private Comment comment;

  @InjectMocks
  private Post post;

  @Mock
  private UserDao userDao;

  @Mock
  private SessionFactory sessionFactory;

  @Mock
  private Session session;

  @Mock
  private Transaction transaction;

  List<Comment> commentList;

  @Before
  public void init() {

    commentList = new ArrayList<Comment>();

    Post post = new Post();
    User user = new User();

    user.setUserId(1L);
    user.setEmail("email1@email.com");
    user.setPassword("pwd1");
    user.setUsername("user1");

    post.setPostId(1L);
    post.setTitle("title");
    post.setDescription("content");
    
    comment.setCommentId(1L);
    comment.setText("comment");
    comment.setPost(post);
    comment.setUser(user);

    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.getTransaction()).thenReturn(transaction);
  }

  @Test
  public void createComments_Comment_Success() {

    when(userDao.getUserByUsername(anyString())).thenReturn(user);
    Comment actualComment = commentDao.createComment("user1", comment, 1L);
    assertEquals(comment.getCommentId(), actualComment.getCommentId());
  }

  @Test(expected = EntityNotFoundException.class)
  public void deleteComments_Comment_CommentNotFound() throws EntityNotFoundException {

    commentDao.deleteComment("user1", comment.getPost().getPostId());
  }
}
