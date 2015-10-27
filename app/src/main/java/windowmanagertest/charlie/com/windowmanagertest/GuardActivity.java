package windowmanagertest.charlie.com.windowmanagertest;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 悬浮窗的演示，类似于360
 * 这个悬浮窗属于Activity级别，当Activity销毁，悬浮窗也没了。
 * 
 */
public class GuardActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard);

        /**
         * 关于悬浮窗的泄露：
         * 1 使用Activity getWindowManager获取的WindowManager，
         *      再添加悬浮窗的时候，实际上添加的是Activity级别的悬浮窗
         *      默认这种悬浮窗，是无法单独在手机上存在的；
         * 2 悬浮窗再添加的时候，如果没有指定LayoutParams type参数，那么
         *      默认悬浮窗也是Activity级别的。
         * 3 当Activity退出的时候，那么这个级别的悬浮窗要必须经过remove的操作
         *      使用WindowManager的removeView可以删除并且释放悬浮窗；
         *
         */
        // 1 悬浮窗的显示方式：使用WindowManager
        WindowManager windowManager = getWindowManager();

        // 2.1 准备addView的参数一 ：View对象(作为悬浮窗整体的View)
        imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);

        // 2.2 准备addView的参数二：WindowManager.LayoutParams
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        //设置悬浮窗所在的内容范围，左上到屏幕右下角
        lp.gravity = Gravity.LEFT | Gravity.TOP;
//        lp.gravity = Gravity.CENTER;
        // xy是相对于Gravity设置的位置而言，的偏移；
        // 悬浮窗不会出屏幕

        lp.x = 100;
        lp.y = 100;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // 注意事项：悬浮窗通过addView之后如果悬浮窗View可以获取焦点
        //      那么下层的Activity将接收不到任何事件，包括返回键
        //      设置flags包含FLAG_NOT_FOCUSABLE,解决这个问题
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        // 设置悬浮窗中没有内容的区域，显示为透明
        lp.format = PixelFormat.TRANSPARENT;

        // 2 添加悬浮窗：WindowManager.addView(View ,LayoutParams)
        windowManager.addView(imageView,lp );

    }

    @Override
    protected void onDestroy() {
        WindowManager windowManager = getWindowManager();
        // 释放Window悬浮窗
        windowManager.removeViewImmediate(imageView);
        super.onDestroy();
    }
}
