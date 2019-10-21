package com.postit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "posts")
public class Post {
  @Id
  @Column(name = "post_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;
  
  @Column(name = "title")
  private String title;
  
  @Column(name = "description")
  private String description;
  
  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinColumn(name = "post_user_id")
  private User user;
  
  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Comment> commentList;

  
  public Long getPostId() {
  
    return postId;
  }

  
  public void setPostId(Long postId) {
  
    this.postId = postId;
  }

  
  public String getTitle() {
  
    return title;
  }

  
  public void setTitle(String title) {
  
    this.title = title;
  }

  
  public String getDescription() {
  
    return description;
  }

  
  public void setDescription(String description) {
  
    this.description = description;
  }

  
  public User getUser() {
  
    return user;
  }

  
  public void setUser(User user) {
  
    this.user = user;
  }

  
  public List<Comment> getCommentList() {
  
    return commentList;
  }

  
  public void setCommentList(List<Comment> commentList) {
  
    this.commentList = commentList;
  }
  
  

}
