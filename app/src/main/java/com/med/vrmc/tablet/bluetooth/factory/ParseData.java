package com.med.vrmc.tablet.bluetooth.factory;

import com.med.vrmc.tablet.utils.ToHexByteUtils;

/**
 * 解析蓝牙返回的byte[]
 * Created by raytine on 2018/1/24.
 */

public class ParseData {
    public static final int HANDLER = 1;
    public static final int NOTHING = 2;
    public static final int NEXT = 3;

    public static ResultData parse(byte[] bytes) {
        String result = ToHexByteUtils.bytesToHexString(bytes);
        if (result.startsWith("015A8101")) {
            ResultData dsc = parseByte(bytes[4]);
            dsc.setZl(result);
            dsc.setType(HANDLER);
            return dsc;
        }else if (result.equals("0100")){
            ResultData s = new ResultData();
            s.setSu(true);
            s.setDec("蓝牙配对成功");
            s.setZl(result);
            s.setType(NEXT);
            s.setCommandHolder(BTFactory.createConnectCMD(null));
            return s;
        }else if(result.startsWith("03000110")){

        }
        return null;
    }

    private static ResultData parseByte(byte aByte) {
        ResultData result = new ResultData();
        String s = null;
        boolean is = false;
        switch (aByte) {
            case (byte) 0:
                s = "成功";
                is = true;
                break;
            case (byte) 1:
                s = "指令没有按照要求格式";
                is = false;
                break;
            case (byte) 2:
                s = "设置的参数不合乎规定,指令后内容错误";
                is = false;
                break;
        }
        result.setDec(s);
        result.setSu(is);
        return result;
    }
}
