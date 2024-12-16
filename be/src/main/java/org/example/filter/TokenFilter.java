package org.example.filter;

import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.example.utils.TokenUtil;
import org.springframework.stereotype.Component;

@Component
public class TokenFilter implements Filter {

  private static final List<String> EXCLUDE_URLS = Arrays.asList("/api/user/login");

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // 初始化操作
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String path = httpRequest.getRequestURI();

    // 如果请求路径在排除列表中，直接放行
    if (EXCLUDE_URLS.contains(path)) {
      chain.doFilter(request, response);
      return;
    }

    String token = httpRequest.getHeader("Authorization");

    // 验证token
    if (isValidToken(token)) {
      // 如果token有效，继续处理请求
      chain.doFilter(request, response);
    } else {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setStatus(401);
      response.getWriter().write("Invalid Token");
    }
  }

  @Override
  public void destroy() {
    // 清理操作
  }

  // 验证token的方法，可以根据具体需求实现
  private boolean isValidToken(String token) {
    // 示例验证逻辑，实际使用中应替换为实际的验证逻辑
    return TokenUtil.validateToken(token);
  }
}
