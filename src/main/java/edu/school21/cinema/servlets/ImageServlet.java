package edu.school21.cinema.servlets;

import edu.school21.cinema.models.Image;
import edu.school21.cinema.models.User;
import edu.school21.cinema.services.AuthenticationService;
import edu.school21.cinema.services.ImageService;
import edu.school21.cinema.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@Component
@PropertySource("/application.properties")
@WebServlet(name = "ImageServlet", value = "/images/*")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class ImageServlet extends HttpServlet{
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
        this.imageService.mkdir();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] uri = req.getRequestURI().split("/");
        if (uri.length != 4) {
            resp.setStatus(403);
            RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/html/403.html");
            view.forward(req, resp);
        }
        String imageId = uri[uri.length - 1];
        Optional<Image> imageOptional = imageService.findById(Long.valueOf(imageId));
        if (!imageOptional.isPresent()) {
            throw new RuntimeException("No such url");
        }
        Image image = imageOptional.get();
        FileInputStream fis = imageService.getImageFile(imageId);
        resp.addHeader("Content-Disposition", String.format("filename=\"%s\"", image.getName()));
        resp.setContentLength(image.getSize().intValue());
        resp.setContentType(image.getMime());
        BufferedInputStream bin = new BufferedInputStream(fis);
        BufferedOutputStream bout = new BufferedOutputStream(resp.getOutputStream());
        int ch = 0;
        while((ch=bin.read())!=-1)
        {
            bout.write(ch);
        }
        bin.close();
        fis.close();
        bout.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        imageService.save(req.getPart("img"), ((User) req.getSession().getAttribute("user")).getId());
        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}
