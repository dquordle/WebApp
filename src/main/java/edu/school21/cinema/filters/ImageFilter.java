package edu.school21.cinema.filters;

import edu.school21.cinema.models.Image;
import edu.school21.cinema.models.User;
import edu.school21.cinema.services.ImageService;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter(filterName = "ImageFilter", urlPatterns = "/images/*")
public class ImageFilter implements Filter {
    private ImageService imageService;
    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
        ApplicationContext springContext = (ApplicationContext) servletContext.getAttribute("springContext");
        this.imageService = springContext.getBean(ImageService.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        User user = (User) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        String[] path = ((HttpServletRequest) servletRequest).getRequestURI().split("/");
        if (user == null) {
            ((HttpServletResponse) servletResponse).setStatus(403);
            RequestDispatcher view = servletContext.getRequestDispatcher("/WEB-INF/html/403.html");
            view.forward(servletRequest, servletResponse);
        } else if (path[path.length - 1].equals("images")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String imageId = path[path.length - 1];
            Optional<Image> imageOptional;
            try {
                imageOptional = imageService.findById(Long.parseLong(imageId));
            } catch (NumberFormatException e) {
                throw new ServletException("No such url");
            }
            if (imageOptional.isPresent() && imageOptional.get().getUserId().equals(user.getId())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ((HttpServletResponse) servletResponse).setStatus(403);
                RequestDispatcher view = servletContext.getRequestDispatcher("/WEB-INF/html/403.html");
                view.forward(servletRequest, servletResponse);
            }
        }
    }
}
