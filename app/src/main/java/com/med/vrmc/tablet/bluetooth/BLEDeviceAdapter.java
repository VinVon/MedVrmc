package com.med.vrmc.tablet.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.widget.TextView;

import com.med.vrmc.tablet.R;
import com.med.vrmc.tablet.adapter.abs.AbstractAdapter;
import com.med.vrmc.tablet.adapter.abs.ViewHolder;

import java.util.List;


/**
 * Created by raytine on 2017/11/30.
 */

public class BLEDeviceAdapter extends AbstractAdapter<BluetoothDevice> {

    public BLEDeviceAdapter(Context context, List<BluetoothDevice> listData, int resId) {
        super(context, listData, resId);
    }

    @Override
    public void convert(ViewHolder holder, BluetoothDevice bean) {
        TextView tv_name = holder.getView(R.id.item_device_name);
        tv_name.setText(bean.getName());
    }
}
