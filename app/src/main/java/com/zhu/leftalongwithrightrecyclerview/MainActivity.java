package com.zhu.leftalongwithrightrecyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.zhu.leftalongwithrightrecyclerview.httpbean.HostMenuBean;
import com.zhu.leftalongwithrightrecyclerview.httpbean.HttpResponseBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DoubleLinkedRecylcerView mDoubleLinkedRecylcerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fetchDataFromNet();
    }

    private void initView() {
        mDoubleLinkedRecylcerView = findViewById(R.id.linked_rvs);
    }

    private void fetchDataFromNet() {
        // one left item point multipley   items
        //获取asset目录下的资源文件
        String assetsData = getAssetsData("sort.json");
        Gson gson = new Gson();
        HttpResponseBean responseBean = gson.fromJson(assetsData, HttpResponseBean.class);
        mDoubleLinkedRecylcerView.setData(responseBean);
    }


    private String getAssetsData(String path) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = getAssets().open(path);
            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("fuck", e.getMessage());
            return result;
        }
    }

}
