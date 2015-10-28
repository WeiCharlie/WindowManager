package windowmanagertest.charlie.com.windowmanagertest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import windowmanagertest.charlie.com.windowmanagertest.widgets.AddPopupWindow;
import windowmanagertest.charlie.com.windowmanagertest.widgets.HelpPopupWindow;

public class TestActivity extends Activity implements View.OnClickListener {

    private LinearLayout childAt;
    private TextView textView;
    private int screenWidth;
    private Button btnSet,btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSet = (Button) findViewById(R.id.btnSet);
        initFloatWindow();

        btnAdd.setOnClickListener(this);
        btnSet.setOnClickListener(this);

        topView();// 初始化顶部添加的View
    }

    public void btnDialogShow(View view) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }

    private boolean isShow = false;

    /**
     * 在标题栏上方添加一个自定义布局
     *
     * @param view
     */
    public void btnAddTopLayout(View view) {
        isShow = !isShow;
        if (isShow)
            childAt.addView(textView, 0);
        else
            childAt.removeView(textView);
    }

    private void topView() {
        Window window = getWindow();
        View decorView = window.getDecorView();
        if (decorView instanceof ViewGroup) {
            ViewGroup container = (ViewGroup) decorView;
            textView = new TextView(this);
//            Button child = new Button(this);
            textView.setText("这里可以添加广告或者其他信息");


            // 获取屏幕宽度高度
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            screenWidth =
                    metrics.widthPixels;
            childAt = (LinearLayout) container.getChildAt(0);

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(screenWidth, 150);
            lp.gravity = Gravity.RIGHT | Gravity.TOP;

//            lp.gravity = Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL;
            textView.setLayoutParams(lp);
            textView.setGravity(Gravity.CENTER);
            // TODO 差一些bug没有解决：点击按钮一直添加


        }
    }

    /**
     * 显示或者隐藏悬浮窗
     *
     * @param view
     */
    public void btnShowFloatWindow(View view) {

    }

    private PopupWindow popupWindow;

    /**
     * 显示PopupWindow
     *
     * @param view
     */
    public void btnShowPopupWindow(View view) {
        if (popupWindow == null) {

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);
//            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.popup_window1);
            popupWindow = new PopupWindow(imageView, screenWidth, 100);

        }
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
//            popupWindow.showAsDropDown(view,0,0, Gravity.TOP);
            popupWindow.showAtLocation(view// 以哪一个容器为基准进行处理
                    , Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,//以控件的哪个位置为基准，
                    0, 0);// 相对的x y偏移
        }
    }

    @Override
    public void onClick(View v) {// 点击按钮弹出自定义PopupWindow
        switch (v.getId()){
            case R.id.btnAdd:
                AddPopupWindow addPopupWindow = new AddPopupWindow(TestActivity.this);
                addPopupWindow.showPopupWindow(btnAdd);
                break;
            case R.id.btnSet:
                HelpPopupWindow morePopWindow = new HelpPopupWindow(TestActivity.this);
                morePopWindow.showPopupWindow(btnSet);
                break;


        }
    }

    /**
     * 下面是关于悬浮窗的代码
     */
    public   WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
    private static WindowManager wm;

    private static ImageView imageView;

    private static DisplayMetrics displayMetrics ;

    private void initFloatWindow(){
        displayMetrics = getResources().getDisplayMetrics();

        //系统悬浮窗的指定步骤

        // 1. 使用系统级别的WindowManager
        //getApplicationContext() 必须使用系统的
        wm = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);

        // 2.1 使用Application context 来创建UI控件
        // 避免Activity退出导致的context问题
        if(imageView != null)
            wm.removeViewImmediate(imageView);
        imageView = new ImageView(getApplicationContext());

        // 默认显示的是小机器人
        imageView.setBackgroundResource(R.mipmap.ic_launcher);


        // 3.设置 ImageView 的touch 触摸事件监听
        // 建议使用匿名内部类设置，触摸监听器
        imageView.setOnTouchListener(new FloatWindowTouchListener());


        // 2.2 设置系统级别悬浮窗的参数，保证悬浮窗，悬在手机的桌面上

        // 系统级别需要指定type属性，
        // 对于应用程序使用的系统类型
        // TYPE_SYSTEM_ALERT 允许接收事件
        // TYPE_SYSTEM_OVERLAY 只是悬浮在系统上
//        lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
//                | WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        LP.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        //系统级别悬浮窗，必须指定flags NOT_FOCUSABLE
        // FLAG_NOT_TOUCH_MODAL 允许用户手指点击悬浮窗之外的空间，并且能够将事件向下传递
        LP.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        LP.gravity = Gravity.LEFT | Gravity.TOP;
        LP.x = displayMetrics.widthPixels;
        LP.y = displayMetrics.heightPixels - 600;
        LP.width = WindowManager.LayoutParams.WRAP_CONTENT;
        LP.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LP.format = PixelFormat.TRANSPARENT;

        // 2. addView

        wm.addView(imageView, LP);
    }
    class FloatWindowTouchListener implements View.OnTouchListener , Runnable{
        private float lastX, lastY;



        private boolean canFly = false;

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 101){
                    wm.updateViewLayout(imageView, LP);
                } else if(msg.what == 102){
                    LP.y = displayMetrics.heightPixels - 500;
                    if( LP.x > displayMetrics.widthPixels / 2) {
                        LP.x = displayMetrics.widthPixels; //靠右
                    }
                    else {
                        LP.x = 0;// 靠左
                    }
                    wm.updateViewLayout(imageView, LP);
                    imageView.setBackgroundResource(R.mipmap.ic_launcher);
                    Log.e("-=====--==", "飞行结束");

                    canFly = false;
                } else if(msg.what ==  103){
//                    LP.y = displayMetrics.heightPixels - 500;
                    if( LP.x > displayMetrics.widthPixels / 2) {
                        LP.x = displayMetrics.widthPixels; //靠右
                    }
                    else {
                        LP.x = 0;// 靠左
                    }
                    wm.updateViewLayout(imageView, LP);
                    imageView.setBackgroundResource(R.mipmap.ic_launcher);
                }
            }
        };

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean ret = false;
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    imageView.setBackgroundResource(R.drawable.rocket_bg);

                    Drawable background0 = imageView.getBackground();
                    if(background0 instanceof AnimationDrawable){
                        AnimationDrawable animationDrawable = (AnimationDrawable) background0;
                        animationDrawable.start();
                    }

                    lastX = event.getRawX();
                    lastY = event.getRawY();
                    ret = true;
                    Log.e("Float:", "ACTION_DOWN");
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.e("Float:", "ACTION_MOVE");
                    //在移动的时候，获取屏幕上的事件点击位置，进行增量计算，设置控件位置
                    float cx = event.getRawX();
                    float cy = event.getRawY();
                    //计算增量
                    float ccx = cx - lastX;
                    float ccy = cy - lastY;
                    //调整悬浮窗的坐标数值
                    LP.x += ccx;
                    LP.y += ccy;
                    //更新悬浮窗
                    wm.updateViewLayout(imageView, LP);

                    //将当前坐标保存成lastX, lastY,在下一次移动的时候使用
                    lastX = cx;
                    lastY = cy;

                    if(lastY > displayMetrics.heightPixels - 200){
                        canFly = true;
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    Log.e("Float:", "ACTION_UP");
                    Drawable background = imageView.getBackground();
                    if(background instanceof AnimationDrawable){
                        AnimationDrawable animationDrawable = (AnimationDrawable) background;
                        animationDrawable.start();
                    }

                    if(canFly){
                        // TODO 开一个线程不断改变小火箭的位置
                        new Thread(this).start();
                    } else {
                        // TODO 让小火箭靠边
                        handler.sendEmptyMessage(103);
                    }

                    break;
            }
            return ret;
        }

        @Override
        public void run() {

            try{
                while (true){
                    LP.y -= 20f;
                    if(LP.y > 0){
                        handler.sendEmptyMessage(101);
                    } else {
                        handler.sendEmptyMessage(102);
                        break;
                    }
                    Thread.sleep(10);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
