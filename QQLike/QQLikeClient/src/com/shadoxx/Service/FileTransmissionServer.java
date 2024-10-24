package com.shadoxx.Service;

import com.shadoxx.QQCommon.Message;
import com.shadoxx.QQCommon.MessageType;

import java.io.*;

public class FileTransmissionServer {

    public void senFileToOneUser(String src, String destination, String senderUID, String getterUID) {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_SEND);
        message.setSender(senderUID);
        message.setGetter(getterUID);
        message.setSrc(src);
        message.setDestination(destination);


        FileInputStream fileInputStream = null;

        try {
            BufferedInputStream readFileFromSrcPath = new BufferedInputStream(new FileInputStream(src));
            byte[] fileBytes = new byte[(int) new File(src).length()];

            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);

            message.setFileBytes(fileBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThreadByUserId(senderUID).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
