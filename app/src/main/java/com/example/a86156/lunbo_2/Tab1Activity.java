package com.example.a86156.lunbo_2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.example.a86156.lunbo_2.bolatu.BolatuAdapter2;
import com.example.a86156.lunbo_2.bolatu.Entity;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Tab1Activity extends AppCompatActivity {

    private CombinedChart dataChart;//图表
    private CombinedData data;  //柏拉图数据
    private GridView gridView;
    private List<Entity> entityList;  //表头数据的实例
    private BolatuAdapter2 adapter;  //表头数据的适配器
    private List<String> scrqList; //生产日期
    private List<String> ydgList; ////应到岗人数
    private List<String> sdgList; //实际到岗人数
    private List<String> bzgsList; //标准工时
    private List<String> zcscList; //正常生产
    private List<String> jdlList; //稼动率
    private List<String> mbjdlList; //目标稼动率
    private List<String> jhslList; //计划数量
    private List<String> sjslList; //实际数量
    private List<String> jhdclList; //计划达成率
    private List<String> mbdclList; //目标达成率
    private List<String> colorList; //判断文本颜色
    private Handler handler; //异步解析
    private Handler handler2; //定时刷新
    private List<String> blNameList; //不良名称
    private List<Integer> blNum; //不良数量
    private List<Float> bllList; //不良率
    private String time; //服务器时间
    private TextView time01; //布局时间
    Handler handler3; //刷新时间；

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);
        //隐藏系统默认标题
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //初始化相关控件和集合
        dataChart = (CombinedChart) findViewById(R.id.manager_chart);
        gridView = (GridView) findViewById(R.id.grid001);
        time01 = (TextView) findViewById(R.id.time001);

        entityList = new ArrayList<>();
        scrqList = new ArrayList<>();
        ydgList = new ArrayList<>();
        sdgList = new ArrayList<>();
        bzgsList = new ArrayList<>();
        zcscList = new ArrayList<>();
        jdlList = new ArrayList<>();
        mbjdlList = new ArrayList<>();
        jhslList = new ArrayList<>();
        sjslList = new ArrayList<>();
        jhdclList = new ArrayList<>();
        mbdclList = new ArrayList<>();
        colorList = new ArrayList<>();
        blNameList = new ArrayList<>();
        blNum = new ArrayList<>();
        bllList = new ArrayList<>();

        //向服务器发送请求获取表头数据
        okHttpBiaotou();
        //向服务器发送请求获取柏拉图数据
        okHttpBolatu();
        showDataOnChart();
        okHttpTime();
        //每隔5秒时间刷新一次
        handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler2.postDelayed(this,15000);
                showDataOnChart();
            }
        },0);


        //展示数据
        showDataOnChart();
        //设置图表描述
        Legend legend = dataChart.getLegend();
        legend.setTextColor(Color.WHITE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        try {
                            for (int i = 0; i < scrqList.size(); i++) {
                                Entity entity = new Entity(
                                        i%2+"",
                                        scrqList.get(i),
                                        ydgList.get(i),
                                        sdgList.get(i),
                                        bzgsList.get(i),
                                        zcscList.get(i),
                                        jdlList.get(i),
                                        mbjdlList.get(i),
                                        jhslList.get(i),
                                        sjslList.get(i),
                                        jhdclList.get(i),
                                        mbdclList.get(i)
                                );
                                entityList.add(entity);
                            }
                            adapter = new BolatuAdapter2(entityList, Tab1Activity.this);
                            gridView.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
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


    }


    /**
     * 向服务器发送请求获取表头数据
     */
    private void okHttpBiaotou() {
        ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
        //延迟1秒后，每隔15秒执行一次该任务
        singleThreadScheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(App.url+App.KanbanYFAll)
                            .build();
                    Response response = client.newCall(request).execute();
                    String dataResponse = response.body().string();
                    Log.d("表头数据", dataResponse + "");
                    parseJSONobjectZl(dataResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1, 15, TimeUnit.SECONDS);

    }

    /**
     * 解析表头数据
     *
     * @param jsonData 传入被解析的json数据
     */
    private void parseJSONobjectZl(String jsonData) {
        try {
            scrqList.clear();
            ydgList.clear();
            sdgList.clear();
            bzgsList.clear();
            zcscList.clear();
            jdlList.clear();
            mbjdlList.clear();
            jhslList.clear();
            sjslList.clear();
            jhdclList.clear();
            mbdclList.clear();
            entityList.clear();
            colorList.clear();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jr = new JSONArray(jsonObject.getString("table"));
            for (int i = 0; i < jr.length(); i++) {
                JSONObject object = new JSONObject(jr.get(i).toString());
                scrqList.add(object.getString("fname"));
                ydgList.add(object.getString("fvalue"));
                sdgList.add(object.getString("fsysrs"));
                bzgsList.add(object.getString("fjhtime"));
                zcscList.add(object.getString("fActTime"));
                jdlList.add(object.getString("factRate"));
                mbjdlList.add(object.getString("fSchjdRate"));
                jhslList.add(object.getString("fSchQty"));
                sjslList.add(object.getString("fActQty"));
                jhdclList.add(object.getString("fschdcrate"));
                mbdclList.add(object.getString("fActdcRate"));
                //colorList.add(object.getString("fyanse"));
            }

            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向服务器发送请求获取柏拉图数据
     */
    private void okHttpBolatu(){
        ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
        //延迟1秒后，每隔1秒执行一次该任务
        singleThreadScheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(App.url+App.MoBLQtyPer)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("柏拉图数据",responseData);
                    parseJSONobjectBolatu(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1, 15, TimeUnit.SECONDS);

    }

    /**
     *  解析柏拉图的JSON数据
     * @param dataJson 被解析的数据
     */
    private void parseJSONobjectBolatu(String dataJson){
        try {
            blNum.clear();
            blNameList.clear();
            bllList.clear();
            JSONObject object = new JSONObject(dataJson);
            JSONArray jr = new JSONArray(object.getString("table"));
            for (int i = 0; i < jr.length(); i++) {
                JSONObject o = new JSONObject(jr.get(i).toString());
                blNameList.add(o.getString("fName"));
                blNum.add(Integer.parseInt(o.getString("fBLqty")));
                bllList.add(Float.parseFloat(o.getString("fBLAddper")));
            }

                  Message message = new Message();
                  message.what=2;
                  handler.sendMessage(message);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 展示数据
     */
    private void showDataOnChart() {
        //绘制图表数据
        data = new CombinedData();
        //设置折线图数据
        data.setData(getLineData());
        //设置柱状图数据
        data.setData(getBarData());
        dataChart.setData(data);
        //设置横坐标数据
        setAxisXBottom();
        //设置右侧纵坐标数据
        setAxisYRight();
        //设置左侧纵坐标数据
        setAxisYLeft();
        dataChart.setTouchEnabled(false);
        dataChart.getDescription().setEnabled(false);
        dataChart.setDrawGridBackground(false);
        dataChart.setDrawBarShadow(false);
        dataChart.setHighlightFullBarEnabled(false);
        dataChart.animateX(2000);
    }

    /**
     * 设置横坐标数据
     */
    private void setAxisXBottom() {
        List<String> valuesX = new ArrayList<>();
        if(blNameList.size()==10){
            for (int i = 0; i < blNameList.size(); i++) {
                valuesX.add(blNameList.get(i));
            }
        }


        //X轴的数据格式
        XAxis xAxis = dataChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(valuesX));
        //设置位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置是否绘制网格线
        xAxis.setDrawGridLines(false);
        dataChart.getAxisLeft().setDrawGridLines(false);
        // barChart.animateY(2500);
        //设置X轴文字剧中对齐
        xAxis.setCenterAxisLabels(false);
        //X轴最小间距
        xAxis.setGranularity(1f);
        //设置柱子的总数量
        xAxis.setLabelCount(10);
        //设置字体大小
        xAxis.setTextSize(10);
        //设置第一条柱子距离左边的距离
        xAxis.setSpaceMin(0.4f);
        xAxis.setSpaceMax(0.4f);
        xAxis.setTextColor(Color.WHITE);


    }

    /**
     * 设置右侧纵坐标数据
     */
    private void setAxisYRight() {
        YAxis right = dataChart.getAxisRight();
        right.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return value + "%";
            }
        });
        right.setDrawGridLines(false);
        right.setAxisMinimum(0f);//为坐标轴设置最小值
        right.setAxisMaximum(100);
        right.setTextSize(15);
        right.setTextColor(Color.RED);
    }

    /**
     * 设置左侧纵坐标数据
     */
    private void setAxisYLeft() {
        YAxis left = dataChart.getAxisLeft();
        left.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int aa = (int)value;
                return aa + "";
            }
        });
        //是否绘制网格线
        left.setDrawGridLines(false);
        left.setAxisMinimum(0f);//为坐标轴设置最小值
        left.setAxisMaximum(600);
        left.setDrawZeroLine(false);
        left.setTextSize(15);
        left.setTextColor(Color.GREEN);
    }

    /**
     * 设置折线图绘制数据
     * @return
     */
    public LineData getLineData() {
        LineData lineData = new LineData();
        List<Entry> customCounts = new ArrayList<>();
        customCounts.clear();
            if(bllList.size()==10){
                for (int i = 0; i <bllList.size() ; i++) {
                    customCounts.add(new Entry(i,bllList.get(i)));
                }
            }else {
                customCounts.add(new Entry(0,1));
            }

        //设置描述
        LineDataSet lineDataSet = new LineDataSet(customCounts, "累计不良");
        //设置折线图依据哪边取值
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        //设置折线的颜色
        lineDataSet.setColor(Color.parseColor("#FF0000"));
        lineDataSet.setValueFormatter(new IValueFormatter(){

            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return value+"";
            }
        });
        lineDataSet.setCircleRadius(5);
        // lineDataSet.setLineWidth(3);
        //设置字体大小
        lineDataSet.setValueTextSize(14);
        //设置字体颜色
        lineDataSet.setValueTextColor(Color.parseColor("#FF0000"));
        lineData.addDataSet(lineDataSet);
        return lineData;
    }

    /**
     * 设置柱状图绘制数据
     *
     * @return
     */
    public BarData getBarData() {
        BarData barData = new BarData();
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        if(blNum.size()==10){
            for (int i = 0; i <blNum.size() ; i++) {
                yVals1.add(new BarEntry(i, blNum.get(i)));
            }

        }

        BarDataSet amountBar = new BarDataSet(yVals1, "不良数量");
        //设置柱状图的颜色
        amountBar.setColor(Color.parseColor("#00FFFF"));
        //设置柱形图上面的字体大小
        amountBar.setValueTextSize(14);
        //设置描述方块的大小
        // amountBar.setFormSize(15);
        amountBar.setValueTextColor(Color.WHITE);

        barData.addDataSet(amountBar);

        //设置柱状图显示的大小
        float barWidth = 0.7f;
        barData.setBarWidth(barWidth);
        return barData;
    }

    /**
     * 发送请求获取服务器时间
     */
    private void okHttpTime(){
        ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
        //延迟1秒后，每隔1秒执行一次该任务
        singleThreadScheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(App.url + App.GetSertime)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.i("服务器时间",responseData);
                    parseJSONTime(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
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
