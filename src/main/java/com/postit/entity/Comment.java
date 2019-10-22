package com.postit.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "comments")
public class Comment {
  @Id
  @Column(name = "comment_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId;
  
  @Column(name = "text")
  private String text;
  
  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinColumn(name = "comment_user_id", nullable=false)
  private User user;
  
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinColumn(name = "comment_post_id", nullable=false)
  private Post post;

  
  public Long getCommentId() {
  
    return commentId;
  }
  
  public User getUser() {
  
    return user;
  }

  public void setUser(User user) {
  
    this.user = user;
  }


  
  public Post getPost() {
  
    return post;
  }


  
  public void setPost(Post post) {
  
    this.post = post;
  }


  public void setCommentId(Long commentId) {
  
    this.commentId = commentId;
  }

  
  public String getText() {
  
    return text;
  }

  
  public void setText(String text) {
  
    this.text = text;
  }
  
}