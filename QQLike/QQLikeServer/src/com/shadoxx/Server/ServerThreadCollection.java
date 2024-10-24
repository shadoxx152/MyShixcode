package com.shadoxx.Server;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerThreadCollection {
    private static Map<BigInteger, ServerConnectClientThread> serverThreadCollectionMap = new HashMap<>();

    public static void addServerThread(BigInteger uid, ServerConnectClientThread serverConnectClientThread) {
        serverThreadCollectionMap.put(uid, serverConnectClientThread);
    }

    public static ServerConnectClientThread getServerConnectionClientThreadByUid(String uid) {
        return serverThreadCollectionMap.get(new BigInteger(uid));
    }

    public static String getOnlineUser() {
        Iterator<BigInteger> uidIterator = serverThreadCollectionMap.keySet().iterator();
        StringBuilder onlineUserList = new StringBuilder();
        while (uidIterator.hasNext()) {
            onlineUserList.append(uidIterator.next()).append(" ");
        }

        return onlineUserList.toString();
    }

    public static void remove(String uid) {
        serverThreadCollectionMap.remove(new BigInteger(uid));
    }

    public static Map<BigInteger, ServerConnectClientThread> getServerThreadCollectionMap() {
        return serverThreadCollectionMap;
    }
}
