package com.nano.Bush.filters;

import io.vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
  private static final Pattern loginPattern = Pattern.compile(".*" + "/login" + ".*");
  private static final Pattern companyPattern = Pattern.compile("/companias");
  private static final Pattern insertUserPattern = Pattern.compile("/usuarios/insertar");
  private static final String localhost = "localhost";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

    final HttpServletRequest req = (HttpServletRequest) request;
    final HttpServletResponse res = (HttpServletResponse) response;
    final String path = req.getRequestURI().substring(req.getContextPath().length());
    res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
    res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, req.getHeader(HttpHeaders.ORIGIN));
    res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "PUT,POST,GET,OPTIONS,PATCH");
    res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "accept, content-type");

    logger.info("Passing through security filter. Path " + path);
    // Si el es local lo dejo pasar

    final Boolean isLocalhost = Option.of(req.getHeader(HttpHeaders.ORIGIN))
            .map(origin -> isFromLocalhost(origin, req))
            .getOrElse(false);

    if (isLocalhost) {
      res.setStatus(HttpServletResponse.SC_OK);
      filterChain.doFilter(request, response);
    } else {
      if (HttpMethod.OPTIONS.equals(HttpMethod.valueOf(req.getMethod()))) {
        res.setStatus(HttpServletResponse.SC_OK);
        filterChain.doFilter(request, response);
      } else {
        if (!matchesLogin(path) && !isLookingForCompanies(req, path) && !matchesInsertUser(path)) {
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
    }

  }

  private boolean isFromLocalhost(String origin, HttpServletRequest req) {
    return origin.contains(localhost) || Option.of(req.getRequestURL().toString()).getOrElse("").contains(localhost);
  }

  private boolean isLookingForCompanies(HttpServletRequest req, String path) {
    return matchesCompany(path) && HttpMethod.GET.equals(HttpMethod.valueOf(req.getMethod()));

  }

  private static boolean matchesLogin(String path) {
    return loginPattern.matcher(path).matches();
  }

  private static boolean matchesCompany(String path) {
    return companyPattern.matcher(path).matches();
  }

  private static boolean matchesInsertUser(String path) {
    return insertUserPattern.matcher(path).matches();
  }

}
