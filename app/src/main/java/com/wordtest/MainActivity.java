package com.wordtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String dataList[] = getRawText(R.raw.link).split("\n");
        class DataItem {
            int tabCnt = 0;
            String data = "";

            public DataItem(int tabCnt, String data) {
                this.tabCnt = tabCnt;
                this.data = data;
            }
        }
        DataItem[] dataItemList = new DataItem[dataList.length];
        for (int i = 0; i < dataList.length; i++) {
            String data = dataList[i];
            String[] dataTabSplit = data.split("\t");
            int tabCnt = dataTabSplit.length - 1;
            // dataList[i] = tabCnt + "@" + dataTabSplit[tabCnt];
            dataItemList[i] = new DataItem(tabCnt, dataTabSplit[tabCnt]);
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (int i = 0; i < dataItemList.length - 1; i++) {
            DataItem nowDataItem = dataItemList[i];
            int nowTabCnt = nowDataItem.tabCnt;
            String nowData = nowDataItem.data;
            for (int j = i + 1; j < dataItemList.length; j++) {
                DataItem tarDataItem = dataItemList[j];
                int tarTabCnt = tarDataItem.tabCnt;
                String tarData = tarDataItem.data;
                if (nowTabCnt == tarTabCnt) {
                    break;
                } else if (nowTabCnt == (tarTabCnt - 1)) {
                    // add
                    String hashVal = hashMap.get(nowData);
                    String putData = "";
                    if (hashVal == null)
                        putData = tarData;
                    else
                        putData = hashVal + "," + tarData;
                    hashMap.put(nowData, putData);
                }
            }
        }
        Log.d("d", "hashMapSize : " + hashMap.size());
        Log.d("d", "hashMap.keySet() : " + hashMap.keySet().size());

        for (String name : hashMap.keySet()) {
            // Log.d("d", "dd");

            Log.d("d", name);
            Log.d("e", hashMap.get(name));
            // Log.d("d", "  " + name + " : " + hashMap.get(name) + "  ");
            // String key = name;
            // Log.d("d", name + " < ");
            // String value = hashMap.get(name).toString();
            // Log.d("d", key + " : " + value);
        }
    }

    public void print(String[] dataList) {
        String print = "";
        for (String data : dataList) {
            print += data + "\n";
        }
        Log.d("d", print);
        ((TextView) findViewById(R.id.textView)).setText(print);
    }

    public String getRawText(int resId) {
        String data = null;
        InputStream inputStream = getResources().openRawResource(resId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            data = new String(byteArrayOutputStream.toByteArray(), "MS949");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
