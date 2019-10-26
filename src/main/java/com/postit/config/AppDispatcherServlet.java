
package com.postit.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppDispatcherServlet extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {

    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {

    return new Class[] {AppConfig.class};
  }

  @Override
  protected String[] getServletMappings() {

    return new String[] {"/"};
  }
}
