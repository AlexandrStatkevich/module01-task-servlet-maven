package by.alst.grand;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class UserDao {

    private static User user;

    private final static String path = "d:\\JSONUser\\User.json";

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserDao.user = user;
    }

    public static File getFile(String path) throws IOException {
        File dir = new File(path).getParentFile();
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static void writeToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(getFile(path), user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User readFromJson() throws IOException {
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(getFile(path)))) {
            byte[] jsonData = inputStream.readAllBytes();
            ObjectMapper objectMapper = new ObjectMapper();
            user = jsonData.length == 0 ? new User() : objectMapper.readValue(jsonData, User.class);
        }
        return user;
    }
}
