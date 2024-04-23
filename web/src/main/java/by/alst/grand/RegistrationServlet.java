package by.alst.grand;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/registration.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int minAge = 0;
        int maxAge = 100;

        String name = req.getParameter("name").trim();

        String ageString = req.getParameter("age").trim();
        int age = minAge;
        if (ageString.matches("[0-9]+")) {
            age = Integer.parseInt(ageString);
            age = age > minAge & age <= maxAge ? age : minAge;
        }

        String email = req.getParameter("email").trim();
        if (!EmailValidator.getInstance().isValid(email)) {
            email = "";
        }

        String login = req.getParameter("login").trim();

        String password = req.getParameter("pwd").trim();


        resp.setContentType("text/html");
        var writer = resp.getWriter();
        writer.write("<html><body>");
        if (login.isBlank() || password.isBlank()) {
            writer.write("<h2>No Login or Password in registration form!!!<h2>");
            writer.println("Вернуться на страницу регистрации");
            writer.println("<a href=\"registration\">Back</a>");
        } else if (name.isBlank()) {
            writer.write("<h2>No Name in registration form!!!<h2>");
            writer.println("Вернуться на страницу регистрации");
            writer.println("<a href=\"registration\">Back</a>");
        } else if (email.isBlank()) {
            writer.write("<h2>No or wrong Email in registration form!!!<h2>");
            writer.println("Вернуться на страницу регистрации");
            writer.println("<a href=\"registration\">Back</a>");
        } else if (age == minAge) {
            writer.write("<h2>No or wrong Age in registration form!!!<h2>");
            writer.println("Вернуться на страницу регистрации");
            writer.println("<a href=\"registration\">Back</a>");
        } else if (userService.isPresentUserByLogin(login)) {
            writer.write("<h2>User with this Login exist in base!!!<h2>");
            writer.println("Вернуться на страницу регистрации");
            writer.println("<a href=\"registration\">Back</a>");
        } else {
            UserDto userDto = new UserDto(name, age, email, login, password);
            userService.addUser(userDto);
            writer.write("<h2>User adding in base!!!<h2>");
            writer.println("Вернуться на страницу аутентификации");
            writer.println("<a href=\"authentication\">Back</a>");
        }

        writer.write("</body></html>");
        writer.close();
    }
}
