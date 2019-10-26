package com.postit.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.postit.dao.PostDao;
import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.exception.EntityNotFoundException;

public class PostServiceTest {

  @Rule
  public MockitoRule rule = MockitoJUnit.rule();

  @InjectMocks
  private PostServiceImpl postService;

  @InjectMocks
  private Post post;

  @InjectMocks
  private Comment comment;

  @Mock
  private PostDao postDao;

  List<Post> postList;
  List<Comment> commentList;

  @Before
  public void init() {

    post.setPostId(1L);
    post.setTitle("title");
    post.setDescription("content");

    comment.setCommentId(1L);
    comment.setText("comment1");

    postList = new ArrayList<Post>();
    postList.add(post);
    commentList = new ArrayList<Comment>();
    commentList.add(comment);
  }

  @Test
  public void createPost_newPost_Success() {

    when(postDao.createPost(anyString(), any())).thenReturn(post);
    Post actualPost = postService.createPost("user1", post);
    assertEquals(post, actualPost);
  }

  @Test
  public void deletePostByPostId_Long_Success() throws EntityNotFoundException {

    when(postDao.deletePostByPostId(anyString(), anyLong())).thenReturn(1L);
    Long actualPostId = postService.deletePostByPostId("user1", 1L);
    assertEquals((long) 1L, (long) actualPostId);
  }

  @Test
  public void listPosts_PostList_Success() throws EntityNotFoundException {

    when(postDao.listPosts()).thenReturn(postList);
    List<Post> actualPostList = postService.listPosts();
    assertEquals(postList, actualPostList);
  }

  @Test
  public void getCommentsByPostId_CommentList_Success() throws EntityNotFoundException {

    when(postDao.getCommentsByPostId(anyLong())).thenReturn(commentList);
    List<Comment> actualCommentList = postService.getCommentsByPostId(1L);
    assertEquals(commentList, actualCommentList);
  }
}
