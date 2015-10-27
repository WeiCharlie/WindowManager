package windowmanagertest.charlie.com.windowmanagertest;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TestActivity extends Activity {

    private LinearLayout childAt;
    private TextView textView;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test);

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
}
