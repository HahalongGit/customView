package com.lll.beizertest.skin.attr;

import android.view.View;

/**
 * Created by longlong on 2018/4/3.
 *
 * @ClassName: SkinAttr
 * @Description:
 * @Date 2018/4/3
 */

public class SkinAttr {

    private String mResourceName;

    private SkinType mSkinType;


    public void skin(View view) {
        mSkinType.skin(view,mResourceName);
    }
}
