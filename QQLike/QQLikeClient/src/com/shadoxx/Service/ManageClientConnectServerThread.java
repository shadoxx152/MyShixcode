package com.shadoxx.Service;

import java.math.BigInteger;
import java.util.HashMap;

public class ManageClientConnectServerThread {
    private static HashMap<BigInteger, ClientConnectServerThread> manageCollection = new HashMap<>();

    public static void addClientConnectServerThread(String uid, ClientConnectServerThread clientConnectServerThread) {
         manageCollection.put(new BigInteger(uid), clientConnectServerThread);
    }

    public static ClientConnectServerThread getClientConnectServerThreadByUserId(String uid) {
        return manageCollection.get(new BigInteger(uid));
    }

    public static void remove(String uid) {
        manageCollection.remove(new BigInteger(uid));
    }
}
