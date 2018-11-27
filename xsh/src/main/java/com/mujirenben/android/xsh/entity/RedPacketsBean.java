package com.mujirenben.android.xsh.entity;

import java.util.List;

public class RedPacketsBean {

    private String code;
    private List<WelfareBean.RedPacketSubBean> readPackets;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<WelfareBean.RedPacketSubBean> getReadPackets() {
        return readPackets;
    }

    public void setReadPackets(List<WelfareBean.RedPacketSubBean> readPackets) {
        this.readPackets = readPackets;
    }
}
