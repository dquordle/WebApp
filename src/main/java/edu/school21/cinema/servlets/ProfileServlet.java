package edu.school21.cinema.servlets;

import edu.school21.cinema.models.Image;
import edu.school21.cinema.models.User;
import edu.school21.cinema.services.AuthenticationService;
import edu.school21.cinema.services.ImageService;
import edu.school21.cinema.services.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "ProfileServlet", value = "/profile")
public class ProfileServlet extends HttpServlet {
    private UsersService usersService;
    private AuthenticationService authService;
    private ImageService imageService;
    @Value("${storage.path}")
    private String storagePath;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        ApplicationContext springContext = (ApplicationContext) context.getAttribute("springContext");
        this.usersService = springContext.getBean(UsersService.class);
        this.authService = springContext.getBean(AuthenticationService.class);
        this.imageService = springContext.getBean(ImageService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) request.getSession().getAttribute("user");
        session.setAttribute("auth", authService.getAuthsByUserId(user.getId()));
        session.setAttribute("img", imageService.getImagesByUserId(user.getId()));
        Optional<Image> ava = imageService.findLastImageByUserId(user.getId());
        String avaPath = ava.map(image -> "/images/" + image.getId().toString()).orElse("/default.jpeg");
        session.setAttribute("avaPath", request.getContextPath() + avaPath);
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/jsp/profile.jsp");
        view.forward(request, response);
    }
}