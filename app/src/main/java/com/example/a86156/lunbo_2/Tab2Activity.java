package com.example.a86156.lunbo_2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.example.a86156.lunbo_2.gongdan.GongdanAdapter;
import com.example.a86156.lunbo_2.gongdan.GongdanEntity;
import com.example.a86156.lunbo_2.gongdan.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tab2Activity extends AppCompatActivity {
    private List<GongdanEntity> dataList;  //存放每行的数据
    private GongdanAdapter adapter; //适配器类
    private GridView gridView;  //主布局
    private List<String> colorList; //颜色
    private List<String> jhjhrqList; // 计划交货日期
    private List<String> ddbhList; // 订单编号
    private List<String> cpbhList; // 产品编号
    private List<String> cpmcList; // 产品名称
    private List<String> jhslList; // 计划数量
    private List<String> yzjlList; // 压铸机
    private List<String> cpflList; // 除披锋
    private List<String> jjlList; // 机加
    private List<String> pglList; // 抛光
    private List<String> ddList; // 电镀
    private List<String> bzList; // 包装
    private String time; //服务器时间
    private TextView time01; //布局时间
    static int num = 0;

    Handler handler; //异步处理
    Handler handler2; //定时刷新
    Handler handler3; //刷新时间；


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        dataList = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.grid);
        time01 = (TextView) findViewById(R.id.time);
        colorList = new ArrayList<>();
        jhjhrqList = new ArrayList<>();
        ddbhList = new ArrayList<>();
        cpbhList = new ArrayList<>();
        cpmcList = new ArrayList<>();
        jhslList = new ArrayList<>();
        yzjlList = new ArrayList<>();
        cpflList = new ArrayList<>();
        jjlList = new ArrayList<>();
        pglList = new ArrayList<>();
        ddList = new ArrayList<>();
        bzList = new ArrayList<>();
        okHttpDingdanKanban();
        okHttpTime();
        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        for (int i = 0; i < colorList.size(); i++) {
                            GongdanEntity entity = new GongdanEntity(
                                    colorList.get(i),
                                    jhjhrqList.get(i),
                                    ddbhList.get(i),
                                    cpbhList.get(i),
                                    cpmcList.get(i),
                                    jhslList.get(i),
                                    yzjlList.get(i),
                                    cpflList.get(i),
                                    jjlList.get(i),
                                    pglList.get(i),
                                    ddList.get(i),
                                    bzList.get(i)
                            );
                            dataList.add(entity);
                            adapter = new GongdanAdapter(dataList,Tab2Activity.this);
                            gridView.setAdapter(adapter);
                        }
                        break;
                    case 2:
                        time01.setText(time);
                        break;
                        default:
                            break;
                }


            }
        };

        /**
         *  定时刷新数据
         */
        handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                okHttpDingdanKanban();
                handler2.postDelayed(this,10000);
            }
        },0);

        /**
         * 定时刷新系统时间
         */
        handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                okHttpTime();
                handler3.postDelayed(this,1000);
            }
        },0);



    }

    /**
     * 向服务器发送请求获取订单产出看板
     */
    private void okHttpDingdanKanban() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(App.url)
                            .build();
                    Response response = client.newCall(request).execute();
                    String dataResponse = response.body().string();
                    parseJSONdingDan(dataResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 解析订单看板的json数据
     */
    private void parseJSONdingDan(String json) {
        try {
            colorList.clear();
            jhjhrqList.clear();
            ddbhList.clear();
            cpbhList.clear();
            cpmcList.clear();
            jhslList.clear();
            yzjlList.clear();
            cpflList.clear();
            jjlList.clear();
            pglList.clear();
            ddList.clear();
            bzList.clear();
            dataList.clear();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jr = new JSONArray(jsonObject.getString("table"));
            for (int i = 0; i < jr.length(); i++) {
                JSONObject object = new JSONObject(jr.get(i).toString());
                colorList.add(object.getString("fcolor"));
                jhjhrqList.add(object.getString("fjhdate"));
                ddbhList.add(object.getString("fNr"));
                cpbhList.add(object.getString("fitemNr"));
                cpmcList.add(object.getString("fName"));
                jhslList.add(object.getString("fSchQty"));
                yzjlList.add(object.getString("fyazhu"));
                cpflList.add(object.getString("fqupifeng"));
                jjlList.add(object.getString("fjijia"));
                pglList.add(object.getString("fpaoguang"));
                ddList.add(object.getString("fdiandu"));
                bzList.add(object.getString("fbaozhuang"));
            }
                Message message = new Message();
                message.what=1;
                handler.sendMessage(message);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送请求获取服务器时间
     */
    private void okHttpTime(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   OkHttpClient client = new OkHttpClient();
                   Request request = new Request.Builder()
                           .url(App.url)
                           .build();
                   Response response = client.newCall(request).execute();
                   String responseData = response.body().string();
                   Log.d("pooo",responseData.length()+"");
                   parseJSONTime(responseData);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }).start();
    }

    /**
     *  解析服务器时间
     * @param json 被解析的数据
     */
    private void parseJSONTime(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jr = new JSONArray(jsonObject.getString("table"));
            for (int i = 0; i <jr.length() ; i++) {
                JSONObject object = new JSONObject(jr.get(i).toString());
                time = object.getString("serTime");
                Log.d("dsfsdgdg",object.getString("serTime"));
            }

            Message message = new Message();
            message.what=2;
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
