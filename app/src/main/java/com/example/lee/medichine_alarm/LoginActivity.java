package com.example.lee.medichine_alarm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {

//    ProgressDialog dialog ;
    boolean loginCheck = false;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.login_layout);

        Button loginB = (Button) findViewById(R.id.logButton2);
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginThread loging = new loginThread();
                loging.execute();

            }
        });
    }
//
//    @Override
//    protected void onStop(){
//        super.onStop();
//        if( ll!= null)
//        {
//            loginDialog.dismiss();
//        }
//    }

    private class loginThread extends AsyncTask<Integer, Integer, Integer>{
        ProgressDialog loginDialog = new ProgressDialog(LoginActivity.this);
        int i = 5;
        @Override
        protected void onPreExecute(){
            loginDialog.setMessage("로그인중입니다!(예상시간 "+i+"초)");
            loginDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            for (i = 5; i > 0; i--) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return 0;
    }

        @Override
        protected void onProgressUpdate(Integer...progress){
            loginDialog.setMessage("로그인중입니다!(예상시간 : "+i+"초)");
            super.onProgressUpdate(0);
        }

        @Override
        protected void onPostExecute(Integer result){
            loginDialog.dismiss();
            loginCheck = true;
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            super.onPostExecute(0);

        }
    }

}

