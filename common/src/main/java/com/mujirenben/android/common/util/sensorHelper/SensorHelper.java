package com.mujirenben.android.common.util.sensorHelper;

import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: panrongfu
 * @date: 2018/9/3 10:18
 * @describe:
 */

public class SensorHelper {

    /**
     * 上传神策打点
     * @param viewProductKey
     * @param map
     */
    public static void uploadTrack(String viewProductKey,Map<String,Object> map){
        JSONObject properties = new JSONObject();
        if(map != null){
            properties = new JSONObject(map);
        }
        SensorsDataAPI.sharedInstance().track(viewProductKey, properties);
    }
}
