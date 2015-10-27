package windowmanagertest.charlie.com.windowmanagertest;

import android.graphics.PixelFormat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements SeekBar.OnSeekBarChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        // Activity Window的测试，Window的属性
        // 获取Activity自身的显示的顶级Window的对象
        Window window = getWindow();
        // window.getDecorView() 才是真正的android UI中一个Activity的顶级容器
        View view = window.getDecorView();

        SeekBar brightBar = (SeekBar) view.findViewById(R.id.brightness_bar);
        brightBar.setOnSeekBarChangeListener(this);
        // 获取当前的属性
        View currentFocus = window.getCurrentFocus();
        //  获取Window的属性
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        // 设置当前的参数的gravity为左上
//        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.gravity = Gravity.CENTER;
/*
        // 设置x偏移，只有gravity设置Left Right之后，才起作用
        layoutParams.x = 100;
        // 设置y偏移，只有gravity设置top bottom之后，起作用
        layoutParams.y = 300;
        // 只有设置了宽高之后，xy才起作用
 */
        layoutParams.width = 700;
        //
        layoutParams.height = 600;

        layoutParams.format = PixelFormat.TRANSLUCENT;
//        layoutParams.alpha = 0.5f;
        // 更新属性
        window.setAttributes(layoutParams);

        /**
         * window作用
         * 1 一个Activity就有一个window；
         * 2 Window是Activity显示内容的管理；用于管理实际显示的内容
         *  Window内部使用DecorView来描述整个UI的最顶级容器，就是一个FragLayout的子类
         * 3 所有Activity中UI相关的方法都是Window来执行，调用内部的DecorView来完成
         * 4 Window内部维护了当前的焦点；如果Window失去焦点，那么Activity将不可以交互
         *  Window同时是事件传递的入口，由Activity把事件传递给Window，Window再把事件
         *   传递给DecorView，一直向下传递
         *  5
         *
         */

        /**
         * 在Activity之上加一个Button
         */
        Button button = new Button(this);
        button.setText("帮助");

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int id = seekBar.getId();
        Window window = getWindow();
        switch (id) {
            case R.id.brightness_bar:
                // 调整屏幕亮度
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.screenBrightness = (float) (progress * 0.01);
                window.setAttributes(attributes);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
