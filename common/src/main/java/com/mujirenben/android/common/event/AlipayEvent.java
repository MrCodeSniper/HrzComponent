package com.mujirenben.android.common.event;

/**
 * @author: panrongfu
 * @date: 2018/8/17 19:40
 * @describe:
 */

public class AlipayEvent {

    private String resultStatus;
    private String resultInfo;

    public AlipayEvent() {
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    @Override
    public String toString() {
        return "AlipayEvent{" +
                "resultStatus='" + resultStatus + '\'' +
                ", resultInfo='" + resultInfo + '\'' +
                '}';
    }
}
