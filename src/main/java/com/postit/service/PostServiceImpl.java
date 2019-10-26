package com.postit.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postit.dao.PostDao;
import com.postit.entity.Comment;
import com.postit.entity.Post;
import com.postit.exception.EntityNotFoundException;

@Service
public class PostServiceImpl implements PostService {

  @Autowired
  private PostDao postDao;

  @Override
  public Post createPost(String username, Post post) {

    Post savedPost = postDao.createPost(username, post);
    return savedPost;
  }

  @Override
  public Long deletePostByPostId(String username, Long postId) throws EntityNotFoundException {

    return postDao.deletePostByPostId(username, postId);
  }

  @Override
  public List<Post> listPosts() {

    List<Post> postList = postDao.listPosts();
    return postList;
  }

  @Override
  public List<Comment> getCommentsByPostId(Long postId) throws EntityNotFoundException {

    List<Comment> commentList = postDao.getCommentsByPostId(postId);
    Set<Comment> commentSet = new HashSet<>(commentList);
    commentList = new ArrayList<Comment>(commentSet);
    commentList = commentList.stream().sorted(Comparator.comparing(Comment::getCommentId))
        .collect(Collectors.toList());
    return commentList;
  }
}
