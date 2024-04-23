package by.alst.grand;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/index.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login").trim();
        String password = req.getParameter("pwd").trim();

        resp.setContentType("text/html");
        var writer = resp.getWriter();
        writer.write("<html><body>");

        if (userService.isPresentUserByLoginPassword(login, password)) {
            UserDto userDto = userService.getUserByLogin(login).get();
            userService.writeUserToJson(userDto);
            resp.sendRedirect("/menu.html");
        } else {
            writer.write("<h2>Wrong login or password!!!<h2>");
            writer.println("Вернуться на страницу аутентификации");
            writer.println("<a href=\"authentication\">Back</a>");
        }
        writer.write("</body></html>");
        writer.close();
    }
}
