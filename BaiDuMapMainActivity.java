package com.netease.nim.uikit.business.session.actions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.module.Container;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.util.NIMUtil;


public class BaiDuMapMainActivity extends AppCompatActivity {
    private TextView send;
    private MapView mapview;
    BaiduMap map;
    String locationdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bai_du_map_main2);
        mapview = (MapView) findViewById(R.id.mapview);
        send = (TextView) findViewById(R.id.send);
        map = mapview.getMap();
        initLocationOption();

    }

        private void initLocationOption() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
                LocationClient locationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类实例并配置定位参数
                LocationClientOption locationOption = new LocationClientOption();
                MyLocationListener myLocationListener = new MyLocationListener();
        //注册监听函数
                locationClient.registerLocationListener(myLocationListener);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
                locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
                locationOption.setCoorType("gcj02");
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
                locationOption.setScanSpan(1000);
        //可选，设置是否需要地址信息，默认不需要
                locationOption.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
                locationOption.setIsNeedLocationDescribe(true);
        //可选，设置是否需要设备方向结果
                locationOption.setNeedDeviceDirect(false);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
                locationOption.setLocationNotify(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
                locationOption.setIgnoreKillProcess(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
                locationOption.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
                locationOption.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否收集CRASH信息，默认收集
                locationOption.SetIgnoreCacheException(false);
        //可选，默认false，设置是否开启Gps定位
                locationOption.setOpenGps(true);
        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
                locationOption.setIsNeedAltitude(false);
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
                locationOption.setOpenAutoNotifyMode();
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
                locationOption.setOpenAutoNotifyMode(3000,1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
        //开始定位
                locationClient.start();

            }
            /**
             * 实现定位回调
             */
            public class MyLocationListener extends BDAbstractLocationListener {
                @Override
                public void onReceiveLocation(final BDLocation location){
                    //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
                    //以下只列举部分获取经纬度相关（常用）的结果信息
                    //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

                    //获取纬度信息
                    final double latitude = location.getLatitude();
                    Log.d("MyLocationListener", "latitude:" + latitude);
                    //获取经度信息
                    final double longitude = location.getLongitude();
                    Log.d("MyLocationListener", "longitude:" + longitude);
                    locationdata+=latitude+longitude;
                    //获取定位精度，默认值为0.0f
                    float radius = location.getRadius();
                    Log.d("MyLocationListener", "radius:" + radius);
                    //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
                    String coorType = location.getCoorType();
                    Log.d("MyLocationListener", "coorType:" + coorType);
                    //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
                    int errorCode = location.getLocType();
                    Log.d("MyLocationListener", "errorCode:" + errorCode);

                    //定义Maker坐标点

                    LatLng point = new LatLng(latitude, longitude);

        //构建Marker图标

                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_launcher);

        //构建MarkerOption，用于在地图上添加Marker

                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);

        //在地图上添加Marker，并显示
                    map.addOverlay(option);

                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String account = BaseAction.container.account;
                            SessionTypeEnum sessionType = BaseAction.container.sessionType;
                            int value = sessionType.getValue();
                            Toast.makeText(BaiDuMapMainActivity.this, account, Toast.LENGTH_SHORT).show();
                            Toast.makeText(BaiDuMapMainActivity.this, "value:" + value, Toast.LENGTH_SHORT).show();
                            IMMessage message = MessageBuilder.createLocationMessage(account, BaseAction.container.sessionType, latitude, longitude,"北京市海淀区东北旺路软件园南站");
                            NIMClient.getService(MsgService.class).sendMessage(message,false);
                            finish();
                        }
                    });
                }
            }
}
