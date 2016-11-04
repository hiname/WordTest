package com.wordtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // String wordMapLines[] = FileMgr.readRawTextFile(this, R.raw.wordmap, "UTF-8");
        // HashMap<String, String> linkTree = getLinkTree(wordMapLines);

//        String print = "";
//        for (String key : linkTree.keySet()) {
//            print += key + " : " + linkTree.get(key) + "\n";
//        }
//        print(print);

        String wordLinkLines[] = FileMgr.readRawTextFile(this, R.raw.wordlink, "UTF-8");
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (String wordLink : wordLinkLines) {
            String wl[] = wordLink.split(" : ");
            hashMap.put(wl[0], wl[1]);
        }

//        String print = "";
//        for (Map.Entry<String, String> ent : hashMap.entrySet()) {
//            print += ent.toString() + "\n";
//        }
//        print(print);

        Map<String, String> map = getContainsMap(hashMap, "노랑");

        String print = "";
        for (Map.Entry<String, String> ent : map.entrySet()) {
            print += ent.toString() + "\n";
        }
        print(print);

    }

    public Map<String, String> getContainsMap(HashMap<String, String> hashMap, String standKey) {
        Map<String, String> map = new LinkedHashMap<String, String>();

        for (String nowKey : hashMap.keySet()) {
            if (nowKey.contains(standKey))
                map.put(nowKey, hashMap.get(nowKey));
        }
        return map;
    }

    public HashMap<String, String> getLinkTree(String[] dataList) {
        DataItem[] dataItemList = new DataItem[dataList.length];
        for (int i = 0; i < dataList.length; i++) {
            String data = dataList[i];
            String[] dataTabSplit = data.split("\t");
            int tabCnt = dataTabSplit.length - 1;
            dataItemList[i] = new DataItem(tabCnt, dataTabSplit[tabCnt]);
        }

        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (int i = 0; i < dataItemList.length - 1; i++) {
            DataItem standDataItem = dataItemList[i];
            int standTabCnt = standDataItem.tabCnt;
            String standData = standDataItem.data;
            for (int j = i + 1; j < dataItemList.length; j++) {
                DataItem nowDataItem = dataItemList[j];
                int nowTabCnt = nowDataItem.tabCnt;
                String nowData = nowDataItem.data;
                if (standTabCnt >= nowTabCnt) {
                    break;
                } else if (standTabCnt == (nowTabCnt - 1)) {
                    addHashItem(hashMap, standData, nowData);
                    addHashItem(hashMap, nowData, standData);
                }
            }
        }

        return hashMap;
    }

    public void addHashItem(HashMap<String, String> hashMap, String key, String value) {
        String standVal = hashMap.get(key);
        String putData = "";

        if (standVal == null)
            putData = value;
        else
            putData = standVal + "," + value;

        hashMap.put(key, putData);
    }

    public void print(String data) {
        print(new String[]{data});
    }

    public void print(String[] dataList) {
        String print = "";

        for (String data : dataList) {
            print += data + "\n";
        }
        Log.d("d", print);
        ((TextView) findViewById(R.id.textView)).setText(print);
    }


}
