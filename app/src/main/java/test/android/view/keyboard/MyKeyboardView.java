package test.android.view.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Environment;
import android.util.AttributeSet;

/**
 * Create by Xiangshifu
 * on 2018/7/31 下午1:12
 */
public class MyKeyboardView extends Keyboard {


    public MyKeyboardView(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public MyKeyboardView(Context context, int xmlLayoutResId, int modeId, int width, int height) {
        super(context, xmlLayoutResId, modeId, width, height);
    }

    public MyKeyboardView(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);

    }

}
