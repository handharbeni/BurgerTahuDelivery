package illiyin.mhandharbeni.utilslibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 10/12/17.
 */

public class CustomFontTextView extends TextView {

    public CustomFontTextView(Context context) {
        super(context);

        CustomFontUtils.applyCustomFont(this, context, null);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        CustomFontUtils.applyCustomFont(this, context, attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        CustomFontUtils.applyCustomFont(this, context, attrs);
    }
}