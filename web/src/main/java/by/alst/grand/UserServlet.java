package by.alst.grand;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserService();

    private UserDto userDto;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        var writer = resp.getWriter();
        userDto = userService.readUserFromJson();
        writer.write("<html><body>");
        writer.write("<h1> " + userDto + " </h1>");
        writer.println("<br/>");

        writer.println("<form action=\"user\" method=\"POST\">");
        writer.println("<h2><p>Изменить информацию о пользователе:</p></h2>");
        writer.println("<h3>Name: <input type=\"text\" name=\"name\"></h3>");
        writer.println("<h3>Age: <input type=\"text\" name=\"age\"></h3>");
        writer.println("<h3>Email: <input type=\"text\" name=\"email\"></h3>");
        writer.println("<h3>Login: <input type=\"text\" name=\"login\"></h3>");
        writer.println("<h3>Password: <input type=\"password\" name=\"pwd\"></h3>");
        writer.println(" <input type=\"submit\" value=\"ИЗМЕНИТЬ\"/>");
        writer.println("<br/>");
        writer.println("<br/>");

        writer.println("<h2>Вернуться в основное меню</h2>");
        writer.println("<h2><a href=\"menu.html\">Back</a></h2>");

        writer.write("</body></html>");
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int changeCounter = 0;
        int minAge = 0;
        int maxAge = 100;

        resp.setContentType("text/html");
        var writer = resp.getWriter();
        writer.write("<html><body>");


        userService.removeUser(userDto);

        String name = req.getParameter("name").trim();
        if (!name.isBlank()) {
            userDto.setName(name);
            changeCounter++;
        }

        String ageString = req.getParameter("age").trim();
        if (!ageString.isEmpty()) {
            int age = minAge;
            if (ageString.matches("[0-9]+")) {
                age = Integer.parseInt(ageString);
                age = age > minAge & age <= maxAge ? age : minAge;
            }
            if (age != minAge) {
                userDto.setAge(age);
                changeCounter++;
            } else {
                writer.write("<h2>Wrong Age in registration form!!!<h2>");
            }
        }


        String email = req.getParameter("email").trim();
        if (!email.isEmpty()) {
            if (EmailValidator.getInstance().isValid(email)) {
                userDto.setEmail(email);
                changeCounter++;
            } else {
                writer.write("<h2>Wrong Email in modification form!!!<h2>");
            }
        }

        String login = req.getParameter("login").trim();
        if (!login.isEmpty()) {
            if (userService.getUserByLogin(login).isEmpty()) {
                userDto.setLogin(login);
                changeCounter++;
            } else {
                writer.write("<h2>User with adding login exist!!!<h2>");
                writer.write("<h2>Login remains the same!!!<h2>");
            }
        }

        String password = req.getParameter("pwd").trim();
        if (!password.isEmpty()) {
            userDto.setPassword(password);
            changeCounter++;
        }

        userService.addUser(userDto);
        userService.writeUserToJson(userDto);


        if (changeCounter > 0) {
            writer.write("<h2>Changes applied!!!<h2>");
        } else {
            writer.write("<h2>No changes applied!!!<h2>");
        }
        writer.write("</body></html>");

        writer.println("Вернуться в профиль пользователя");
        writer.println("<a href=\"user\">Back</a>");
        writer.close();
    }
}
