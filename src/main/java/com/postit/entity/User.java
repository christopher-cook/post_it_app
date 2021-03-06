package com.postit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.postit.entity.UserRole;
import com.postit.entity.UserProfile;

@Entity
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @Email(message = "Email invalid")
  @Column(name = "email", unique = true, nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String email;

  @NotBlank(message = "Username cannot be blank") // javax
  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_profile_id")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private UserProfile userProfile;

  @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
  @JoinColumn(name = "user_role_id", nullable = false)
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private UserRole userRole;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Post> postList;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER) //
  private List<Comment> commentList;

  public Long getUserId() {

    return userId;
  }

  public void setUserId(Long userId) {

    this.userId = userId;
  }

  public String getUsername() {

    return username;
  }

  public void setUsername(String username) {

    this.username = username;
  }

  public String getPassword() {

    return password;
  }

  public void setPassword(String password) {

    this.password = password;
  }

  public UserProfile getUserProfile() {

    return userProfile;
  }

  public void setUserProfile(UserProfile userProfile) {

    this.userProfile = userProfile;
  }

  public List<Post> getPostList() {

    return postList;
  }

  public void setPostList(List<Post> postList) {

    this.postList = postList;
  }

  public List<Comment> getCommentList() {

    return commentList;
  }

  public void setCommentList(List<Comment> commentList) {

    this.commentList = commentList;
  }

  public UserRole getUserRole() {

    return userRole;
  }

  public void setUserRole(UserRole userRole) {

    this.userRole = userRole;
  }

  public String getEmail() {

    return email;
  }

  public void setEmail(String email) {

    this.email = email;
  }
}
