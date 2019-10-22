package com.postit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.postit.dao.PostDao;
import com.postit.entity.Post;
import com.postit.entity.User;

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
  public Long deletePostByPostId(String username, Long postId) {

    return postDao.deletePostByPostId(username, postId);
  }

  @Override
  public List<Post> listPosts() {

    List<Post> postList = postDao.listPosts();
    return postList;
  }

}
