package com.med.vrmc.tablet.bluetooth.factory;

import com.med.vrmc.tablet.utils.ToHexByteUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raytine on 2017/12/28.
 */

public class BTFactory {
    /**
     * system_conn 连接
     * 015A810100
     *
     * @param paramICommandCallback
     * @return
     */
    public static CommandHolder createConnectCMD(ICommandCallback paramICommandCallback) {
        return CommandHolder.create((byte) 1, (byte) 90, (byte) 1, ToHexByteUtils.hexStringToByte("01 5A810855AA112264189099"), paramICommandCallback);
    }

    /**
     * 测试指令
     * 015A810100
     *
     * @param paramICommandCallback
     * @return
     */
    public static CommandHolder createTestCMD(String names, ICommandCallback paramICommandCallback) {
        String s = ToHexByteUtils.convertStringToUTF8(names);
        List<byte[]> lists = new ArrayList<>();
        int lengh = s.length() / 2 + s.length() % 2;
        String format = String.format("%02x", lengh);
        String last = null;
        for (int i = 0; i < 15 - lengh; i++) {
            last+="00";
        }
        lists.add(ToHexByteUtils.hexStringToByte("03000110" + format + s+last));
        lists.add(ToHexByteUtils.hexStringToByte("0300021001010F01010101010101010101010101"));
        lists.add(ToHexByteUtils.hexStringToByte("0300830400000100"));
        return CommandHolder.create((byte) 1, (byte) 90, (byte) 1, lists, paramICommandCallback);
    }

}

