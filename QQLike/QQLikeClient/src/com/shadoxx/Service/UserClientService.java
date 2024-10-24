package com.shadoxx.Service;

import com.shadoxx.QQCommon.Message;
import com.shadoxx.QQCommon.MessageType;
import com.shadoxx.QQCommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {
    private User user = new User();
    private Socket socket;

    public boolean checkUser(String uid, String password) {
        boolean returnValue;
        user.setUserId(new BigInteger(uid));
        user.setPassword(password);

        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 152);

            ObjectOutputStream userVerify= new ObjectOutputStream(socket.getOutputStream());
            userVerify.writeObject(user);

            ObjectInputStream loginMessageStream = new ObjectInputStream(socket.getInputStream());
            Message loginMessage = (Message)loginMessageStream.readObject();

            if (loginMessage.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                clientConnectServerThread.start();

                ManageClientConnectServerThread.addClientConnectServerThread(uid, clientConnectServerThread);

                returnValue = true;
            } else {
                socket.close();
                returnValue = false;
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return returnValue;
    }

    public void onlineUserList(String uid) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_USER);
        message.setSender(uid);

        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 152);
            ObjectOutputStream sendRequestToGetOnlineUserList = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThreadByUserId(uid).getSocket().getOutputStream());

            sendRequestToGetOnlineUserList.writeObject(message);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void logout(String uid) {
        Message logoutRequest = new Message();
        logoutRequest.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        logoutRequest.setSender(uid);

        try {
            ObjectOutputStream sendLogoutRequest = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThreadByUserId(uid).getSocket().getOutputStream());
            sendLogoutRequest.writeObject(logoutRequest);
            System.exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
