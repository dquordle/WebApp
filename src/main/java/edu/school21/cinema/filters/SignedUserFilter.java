package edu.school21.cinema.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "SignedUserFilter", urlPatterns = {"/signIn", "/signUp"})
public class SignedUserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (httpServletRequest.getSession().getAttribute("user") == null) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/profile");
        }
    }
}
