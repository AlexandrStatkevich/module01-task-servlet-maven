package by.alst.grand;

import java.io.IOException;
import java.util.Optional;

public class UserService {

    public Optional<UserDto> getUserByLogin(String login) throws IOException {
        return UserListDao.findByLogin(login).map(u -> new UserDto(u.getName(),
                u.getAge(),
                u.getEmail(),
                u.getLogin(),
                u.getPassword()));
    }

    public boolean isPresentUserByLogin(String login) throws IOException {
        return UserListDao.findByLogin(login).isPresent();
    }

    public boolean isPresentUserByLoginPassword(String login, String password) throws IOException {
        return UserListDao.findByLoginPassword(login, password).isPresent();
    }

    public void addUser(UserDto userDto) throws IOException {
        UserListDao.addUser(getUserFromUserDto(userDto));
    }

    public void removeUser(UserDto userDto) throws IOException {
        UserListDao.removeUser(getUserFromUserDto(userDto));
    }

    public void writeUserToJson(UserDto userDto) {
        UserDao.setUser(getUserFromUserDto(userDto));
        UserDao.writeToJson();
    }

    public UserDto readUserFromJson() throws IOException {
        var user = UserDao.readFromJson();
        return new UserDto(user.getName(),
                user.getAge(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword());
    }

    private User getUserFromUserDto(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
