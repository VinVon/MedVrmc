package com.med.vrmc.tablet.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.med.vrmc.tablet.R;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by raytine on 2017/11/30.
 */

public class DevicePopulWindow extends PopupWindow {
    private Context mContext;
    private List<BluetoothDevice> mDevRssiMap;
    private PopupWindow popupWindow;
    private BLEDeviceAdapter mBLEDeviceAdapter;
    private ListView mListView;
    private chengDeviceListener mchengDeviceListener;
    public DevicePopulWindow(Context mContext,chengDeviceListener mchengDeviceListener) {
        this.mContext = mContext;
        mDevRssiMap = new ArrayList<>();
        this.mchengDeviceListener = mchengDeviceListener;
    }

    public void setmDevRssiMap(List<BluetoothDevice> mmm) {
        if (mDevRssiMap.size() != 0){
            mDevRssiMap.clear();
        }
        mDevRssiMap.addAll(mmm);
        mBLEDeviceAdapter.notifyDataSetChanged();
    }

    public void showDevice(View parent){
        mBLEDeviceAdapter = new BLEDeviceAdapter(mContext,mDevRssiMap, R.layout.device_item);
        popupWindow = new PopupWindow(mContext);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_popupwindow_device, null);
        popupWindow.setContentView(inflate);
        popupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.color.white));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        mListView = (ListView) inflate.findViewById(R.id.device_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mchengDeviceListener.selectorDevice(mDevRssiMap.get(position));
            }
        });
        mListView.setAdapter(mBLEDeviceAdapter);
        popupWindow.showAsDropDown(parent);
    }
    public interface  chengDeviceListener{
        public void selectorDevice(BluetoothDevice device);
    };
    public void close(){
        if (popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }
}
