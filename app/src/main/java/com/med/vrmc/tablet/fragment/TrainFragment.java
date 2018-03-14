package com.med.vrmc.tablet.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.cs.common.utils.ToastUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.med.vrmc.tablet.R;
import com.med.vrmc.tablet.adapter.BTDeviceListAdapter;
import com.med.vrmc.tablet.bluetooth.BLEHelper;
import com.med.vrmc.tablet.bluetooth.DevicePopulWindow;
import com.med.vrmc.tablet.bluetooth.factory.BTFactory;
import com.med.vrmc.tablet.utils.BleHeadManager;
import com.med.vrmc.tablet.utils.LineChartManager;
import com.med.vrmc.tablet.utils.Logger;
import com.med.vrmc.tablet.utils.Permissions;
import com.med.vrmc.tablet.utils.ToHexByteUtils;
import com.neurosky.connection.TgStreamReader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 训练中心
 * Created by raytine on 2018/1/2.
 */


public class TrainFragment extends Fragment implements DevicePopulWindow.chengDeviceListener {
    private static TrainFragment fragment;
    @BindView(R.id.btn_scan)
    Button btnScan;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_stop)
    Button btnStop;
    @BindView(R.id.chart1)
    LineChart chart1;
    @BindView(R.id.btn_scan2)
    Button btnScan2;
    @BindView(R.id.btn_heart)
    Button btnHeart;
    @BindView(R.id.chart2)
    LineChart chart2;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private static final String TAG = "TrainFragment";
    private String address = null;
    Unbinder unbinder;
    //show device list while scanning
    private ListView list_select;
    private BTDeviceListAdapter deviceListApapter = null;
    private Dialog selectDialog;
    private TgStreamReader tgStreamReader;
    private BleHeadManager bleHeadManager;
    //设置x轴的数据
    ArrayList<String> xValues = new ArrayList<>();
    //设置y轴的数据()
    List<Integer> yValues = new ArrayList<>();
    List<Integer> yValuess = new ArrayList<>();
    LineChartManager lineChartManager;
    LineChartManager lineChartManagers;
    //手环
    private final int RESULTCODE_TRUE_ON_BLUETOOTH = 0;
    private BluetoothAdapter mBluetoothAdapters;
    private BluetoothAdapter.LeScanCallback mBLEScanCallback;
    private List<BluetoothDevice> mDataList;
    private static final long SCAN_PERIOD = 10000; //5 seconds
    private DevicePopulWindow mDevicePopulWindow;
    private String mDeviceAddress;
    private boolean conn_android = false;
    private boolean mIsScanning;
    private boolean isExit = false;

    public static TrainFragment newInstance() {
        if (fragment == null) {
            fragment = new TrainFragment();
        }
        return fragment;
    }

    int y1;
    private Handler mHandlers = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    isExit = false;
                    break;
                case 2:
                    mDataList = (List<BluetoothDevice>) msg.obj;
                    mDevicePopulWindow.setmDevRssiMap(mDataList);
                    break;
                case 3:
                    Toast.makeText(getActivity(), "connected", Toast.LENGTH_SHORT).show();
                    Log.e("----手环","3");
                    break;
                case 4:
                    conn_android = false;
                    Toast.makeText(getActivity(), "断开连接", Toast.LENGTH_SHORT).show();
                    btnScan2.setText("扫描手环");
                    btnScan2.setEnabled(true);
                    break;
                case 5:
                    conn_android = true;
                    Toast.makeText(getActivity(), "连接成功", Toast.LENGTH_SHORT).show();
                    btnScan2.setText("已连接");
                    btnScan2.setEnabled(false);
                    break;
                case 6:
                    String heartSt  = (String) msg.obj;
                    String substring = heartSt.substring(heartSt.length() - 2, heartSt.length());
                    Log.e(TAG,"心率"+substring);
                    lineChartManagers.addEntry(Integer.parseInt(substring,16));
                    break;
                case 7:
                    Log.e("----手环","7");
                    Toast.makeText(getActivity(), "connect BLE success", Toast.LENGTH_SHORT).show();
                    break;
                case 8:
                    Toast.makeText(getActivity(), "binding service failed", Toast.LENGTH_SHORT).show();
                    break;
                case 9:
                    Toast.makeText(getActivity(), "操作失败,请重新操作", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    private Handler mHanler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    deviceListApapter.addDevice((BluetoothDevice) msg.obj);
                    deviceListApapter.notifyDataSetChanged();
                    break;
                case 2://MEDITATION
                    yValues.add(msg.arg1);
                    lineChartManager.addEntry(yValues);
                    yValues.clear();
                    BLEHelper.getInsrance().writeRXCharacteristic(ToHexByteUtils.hexStringToByte("8900810100"));
                    break;
                case 3://CODE_ATTENTION
                    yValues.add(msg.arg1);
                    break;
                case 4:
                    ToastUtil.showMessage(getActivity(), "Connected");
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.train_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        bleHeadManager = BleHeadManager.newInstance(getActivity());
        bleHeadManager.registerReceiver();
        bleHeadManager.setToUIhandler(mHanler);
        BLEHelper.getInsrance().setmHandler(mHandlers);
        mBluetoothAdapter = bleHeadManager.getDefaultAdapter();
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getActivity(), "设备不支持蓝牙", Toast.LENGTH_LONG).show();
            btnScan.setVisibility(View.GONE);
        }
        Description description = new Description();
        description.setText("头盔数据");
        chart1.setDescription(description);
        Description description2 = new Description();
        description2.setText("手环数据");
        chart2.setDescription(description2);
        //颜色集合
        List<Integer> colors = new ArrayList<>();
        //线的名字集合
        List<String> names = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        names.add("注意力");
        names.add("冥想度");
        lineChartManager = new LineChartManager(chart1, names, colors);
        lineChartManagers = new LineChartManager(chart2, "心率", colors.get(0));
        return view;
    }

    @OnClick({R.id.btn_scan, R.id.btn_start, R.id.btn_stop, R.id.btn_scan2, R.id.btn_heart,R.id.btn_read,R.id.btn_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_heart:
                BLEHelper.getInsrance().writeRXCharacteristic(ToHexByteUtils.hexStringToByte("8900810100"));
                break;
            case R.id.btn_scan2://扫描手环
                mDataList = new LinkedList<BluetoothDevice>();
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 1);
                mDevicePopulWindow = new DevicePopulWindow(getActivity(), this);
                mDevicePopulWindow.showDevice(btnScan2);
                mBLEScanCallback = BLEHelper.getInsrance().getBLEScanCallback();
                mBluetoothAdapters = BLEHelper.getInsrance().checkBLEDevice(getActivity());
                Log.e(TAG, "mBluetoothAdapters" + mBluetoothAdapters.toString());
                if (mBluetoothAdapters != null) {
                    Log.e("----mIsScanning", mIsScanning + "");
                    scanOtherBLEDevice(!mIsScanning);
                } else {
                    Toast.makeText(getActivity(), "设备不支持蓝牙BLE", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_scan:
                Permissions p = new Permissions(getActivity());
                if (p.checkPermissionAllGranted(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})) {
                    bleHeadManager.scanDevice(mBluetoothAdapter);
                    setUpDeviceListView();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            1);
                }
                break;
            case R.id.btn_start://开始测试
//                bleHeadManager.setBadPacketCount(0);
//                if (address != null) {
//                    BluetoothDevice bd = mBluetoothAdapter.getRemoteDevice(address);
//                    tgStreamReader = bleHeadManager.createStreamReader(bd);
//                    tgStreamReader.connectAndStart();
//                } else {
//                    ToastUtil.showMessage(getActivity(), "请链接设备");
//                }
                BLEHelper.getInsrance().writeRXCharacteristic(BTFactory.createTestCMD("向文圣",null));
                break;
            case R.id.btn_stop://停止测试0702810100
//                bleHeadManager.stopRecord();
                BLEHelper.getInsrance().writeRXCharacteristic(ToHexByteUtils.hexStringToByte("0400810100"));
            case R.id.btn_read:
//                bleHeadManager.stopRecord();
                BLEHelper.getInsrance().writeRXCharacteristic(ToHexByteUtils.hexStringToByte("8400810100"));
                break;
            case R.id.btn_back://恢复测试状态
//                bleHeadManager.stopRecord();
                BLEHelper.getInsrance().writeRXCharacteristic(ToHexByteUtils.hexStringToByte("0500810100"));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        if (yValues.size()!= 0){
            yValues.clear();
        }
        if (yValuess.size()!=0){
            yValuess.clear();
        }

        if (bleHeadManager != null) {
            bleHeadManager.stopRecord();
            bleHeadManager.unregisterReceiver();
        }
        super.onDestroyView();

    }



    private void setUpDeviceListView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_select_device, null);
        list_select = (ListView) view.findViewById(R.id.list_select);
        selectDialog = new Dialog(getActivity(), R.style.dialog1);
        selectDialog.setContentView(view);
        //List device dialog
        deviceListApapter = new BTDeviceListAdapter(getActivity());
        list_select.setAdapter(deviceListApapter);
        list_select.setOnItemClickListener(selectDeviceItemClickListener);

        selectDialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {
                // TODO Auto-generated method stub
                Log.e(TAG, "onCancel called!");
//                bleHeadManager.unregisterReceiver();
            }

        });

        selectDialog.show();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            deviceListApapter.addDevice(device);
        }
        deviceListApapter.notifyDataSetChanged();
    }

    //Select device operation
    private OnItemClickListener selectDeviceItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            //unregister receiver
//            getActivity().unregisterReceiver(mReceiver);
//            bleHeadManager.unregisterReceiver();

            mBluetoothDevice = deviceListApapter.getDevice(arg2);
            selectDialog.dismiss();
            selectDialog = null;
            Log.d(TAG, "onItemClick name: " + mBluetoothDevice.getName() + " , address: " + mBluetoothDevice.getAddress());
            address = mBluetoothDevice.getAddress().toString();
            //ger remote device
            BluetoothDevice remoteDevice = mBluetoothAdapter.getRemoteDevice(mBluetoothDevice.getAddress().toString());
            //bind and connect
            //bindToDevice(remoteDevice); // create bond works unstable on Samsung S5
            //showToast("pairing ...",Toast.LENGTH_SHORT);
            tgStreamReader = bleHeadManager.createStreamReader(remoteDevice);
            tgStreamReader.connectAndStart();

        }
    };

    @Override
    public void selectorDevice(BluetoothDevice device) {
        mDevicePopulWindow.close();
        mBluetoothAdapters.stopLeScan(mBLEScanCallback);
        mDeviceAddress = device.getAddress();
        Log.e("---ble", "地址" + mDeviceAddress);
        BLEHelper.getInsrance().initBLEControlService(getActivity(), mDeviceAddress);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULTCODE_TRUE_ON_BLUETOOTH) {
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(getActivity(), "蓝牙已经开启", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "设备不支持蓝牙", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void scanOtherBLEDevice(boolean enable) {
        if (enable) {
            mDataList.clear();
            mHandlers.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //stop scan
                    mIsScanning = false;
                    mBluetoothAdapters.stopLeScan(mBLEScanCallback);
//                    tvBle.setText("蓝牙");
                }
            }, SCAN_PERIOD);
            //start scan
            mIsScanning = true;
            mBluetoothAdapters.startLeScan(mBLEScanCallback);
//            tvBle.setText("停止");
        } else {
            //stop scan
            mIsScanning = false;
            mBluetoothAdapters.stopLeScan(mBLEScanCallback);
//            tvBle.setText("蓝牙");
        }
    }
}
