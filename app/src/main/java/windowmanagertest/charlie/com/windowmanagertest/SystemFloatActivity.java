package windowmanagertest.charlie.com.windowmanagertest;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 系统悬浮窗，开启的悬浮窗可以在手机桌面上显示
 */
public class SystemFloatActivity extends AppCompatActivity {

    public WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
    private static WindowManager wm;
    private static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_float);

        // 系统悬浮窗的制定步骤

        // 1 使用系统级别的WindowManager,必须强行使用系统级别
        wm = (WindowManager)getApplicationContext(). getSystemService(WINDOW_SERVICE);

        // 2.1 使用进程Application context来创建UI控件
        // 避免Activity退出导致的context问题
        if (imageView != null) {
            wm.removeViewImmediate(imageView);
        }
         imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.mipmap.ic_launcher);

         // 2.3 设置ImageView的touch事件监听
        // 建议使用匿名内部类来设置触摸监听器
        imageView.setOnTouchListener(new View.OnTouchListener() {
            private float lastX,lastY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean ret = false;
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        ret = true;
                        Log.d("ABC","ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 在移动的时候获取屏幕上的事件点击位置
                        // 进行增量计算，设置控件位置
                        float cx = event.getRawX();// 相当于屏幕的
                        float cy = event.getRawY();
                        // 计算增量
                        float ccx = cx - lastX;
                        float ccy = cy - lastY;
                        // 调整悬浮窗的坐标数值
                        LP.x += ccx;
                        LP.y += ccy;

                        // 更新悬浮窗
                        wm.updateViewLayout(imageView,LP);
                        // 将当前坐标保存成lastX，lastY，在下一次移动的时候使用
                        lastX = cx;
                        lastY = cy;
                        Log.d("ABC","ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("ABC","ACTION_UP");
                        break;
                }
                return ret;
            }
        });

        // 2.2 设置系统级别悬浮窗的参数，保证悬浮窗悬在手机桌面上
        // 系统级别需要指定type属性，
        // 对于应用程序使用的系统类型：
        //      TYPE_SYSTEM_ALERT：允许接受事件
        //      TYPE_SYSTEM_OVERLAY：只是悬浮在系统上获取事件
        // 使用系统级别的悬浮窗必须要指定权限 |WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
        LP.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
// 系统级别悬浮窗必须要指定NOT_FOUCUSABLE
        // FLAG_NOT_TOUCH_MODAL 允许用户手指点击悬浮窗以外的空间
            // 并且能够将事件向下传递
        LP.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        LP.gravity = Gravity.LEFT | Gravity.TOP;
        LP.x = 300;
        LP.y = 300;

        LP.width = WindowManager.LayoutParams.WRAP_CONTENT;
        LP.height = WindowManager.LayoutParams.WRAP_CONTENT;
        LP.format = PixelFormat.TRANSPARENT;

        // 2 addView
        wm.addView(imageView, LP);
    }
}
