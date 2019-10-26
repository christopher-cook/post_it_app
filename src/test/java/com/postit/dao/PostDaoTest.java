package com.postit.dao;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.entity.User;
import com.postit.exception.EntityNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class PostDaoTest {

  @Mock
  UserDaoImpl userDao;

  @InjectMocks
  PostDaoImpl postDao;

  @Mock
  SessionFactory sessionFactory;

  @Mock
  private Session session;

  @Mock
  private Transaction transaction;

  @InjectMocks
  User user;

  @InjectMocks
  Post post;

  @InjectMocks
  Comment comment;

  List<Post> postList;

  List<Comment> commentList;

  @Mock
  private Query<Post> query;

  @Before
  public void init() {

    postList = new ArrayList<Post>();
    commentList = new ArrayList<Comment>();

    user.setUsername("user1");

    post.setDescription("testDescrip");
    post.setPostId(2L);
    post.setTitle("test title");
    post.setUser(user);

    comment.setCommentId(2L);
    comment.setPost(post);
    comment.setText("test comment");
    comment.setUser(user);

    when(sessionFactory.getCurrentSession()).thenReturn(session);
    when(session.getTransaction()).thenReturn(transaction);
    when(sessionFactory.getCurrentSession()).thenReturn(session);
  }

  @Test
  public void create_Post_Success() {

    when(userDao.getUserByUsername(any())).thenReturn(user);

    Post tempPost = postDao.createPost("testUsername", post);
    assertEquals(tempPost, post);
  }

  @Test
  public void deletePost_ById_Success() throws EntityNotFoundException {

    postList.add(post);
    user.setPostList(postList);

    when(userDao.getUserByUsername(any())).thenReturn(user);
    when(session.createQuery(anyString())).thenReturn(query);
    when(query.getSingleResult()).thenReturn(post);

    Long deletedPostId = postDao.deletePostByPostId("user1", 1L);
    assertEquals((long) 1L, (long) deletedPostId);
  }

  @Test(expected = EntityNotFoundException.class)
  public void deletePost_ById_PostEntityNotFound() throws EntityNotFoundException {

    postList.add(post);
    user.setPostList(postList);

    when(userDao.getUserByUsername(any())).thenReturn(user);
    when(session.createQuery(anyString())).thenReturn(query);
    when(query.getSingleResult()).thenReturn(null);

    postDao.deletePostByPostId("user1", 1L);
  }

  @Test
  public void getCommentsByPostId_CommentList_Success() throws EntityNotFoundException {

    post.setCommentList(commentList);
    when(session.createQuery(anyString())).thenReturn(query);
    when(query.getSingleResult()).thenReturn(post);

    List<Comment> actualCommentList = postDao.getCommentsByPostId(1L);
    assertEquals(commentList, actualCommentList);
  }

  @Test(expected = EntityNotFoundException.class)
  public void getCommentsByPostId_CommentList_PostEntityNotFoundException()
      throws EntityNotFoundException {

    when(session.createQuery(anyString())).thenReturn(query);
    when(query.getSingleResult()).thenReturn(null);

    postDao.getCommentsByPostId(1L);
  }

  @Test
  public void listPosts_All_Success() {

    postList.add(post);

    when(session.createQuery(anyString())).thenReturn(query);
    when(query.getResultList()).thenReturn(postList);

    List<Post> actualPostList = postDao.listPosts();
    assertEquals(postList, actualPostList);
  }
}
