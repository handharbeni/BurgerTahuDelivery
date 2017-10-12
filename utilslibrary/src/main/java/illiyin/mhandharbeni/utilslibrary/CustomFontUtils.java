package illiyin.mhandharbeni.utilslibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by root on 10/12/17.
 */

public class CustomFontUtils {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public static void applyCustomFont(TextView customFontTextView, Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomFontTextView);

        String fontName = attributeArray.getString(R.styleable.CustomFontTextView_font);

        // check if a special textStyle was used (e.g. extra bold)
        int textStyle = attributeArray.getInt(R.styleable.CustomFontTextView_textStyle, 0);

        // if nothing extra was used, fall back to regular android:textStyle parameter
        if (textStyle == 0) {
            textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
        }

        Typeface customFont = selectTypeface(context, fontName, textStyle);
        customFontTextView.setTypeface(customFont);

        attributeArray.recycle();
    }

    private static Typeface selectTypeface(Context context, String fontName, int textStyle) {
        if (fontName.contentEquals(context.getString(R.string.twcen_regular))) {
            return FontCache.getTypeface("twcen_regular.ttf", context);
        }
        else if (fontName.contentEquals(context.getString(R.string.twcen_regular))) {
            /*
            information about the TextView textStyle:
            http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
            */
            switch (textStyle) {
                case Typeface.BOLD: // bold
                    return FontCache.getTypeface("twcen_bold.ttf", context);

                case Typeface.ITALIC: // italic
                    return FontCache.getTypeface("twcen_italic.ttf", context);

                case Typeface.BOLD_ITALIC: // bold italic
                    return FontCache.getTypeface("twcen_bold_italic.ttf", context);

                case 10: // extra light, equals @integer/font_style_extra_light
                    return FontCache.getTypeface("twcen_condensed.ttf", context);

                case 11: // extra bold, equals @integer/font_style_extra_bold
                    return FontCache.getTypeface("twcen_condensed_extra_bold.ttf", context);

                case Typeface.NORMAL: // regular
                default:
                    return FontCache.getTypeface("twcen_regular.ttf", context);
            }
        }
        else {
            // no matching font found
            // return null so Android just uses the standard font (Roboto)
            return null;
        }
    }
}