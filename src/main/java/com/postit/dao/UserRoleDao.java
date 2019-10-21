package com.postit.dao;

import com.postit.entity.UserRole;

public interface UserRoleDao {
  public UserRole getRole(String userRoleName);
}
