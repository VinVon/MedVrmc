package com.med.vrmc.tablet.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

/**
 * Created by vonchenchen on 2016/3/12 0012.
 */
public class MyBluetoothGattCallback extends BluetoothGattCallback {

    private String TAG = "MyBluetoothGattCallback";
    private BluetoothInfoChangeListener mBluetoothInfoChangeListener = null;

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

        Log.e(TAG, "ConnectionStateChange");
        for (int i = 0; i < +gatt.getServices().size(); i++) {
            Log.i(TAG, "ConnectionStateChange" +gatt.getServices().get(0).getUuid());
        }
        if (newState == BluetoothProfile.STATE_CONNECTED) {

            if(mBluetoothInfoChangeListener != null){
                mBluetoothInfoChangeListener.onConnectionStateChange(BLEControlService.ACTION_GATT_CONNECTED);
            }
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {

            if(mBluetoothInfoChangeListener != null){
                mBluetoothInfoChangeListener.onConnectionStateChange(BLEControlService.ACTION_GATT_DISCONNECTED);
            }
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {

        Log.e(TAG, "ServicesDiscovered");
        if (status == BluetoothGatt.GATT_SUCCESS) {
            if(mBluetoothInfoChangeListener != null){
                mBluetoothInfoChangeListener.onServicesDiscovered(BLEControlService.ACTION_GATT_SERVICES_DISCOVERED);
            }
        }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.e(TAG, "onCharacteristicRead");
        if (status == BluetoothGatt.GATT_SUCCESS) {
            if(mBluetoothInfoChangeListener != null){
                mBluetoothInfoChangeListener.onCharacteristicRead(BLEControlService.ACTION_DATA_AVAILABLE, characteristic);
            }
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.e(TAG, "onCharacteristicWrite");
        if (status == BluetoothGatt.GATT_SUCCESS) {
            if(mBluetoothInfoChangeListener != null){
                mBluetoothInfoChangeListener.onCharacteristicWrite(BLEControlService.ACTION_DATA_AVAILABLE, characteristic);
//                String s = bytesToHexString(characteristic.getValue());
//                Log.e(TAG, "onCharacteristicWrite=="+s);
            }
        }
    }
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

        Log.e(TAG, "onCharacteristicChanged");
        if(mBluetoothInfoChangeListener != null){
            mBluetoothInfoChangeListener.onCharacteristicChanged(BLEControlService.ACTION_DATA_AVAILABLE, characteristic);
        }
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorRead(gatt, descriptor, status);

        if(mBluetoothInfoChangeListener != null){
            mBluetoothInfoChangeListener.onDescriptorRead(BLEControlService.ACTION_DATA_AVAILABLE, descriptor);
        }
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        if(mBluetoothInfoChangeListener != null){
            mBluetoothInfoChangeListener.onDescriptorWrite(BLEControlService.ACTION_DATA_AVAILABLE, descriptor);
        }
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        super.onReadRemoteRssi(gatt, rssi, status);
        if(mBluetoothInfoChangeListener != null){
            mBluetoothInfoChangeListener.onReadRemoteRssi(BLEControlService.ACTION_DATA_AVAILABLE, rssi);
        }
    }

    public void setBluetoothInfoChangeListener(BluetoothInfoChangeListener bluetoothInfoChangeListener){
        this.mBluetoothInfoChangeListener = bluetoothInfoChangeListener;
    }

    public interface BluetoothInfoChangeListener{
        void onConnectionStateChange(String action);
        void onServicesDiscovered(String action);
        void onCharacteristicRead(String action, BluetoothGattCharacteristic characteristic);
        void onCharacteristicChanged(String action, BluetoothGattCharacteristic characteristic);
        void onDescriptorRead(String action, BluetoothGattDescriptor descriptor);
        void onDescriptorWrite(String action, BluetoothGattDescriptor descriptor);
        void onReadRemoteRssi(String action, int rssi);

        /**
         * 学数据
         * @param action
         * @param characteristic
         */
        void onCharacteristicWrite(String action, BluetoothGattCharacteristic characteristic);
    }
}
