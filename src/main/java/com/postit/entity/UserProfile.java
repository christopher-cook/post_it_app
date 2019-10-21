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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "profiles")
public class UserProfile {

  @Id
  @Column(name = "profile_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long profileId;

  @Column(name = "email")
  private String additionalEmail;

  @Column(name = "mobile")
  private String mobile;

  @Column(name = "address")
  private String address;

  public Long getProfileId() {

    return profileId;
  }

  public void setProfileId(Long profileId) {

    this.profileId = profileId;
  }

  public String getAdditionalEmail() {

    return additionalEmail;
  }

  public void setAdditionalEmail(String additionalEmail) {

    this.additionalEmail = additionalEmail;
  }

  public String getMobile() {

    return mobile;
  }

  public void setMobile(String mobile) {

    this.mobile = mobile;
  }

  public String getAddress() {

    return address;
  }

  public void setAddress(String address) {

    this.address = address;
  }

}
