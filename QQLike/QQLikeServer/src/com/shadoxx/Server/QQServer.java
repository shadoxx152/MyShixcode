package com.shadoxx.Server;

import com.shadoxx.QQCommon.Message;
import com.shadoxx.QQCommon.MessageType;
import com.shadoxx.QQCommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

public class QQServer {
    ServerSocket serverSocket = null;
    UserVerifyServer userVerifyServer = new UserVerifyServer();

    public QQServer() {
        try {
            serverSocket = new ServerSocket(152);

            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream receiveUserAndMessage = new ObjectInputStream(socket.getInputStream());
                User user = (User) receiveUserAndMessage.readObject();

                String uid = String.valueOf(user.getUid());
                String password = user.getPassword();
                User u = userVerifyServer.verify(uid, password);

                ObjectOutputStream sendMessageToClint = new ObjectOutputStream(socket.getOutputStream());

                Message message = new Message();
                if (u != null) {
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    sendMessageToClint.writeObject(message);

                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, new BigInteger(uid));
                    serverConnectClientThread.start();
                    ServerThreadCollection.addServerThread(u.getUid(), serverConnectClientThread);
                } else {
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    sendMessageToClint.writeObject(message);
                    socket.close();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
