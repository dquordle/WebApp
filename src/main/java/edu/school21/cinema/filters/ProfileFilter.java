package edu.school21.cinema.filters;

import edu.school21.cinema.services.ImageService;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "ProfileFilter", urlPatterns = "/profile")
public class ProfileFilter implements Filter {
    private ImageService imageService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (((HttpServletRequest) request).getSession().getAttribute("user") != null) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).setStatus(403);
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/html/403.html");
            view.forward(request, response);
        }
    }
}
