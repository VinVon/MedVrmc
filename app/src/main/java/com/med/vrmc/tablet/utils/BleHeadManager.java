package com.med.vrmc.tablet.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.EEGPower;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;

/**
 * Created by raytine on 2018/1/4.
 */

public class BleHeadManager {
    private boolean zhuce = false;
    private static final String TAG = "BleHeadManager";
    private static BleHeadManager manager;
    private Context mContext;
    private TgStreamReader tgStreamReader;
    private int currentState = 0;

    private boolean isPressing = false;
    private static final int MSG_UPDATE_BAD_PACKET = 1001;
    private static final int MSG_UPDATE_STATE = 1002;
    private static final int MSG_CONNECT = 1003;
    private boolean isReadFilter = false;
    int raw;
    private int badPacketCount = 0;

    private Handler toUIhandler = null;

    public void setToUIhandler(Handler toUIhandler) {
        this.toUIhandler = toUIhandler;
    }

    private BleHeadManager(Context mContext) {
        this.mContext = mContext;
    }

    public static BleHeadManager newInstance(Context mContext) {
        if (manager == null){
            manager = new BleHeadManager(mContext);
        }
        return manager;
    }

    public BluetoothAdapter getDefaultAdapter() {
        BluetoothAdapter mBluetoothAdapter = null;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter;
    }

    public void scanDevice(BluetoothAdapter mBluetoothAdapter) {
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
    }
    public void registerReceiver(){
        //register the receiver for scanning
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        Log.e("-----","广播注册");
        zhuce = true;
        mContext.registerReceiver(mReceiver, filter);
    }
    //The BroadcastReceiver that listens for discovered devices
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
//            Log.e(TAG, "mReceiver()");
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                Log.e(TAG, "mReceiver found device: " + device.getName());
                // update to UI
                Message msg  = Message.obtain();
                msg.what = 1;
                msg.obj = device;
                toUIhandler.sendMessage(msg);
            }
        }
    };
    public void unregisterReceiver() {
     if (mContext != null && mReceiver!= null&& zhuce){

         zhuce = false;
         mContext.unregisterReceiver(mReceiver);
     }
    }

    public void setBadPacketCount(int badPacketCount) {
        this.badPacketCount = badPacketCount;
    }

    /**
     * If the TgStreamReader is created, just change the bluetooth
     * else create TgStreamReader, set data receiver, TgStreamHandler and parser
     *
     * @param bd
     * @return TgStreamReader
     */
    public TgStreamReader createStreamReader(BluetoothDevice bd) {

        if (tgStreamReader == null) {
            // Example of constructor public TgStreamReader(BluetoothDevice mBluetoothDevice,TgStreamHandler tgStreamHandler)
            tgStreamReader = new TgStreamReader(bd, callback);
            tgStreamReader.startLog();
        } else {
            // (1) Demo of changeBluetoothDevice
            tgStreamReader.changeBluetoothDevice(bd);

            // (4) Demo of setTgStreamHandler, you can change the data handler by this function
            tgStreamReader.setTgStreamHandler(callback);
        }
        return tgStreamReader;
    }

    private TgStreamHandler callback = new TgStreamHandler() {
        @Override
        public void onStatesChanged(int connectionStates) {
            // TODO Auto-generated method stub
//            Log.d(TAG, "connectionStates change to: " + connectionStates);
            currentState = connectionStates;
            switch (connectionStates) {
                case ConnectionStates.STATE_CONNECTED:
                    //sensor.start();
                    Message ConnectedMsg  = Message.obtain();
                    ConnectedMsg.what = 4;
                    toUIhandler.sendMessage(ConnectedMsg);
                    break;
                case ConnectionStates.STATE_WORKING:
                    //byte[] cmd = new byte[1];
                    //cmd[0] = 's';
                    //tgStreamReader.sendCommandtoDevice(cmd);
                    LinkDetectedHandler.sendEmptyMessageDelayed(1234, 5000);
                    break;
                case ConnectionStates.STATE_GET_DATA_TIME_OUT:
                    //get data time out
                    break;
                case ConnectionStates.STATE_COMPLETE:
                    //read file complete
                    break;
                case ConnectionStates.STATE_STOPPED:
                    break;
                case ConnectionStates.STATE_DISCONNECTED:

                    break;
                case ConnectionStates.STATE_ERROR:
//                    Log.d(TAG, "Connect error, Please try again!");
                    break;
                case ConnectionStates.STATE_FAILED:
//                    Log.d(TAG, "Connect failed, Please try again!");
                    break;
            }
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = MSG_UPDATE_STATE;
            msg.arg1 = connectionStates;
            LinkDetectedHandler.sendMessage(msg);


        }

        @Override
        public void onRecordFail(int a) {
            // TODO Auto-generated method stub
//            Log.e(TAG, "onRecordFail: " + a);

        }

        @Override
        public void onChecksumFail(byte[] payload, int length, int checksum) {
            // TODO Auto-generated method stub
            badPacketCount++;
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = MSG_UPDATE_BAD_PACKET;
            msg.arg1 = badPacketCount;
            LinkDetectedHandler.sendMessage(msg);

        }

        @Override
        public void onDataReceived(int datatype, int data, Object obj) {
            // TODO Auto-generated method stub
//            Log.e(TAG,"onDataReceived"+datatype+"");
            Message msg = LinkDetectedHandler.obtainMessage();
            msg.what = datatype;
            msg.arg1 = data;
            msg.obj = obj;
            LinkDetectedHandler.sendMessage(msg);
            //Log.i(TAG,"onDataReceived");
        }

    };




    private Handler LinkDetectedHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1234:
                    tgStreamReader.MWM15_getFilterType();
                    isReadFilter = true;
//                    Log.d(TAG, "MWM15_getFilterType ");
                    break;
                case 1235:
                    tgStreamReader.MWM15_setFilterType(MindDataType.FilterType.FILTER_60HZ);
//                    Log.d(TAG, "MWM15_setFilter  60HZ");
                    LinkDetectedHandler.sendEmptyMessageDelayed(1237, 1000);
                    break;
                case 1236:
                    tgStreamReader.MWM15_setFilterType(MindDataType.FilterType.FILTER_50HZ);
//                    Log.d(TAG, "MWM15_SetFilter 50HZ ");
                    LinkDetectedHandler.sendEmptyMessageDelayed(1237, 1000);
                    break;

                case 1237:
                    tgStreamReader.MWM15_getFilterType();
//                    Log.d(TAG, "MWM15_getFilterType ");
                    break;
                case MindDataType.CODE_FILTER_TYPE:
//                    Log.d(TAG, "CODE_FILTER_TYPE: " + msg.arg1 + "  isReadFilter: " + isReadFilter);
                    if (isReadFilter) {
                        isReadFilter = false;
                        if (msg.arg1 == MindDataType.FilterType.FILTER_50HZ.getValue()) {
                            LinkDetectedHandler.sendEmptyMessageDelayed(1235, 1000);
                        } else if (msg.arg1 == MindDataType.FilterType.FILTER_60HZ.getValue()) {
                            LinkDetectedHandler.sendEmptyMessageDelayed(1236, 1000);
                        } else {
                            Log.e(TAG, "Error filter type");
                        }
                    }

                    break;


                case MindDataType.CODE_RAW:
//                    updateWaveView(msg.arg1);
                    break;
                case MindDataType.CODE_MEDITATION:
//                    Log.e(TAG, "HeadDataType.CODE_MEDITATION " + msg.arg1);
                    Message meditationMsg  = Message.obtain();
                    meditationMsg.what = 2;
                    meditationMsg.arg1 = msg.arg1;
                    toUIhandler.sendMessage(meditationMsg);
//                    tv_meditation.setText("" +msg.arg1 );
                    break;
                case MindDataType.CODE_ATTENTION:
//                    Log.e(TAG, "CODE_ATTENTION " + msg.arg1);
                    Message attentionMsg  = Message.obtain();
                    attentionMsg.what = 3;
                    attentionMsg.arg1 = msg.arg1;
                    toUIhandler.sendMessage(attentionMsg);
//                    tv_attention.setText("" +msg.arg1 );
                    break;
                case MindDataType.CODE_EEGPOWER:
                    EEGPower power = (EEGPower) msg.obj;
                    if (power.isValidate()) {
//                        tv_delta.setText("" +power.delta);
//                        tv_theta.setText("" +power.theta);
//                        tv_lowalpha.setText("" +power.lowAlpha);
//                        tv_highalpha.setText("" +power.highAlpha);
//                        tv_lowbeta.setText("" +power.lowBeta);
//                        tv_highbeta.setText("" +power.highBeta);
//                        tv_lowgamma.setText("" +power.lowGamma);
//                        tv_middlegamma.setText("" +power.middleGamma);
                    }
                    break;
                case MindDataType.CODE_POOR_SIGNAL://
                    int poorSignal = msg.arg1;
//                    Log.d(TAG, "poorSignal:" + poorSignal);
//                    tv_ps.setText(""+msg.arg1);

                    break;
                case MSG_UPDATE_BAD_PACKET:
//                    tv_badpacket.setText("" + msg.arg1);

                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    public void stopRecord(){
        if(tgStreamReader != null){
            tgStreamReader.stop();
        }
    }
}
