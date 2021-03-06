package com.hms.example.dummyapplication.connectapi;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PublishAPIAsync extends AsyncTask <Void,String,Integer> {

    private static final String CLIENT_ID="345768629914042368";
    private static final String CLIENT_SECRET="7F1F2032934D4521CC64F414E7797830DEA023460E6C1B4BC76CF0649D6F8F93";
    private static final String DOMAIN="https://connect-api.cloud.huawei.com/api";
    private static final String PACKAGE_NAME="com.hms.example.dummyapplication";
    private static final String INFO_URL="https://connect-api.cloud.huawei.com/api/publish/v2/app-info";

    private OnProgressListener listener;

    public PublishAPIAsync(OnProgressListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        String data="{\"0\":{\"0\":\"13\"},\"1\":{\"0\":\"23\"},\"2\":{\"0\":\"34\"},\"3\":{\"0\":\"46\"},\"4\":{\"0\":\"57\"}}";
        try {
            JSONObject json=new JSONObject(data);
            int iden;

            for (int i = 0; i < json.length(); i++) {

                JSONObject ide = json.getJSONObject(String.valueOf(i));
                iden= ide.getInt("0");
                Log.e("JSON",iden+"");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ConnectApiHelper helper=new ConnectApiHelper();
        publishProgress("obtaining access token...");
        helper.appRequestAccessToken("https://oauth-login.cloud.huawei.com/oauth2/v2/token","102405457","5a5ec7e12f2246608b625fc96210c0253e272000ff9b4294fcdd7f0c5ece335a");
        String token=helper.getToken(DOMAIN,CLIENT_ID,CLIENT_SECRET);
        publishProgress("Access Token: "+token);

        publishProgress("Getting APP ID for: "+PACKAGE_NAME);
        String appId=helper.queryAppId(DOMAIN,CLIENT_ID,token,PACKAGE_NAME);
        publishProgress(appId);

        publishProgress("Getting App Information...");
        String info=helper.getAppInfo(DOMAIN,CLIENT_ID,token,appId,"en-US");
        publishProgress(info);

        //helper.updateBasicInformation(DOMAIN,CLIENT_ID,token,appId);

        publishProgress("Getting Upload URL...");
        JSONObject url=helper.getUploadURL(DOMAIN,CLIENT_ID,token,appId,"apk");
        publishProgress("Upload URL: "+ url.toString());
        publishProgress("Getting App Report... ");
        List<String> filterCondition=new ArrayList<>(16);
        List<String> filterConditionValue=new ArrayList<>(16);
        filterCondition.add("countryId");
        filterConditionValue.add("MX");
        String report=helper.getReport(DOMAIN,CLIENT_ID,token,appId,"en-US","20200415","20200612",filterCondition,filterConditionValue);
        publishProgress("Report: "+report);
        return 1;

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if(listener!=null){
            listener.onUpdate(values[0]);
        }
    }

    public interface OnProgressListener{
        void onUpdate(String entry);
    }
}
