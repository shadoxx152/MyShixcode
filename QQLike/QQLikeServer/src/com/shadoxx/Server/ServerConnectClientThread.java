package com.shadoxx.Server;

import com.shadoxx.QQCommon.Message;
import com.shadoxx.QQCommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

public class ServerConnectClientThread extends Thread{
    private Socket socket;
    private BigInteger uid;
    private boolean isAlive;

    public ServerConnectClientThread(Socket socket, BigInteger uid) {
        this.socket = socket;
        this.uid = uid;
        isAlive = true;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        while (isAlive) {
            try {
                ObjectInputStream getMessage = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) getMessage.readObject();

                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_USER)) {
                    System.out.println(message.getSender() + "Message");
                    String onlineUser = ServerThreadCollection.getOnlineUser();

                    Message sendOnlineUser = new Message();
                    sendOnlineUser.setMesType(MessageType.MESSAGE_RET_ONLINE_USER);
                    sendOnlineUser.setContent(onlineUser);
                    sendOnlineUser.setGetter(message.getSender());

                    ObjectOutputStream sendTheUserList = new ObjectOutputStream(socket.getOutputStream());
                    sendTheUserList.writeObject(sendOnlineUser);
                }
                if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    ServerThreadCollection.remove(message.getSender());
                    socket.close();
                    break;
                }
                if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    ServerConnectClientThread socketThreadToGetter = ServerThreadCollection.getServerConnectionClientThreadByUid(message.getGetter());
                    ObjectOutputStream socketToGetter = new ObjectOutputStream(socketThreadToGetter.getSocket().getOutputStream());
                    socketToGetter.writeObject(message);
                }
                if (message.getMesType().equals(MessageType.MESSAGE_GROUP_MES)) {
                    Map<BigInteger, ServerConnectClientThread> serverThreadCollectionMap = ServerThreadCollection.getServerThreadCollectionMap();

                    Iterator<BigInteger> socketThreadOfUsersIterator = serverThreadCollectionMap.keySet().iterator();

                    while (socketThreadOfUsersIterator.hasNext()) {
                        String userUid = socketThreadOfUsersIterator.next().toString();

                        if (!userUid.equals(message.getSender())) {
                            ObjectOutputStream groupChat = new ObjectOutputStream(ServerThreadCollection.getServerConnectionClientThreadByUid(userUid).getSocket().getOutputStream());
                            groupChat.writeObject(message);
                        }
                    }
                }

                if (message.getMesType().equals(MessageType.MESSAGE_FILE_SEND)) {
                    ServerConnectClientThread socketThreadToGetter = ServerThreadCollection.getServerConnectionClientThreadByUid(message.getGetter());

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketThreadToGetter.getSocket().getOutputStream());
                    objectOutputStream.writeObject(message);
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
