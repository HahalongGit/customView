package com.lll.beizertest.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by longlong on 2018/4/3.
 *
 * @ClassName: SkinView
 * @Description:
 * @Date 2018/4/3
 */

public class SkinView {

    private View mView;

    private List<SkinAttr> mAttrs;

    public  void skin(){
        for (SkinAttr attr : mAttrs) {
            attr.skin(mView);
        }
    }

}
