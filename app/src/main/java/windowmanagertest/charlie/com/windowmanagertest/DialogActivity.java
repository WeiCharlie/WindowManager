package windowmanagertest.charlie.com.windowmanagertest;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class DialogActivity extends Activity {



        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_dialog);
            // 这里你可以进行一些等待时的操作，我这里用8秒后显示Toast代理等待操作
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run(){

                    DialogActivity.this.finish();
//                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                }
            }, 8000);
        }


    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        AlertDialog dialog = builder.setTitle("Hi").setMessage("你好")
//                .show();
//        dialog.dismiss();

    }
    */
}
