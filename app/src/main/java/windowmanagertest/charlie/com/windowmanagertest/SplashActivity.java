package windowmanagertest.charlie.com.windowmanagertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Window window = getWindow();
        View decorView = window.getDecorView();
        if (decorView instanceof ViewGroup) {
            ViewGroup container = (ViewGroup)decorView;
            Button child = new Button(this);
            child.setText("点我进入帮助页面");

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(200,150);
            lp.gravity = Gravity.RIGHT | Gravity.TOP;
            child.setLayoutParams(lp);

            container.addView(child);
            //

            // 测试 ↓
            View childAt = container.getChildAt(0);

            if (childAt instanceof LinearLayout){
                // TODO 在标题栏上面加广告
            }

        }
    }

}
