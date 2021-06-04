package com.example.demo.po;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.vo.KGEditFormVo;

import java.util.List;

public class HistoryPo {
    public String detail;
    public String time;
    public String ver;
    public String tableId;

    public static JSONArray toJsonArray(List<HistoryPo> hs) {
        JSONArray ja = new JSONArray();
        for (HistoryPo h :
                hs) {
            JSONObject jo = new JSONObject();
            if (h.detail.startsWith("NKG:")) {
                jo.put("detail", h.detail);
            } else {
                jo.put("detail", TransToDetail(h.detail));
            }
            jo.put("time", h.time);
            jo.put("ver", h.ver);
            ja.add(jo);
        }
        return ja;
    }

    private static JSONArray TransToDetail(String jsonStr) {
        JSONArray detailJa = JSON.parseArray(jsonStr);
        JSONArray ja = new JSONArray();
        for (Object o :
                detailJa) {
            JSONObject tmp = (JSONObject) o;
            KGEditFormVo f = JSON.toJavaObject(tmp, KGEditFormVo.class);
            ja.add(f.toJSONObject(0));
        }
        return ja;
    }

}
