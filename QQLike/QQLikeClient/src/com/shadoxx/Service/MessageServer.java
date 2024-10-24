package com.shadoxx.Service;

import com.shadoxx.QQCommon.Message;
import com.shadoxx.QQCommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageServer {

    public void sendMessageToOneUser(String senderUid, String getterUid, String content) {
        Message message = new Message();
        message.setSender(senderUid);
        message.setGetter(getterUid);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        message.setMesType(MessageType.MESSAGE_COMM_MES);

        try {
            ObjectOutputStream sendMessageToOneUser = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThreadByUserId(senderUid).getSocket().getOutputStream());
            sendMessageToOneUser.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void senMessageToGroup(String senderUid, String content) {
        Message message = new Message();
        message.setSender(senderUid);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        message.setMesType(MessageType.MESSAGE_GROUP_MES);

        try {
            ObjectOutputStream sendMessageToOneUser = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThreadByUserId(senderUid).getSocket().getOutputStream());
            sendMessageToOneUser.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
