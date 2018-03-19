package au.com.optus.express.calligraphy;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import uk.co.chrisjenx.calligraphy.CalligraphyFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

public class FontFactory extends CalligraphyFactory {

    private final int[] attributeId;

    public FontFactory(int attributeId) {
        super(attributeId);
        this.attributeId = new int[]{attributeId};
    }

    @Override
    protected String resolveFontPath(Context context, AttributeSet attrs) {
        String fontPath = CalligraphyUtils.pullFontPathFromView(context, attrs, attributeId);

        if (TextUtils.isEmpty(fontPath)) {
            fontPath = CalligraphyUtils.pullFontPathFromTextAppearance(context, attrs, attributeId);

            if (TextUtils.isEmpty(fontPath)) {
                fontPath = CalligraphyUtils.pullFontPathFromStyle(context, attrs, attributeId);
            }
        }

        return fontPath;
    }
}
