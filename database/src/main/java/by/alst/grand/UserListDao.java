package by.alst.grand;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class UserListDao {

    private static UserList userList;

    private final static String path = "d:\\JSONUserList\\UserList.json";

    public static UserList getUserList() {
        return userList;
    }

    public static void setUserList(UserList userList) {
        UserListDao.userList = userList;
    }

    public static void writeToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(UserDao.getFile(path), userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserList readFromJson() throws IOException {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(UserDao.getFile(path)))) {
            byte[] jsonData = inputStream.readAllBytes();
            ObjectMapper objectMapper = new ObjectMapper();
            userList = jsonData.length == 0 ? new UserList() : objectMapper.readValue(jsonData, UserList.class);
        }
        return userList;
    }

    public static void addUser(User user) throws IOException {
        userList = readFromJson();
        List<User> list = userList.getUserList();
        list.add(user);
        userList.setUserList(list);
        writeToJson();
    }

    public static void removeUser(User user) throws IOException {
        userList = readFromJson();
        List<User> list = userList.getUserList();
        list.remove(user);
        userList.setUserList(list);
        writeToJson();
    }

    public static Optional<User> findByLogin(String login) throws IOException {
        userList = readFromJson();
        return userList.getUserList().stream().filter(s -> Objects.equals(s.getLogin(), login)).findFirst();
    }

    public static Optional<User> findByLoginPassword(String login, String password) throws IOException {
        userList = readFromJson();
        return userList.getUserList().stream()
                .filter(((Predicate<User>) s -> Objects.equals(s.getLogin(), login))
                        .and(s -> Objects.equals(s.getPassword(), password)))
                .findFirst();
    }

    public static void sortByLoginPrint() {
        userList.getUserList().stream()
                .sorted((u1, u2) -> (int) (u1.getLogin().compareTo(u2.getLogin()))).peek(System.out::println)
                .toList();
        System.out.println();
    }

    public static void printUserList() throws IOException {
        userList = readFromJson();
        userList.getUserList().forEach(System.out::println);
        System.out.println();
    }
}
