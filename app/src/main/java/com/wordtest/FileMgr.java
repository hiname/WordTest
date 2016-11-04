package com.wordtest;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by USER on 2016-11-04.
 */
public class FileMgr {

    public static String[] readRawTextFile(Context ctx, int resId, String enc){

        ArrayList<String> lineList = new ArrayList<String>();

        InputStream inputStream = ctx.getResources().openRawResource(resId);

        InputStreamReader inputReader = null;
        try {
            inputReader = new InputStreamReader(inputStream, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedReader buffreader = new BufferedReader(inputReader);
        String line;

        try {
            while (( line = buffreader.readLine()) != null)
                lineList.add(line);

        } catch (IOException e) {
            return null;
        }

        return lineList.toArray(new String[lineList.size()]);
    }

    /**
     * 문제 있는 코드.
     * 해당 메소드로 텍스트 파일을 불러온 뒤(ANSI)
     * "\n" 으로 split - hash에 넣어쓸 때 불러오기가 제대로 안됨.
     * TextView, Toast 에는 제대로 뜨지만,
     * Log.d(), System.out.println() 등에서 출력 자체가 되지 않음.
     * (비어있는게 아니라, 아예 메소드 수행이 안되는 것으로 보임.)
     * (인코딩 방식(디코딩 등)에 따른 캐릭터셋 관련 문제로 보여짐.)
     */
    public static void readRawText(Context context) {
        String data = null;
        InputStream inputStream = context.getResources().openRawResource(R.raw.wordmap);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray(),"MS949");
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] dataList = data.split("\n");
        for (int ii = 0; ii < dataList.length; ii++) {
            Log.d("d", "start");
            Log.d("d", dataList[ii] + " <- str");
            Log.d("d", "end");

        }
    }
}
