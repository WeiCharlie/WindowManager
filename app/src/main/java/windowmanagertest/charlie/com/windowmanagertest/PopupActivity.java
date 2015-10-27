package windowmanagertest.charlie.com.windowmanagertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class PopupActivity extends AppCompatActivity {

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
    }

    /**
     * PopupWindow实例，
     *
     * @param view
     */
    public void btnPopupWindow(View view) {
        // 1 创建对象，
        if (popupWindow == null) {

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.mipmap.ic_launcher);

            popupWindow = new PopupWindow(imageView, 200, 100);
        }
        if (popupWindow.isShowing()) {
                popupWindow.dismiss();
        } else {
//            popupWindow.showAsDropDown(view,0,0, Gravity.TOP);
            popupWindow.showAtLocation(view// 以哪一个容器为基准进行处理
                    ,Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,//以控件的哪个位置为基准，
                    0,0);// 相对的x y偏移
        }
    }
}
