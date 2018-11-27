package com.mujirenben.android.mine.mvp.model.bean;

/**
 * Created by mac on 2018/9/25.
 */

public class FuncOption {
    private String  option_tag;
    private int pic_id;

    public FuncOption(String option_tag, int pic_id) {
        this.option_tag = option_tag;
        this.pic_id = pic_id;
    }

    public String getOption_tag() {
        return option_tag;
    }

    public void setOption_tag(String option_tag) {
        this.option_tag = option_tag;
    }

    public int getPic_id() {
        return pic_id;
    }

    public void setPic_id(int pic_id) {
        this.pic_id = pic_id;
    }
}
