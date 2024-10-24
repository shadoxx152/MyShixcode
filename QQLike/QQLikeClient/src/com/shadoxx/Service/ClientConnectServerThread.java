package com.shadoxx.Service;

import com.shadoxx.QQCommon.Message;
import com.shadoxx.QQCommon.MessageType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.spec.RSAOtherPrimeInfo;

public class ClientConnectServerThread extends Thread{
    private Socket socket;
    private boolean isAlive;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
        this.isAlive = true;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public void run() {
        while (isAlive) {
            try {
                ObjectInputStream readMessage = new ObjectInputStream(socket.getInputStream());
                Message message = (Message)readMessage.readObject();

                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_USER)) {
                    String[] onlineUser = message.getContent().split(" ");
                    for (String s : onlineUser) {
                        System.out.println("User:" + s);
                    }
                }

                if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    System.out.println(message.getSender() + " Say " + message.getContent());
                }
                if (message.getMesType().equals(MessageType.MESSAGE_GROUP_MES)) {
                    System.out.println(message.getSender() + " Say " + message.getContent());
                }

                if (message.getMesType().equals(MessageType.MESSAGE_FILE_SEND)) {
                    System.out.println(message.getSender() + " send a file to you ");
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDestination());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    System.out.println("Receive the file");
                }

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
