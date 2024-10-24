package com.shadoxx.View;

import com.shadoxx.Service.FileTransmissionServer;
import com.shadoxx.Service.MessageServer;
import com.shadoxx.Service.UserClientService;
import com.shadoxx.Utils.Utility;

import java.math.BigInteger;

public class View {
    private UserClientService userClientService = new UserClientService();
    private String uid;
    private MessageServer messageServer = new MessageServer();
    private FileTransmissionServer fileTransmissionServer = new FileTransmissionServer();

    public void loginView() {
        boolean loginViewIsDisplay = true;
        while (loginViewIsDisplay) {
            System.out.println("--------Welcome to QQLIke--------");
            System.out.println("\t\t 1 Login");
            System.out.println("\t\t 9 Exit");

            String key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.println("UID");
                    uid = Utility.readString(50);
                    System.out.println("password:");
                    String password = Utility.readString(50);

                    if (userClientService.checkUser(uid , password)) {
                        System.out.println("Login Successfully\n");
                        mainView();
                        break;
                    }
                    System.out.println("Login Fail\n");
                    break;
                case "9":
                    System.out.println("Exit\n");
                    loginViewIsDisplay = false;
                    break;
                default:
                    System.out.println("Input Error\n");
            }
        }
    }

    public void mainView() {
        boolean mainViewIsDisplay = true;
        while (mainViewIsDisplay) {
            System.out.println("\n--------Menu(" + uid +")--------");
            System.out.println("\t\t 1 Show list of online user");
            System.out.println("\t\t 2 Group message");
            System.out.println("\t\t 3 Private chat");
            System.out.println("\t\t 4 Sent files");
            System.out.println("\t\t 9 Exit");

            String key = Utility.readString(1);
            switch (key) {
                case "1":
                    userClientService.onlineUserList(uid);
                    break;
                case "2":
                    System.out.println("Group Chat Connect:");
                    String groupChatContent = Utility.readString(50);
                    messageServer.senMessageToGroup(uid, groupChatContent);
                    break;
                case "3":
                    System.out.println("Input the user you want to chat:");
                    String userWantToChat = Utility.readString(50);
                    System.out.println("Content");
                    String content = Utility.readString(50);
                    messageServer.sendMessageToOneUser(uid, userWantToChat, content);
                    break;
                case "4":
                    System.out.println("Please input the uid you want to send:");
                    String UserUID = Utility.readString(50);
                    System.out.println("Please input the src of file:");
                    String src = Utility.readString(50);
                    System.out.println("Please input the destination:");
                    String destination = Utility.readString(50);
                    fileTransmissionServer.senFileToOneUser(src, destination, uid, UserUID);
                    break;
                case "9":
                    mainViewIsDisplay = false;
                    System.out.println( uid + " Exit\n");
                    userClientService.logout(uid);
                    loginView();
                    break;
                default:
                    System.out.println("Input Error\n");
            }
        }
    }
}
