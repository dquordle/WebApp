package edu.school21.cinema.servlets;

import edu.school21.cinema.models.Authentication;
import edu.school21.cinema.models.User;
import edu.school21.cinema.services.AuthenticationService;
import edu.school21.cinema.services.ImageService;
import edu.school21.cinema.services.UsersService;
import org.springframework.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Timer;

@WebServlet(name = "SignInServlet", value = "/signIn")
public class SignInServlet extends HttpServlet {
    private UsersService usersService;
    private AuthenticationService authService;
    private ImageService imageService;

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
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/html/signIn.html");
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("pass");

        User user = usersService.signIn(email, password);
        if (user != null) {
            String ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("0:0:0:0:0:0:0:1")) {
                ipAddress = "127.0.0.1";
            }
            authService.save(new Authentication(null, user.getId(), new Date(new java.util.Date().getTime()),
                    new Time(System.currentTimeMillis()), ipAddress));
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/profile");
        } else {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/html/signIn.html");
            view.forward(request, response);
        }
    }
}
