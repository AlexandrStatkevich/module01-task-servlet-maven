package by.alst.grand;

import java.io.IOException;

public class Main {

    static {
        try {
            boolean isEmptyJson = UserListDao.readFromJson().getUserList().isEmpty();
            UserList listUser = !isEmptyJson ? UserListDao.readFromJson()
                    : new UserList();
            if (isEmptyJson) {
                UserListDao.setUserList(listUser);
                UserListDao.writeToJson();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        UserListDao.sortByLoginPrint();
    }
}