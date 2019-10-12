package com.nano.Bush.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class SecurityFilter implements Filter {

  private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
  private static final Pattern loginPattern = Pattern.compile(".*" + "/usuarios/validar" + ".*");
  private static final String JSESSIONID = "JSESSIONID";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

    final HttpServletRequest req = (HttpServletRequest) request;
    final HttpServletResponse res = (HttpServletResponse) response;
    final String path = req.getRequestURI().substring(req.getContextPath().length());

    logger.info("Passing through security filter. Path " + path);

    if (!matchesLogin(path)) {
      //passing the Boolean parameter “false” to the getSession() returns the existing session and returns null if no session exists.
      // Passing the parameter “true” will create a new session if no session exists.
      HttpSession session = req.getSession(false);

      if (session == null)
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access request");

      else
        filterChain.doFilter(request, response);
    } else
      filterChain.doFilter(request, response);
  }

  private static boolean matchesLogin(String path) {
    return loginPattern.matcher(path).matches();
  }

}
