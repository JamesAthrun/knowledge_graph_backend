package com.example.demo.po;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huaban.analysis.jieba.JiebaSegmenter;

import java.util.*;

public class QuestionPo {
    public String keyWords;
    public String help;
    public String relatedIds;
    public String ver;

    public static QuestionPo getMostMatch(List<QuestionPo> qList, String inputStr) {
        HashMap<QuestionPo, Float> votes = new HashMap<>();
        for (QuestionPo q : qList) {
            votes.put(q, calMatchLevel(inputStr, q.getKeyWords()));
        }
        votes.put(null, 0F);
        QuestionPo max = null;
        for (QuestionPo q : votes.keySet())
            if (votes.get(q) > votes.get(max) || (votes.get(q).equals(votes.get(max)) && Math.random() > 0.5)) max = q;
        return max;
    }

    private static Float calMatchLevel(String inputStr, List<String> keyWords) {
        List<String> indices = new JiebaSegmenter().sentenceProcess(inputStr);//jieba分词
        return calCosSimilarity(indices, keyWords);
    }

    private static Float calCosSimilarity(List<String> indices, List<String> keyWords) {
        HashSet<String> tmp = new HashSet<>();
        tmp.addAll(indices);
        tmp.addAll(keyWords);
        List<String> dict = Arrays.asList(tmp.toArray(new String[0]));
        float[] vec1 = getVec(indices, dict);
        float[] vec2 = getVec(keyWords, dict);

        float var1 = 0F, var2 = 0F, var3 = 0F;
        for (int i = 0; i < dict.size(); i++) {
            var1 += vec1[i] * vec2[i];
            var2 += vec1[i] * vec1[i];
            var3 += vec2[i] * vec2[i];
        }
        return var1 / (float) Math.sqrt(var2 * var3);
    }

    private static float[] getVec(List<String> arr, List<String> dict) {
        float[] vec = new float[dict.size()];
        for (int i = 0; i < dict.size(); i++)
            vec[i] = 0;

        for (String s : arr) {
            for (String m : dict) {
                vec[dict.indexOf(m)] += getSingleWordSimilarity(s, m);
            }
        }
        return vec;
    }

    private static float getSingleWordSimilarity(String s1, String s2) {
        if (s1.equals(s2)) return 1F;
        int tmp = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s2.contains(s1.substring(i))) tmp += 1;
        }
        float res = (float) tmp * tmp / (s2.length() * s2.length());
        return res / 2;
    }

    public List<String> getKeyWords() {
        return JSONArrayToList(keyWords);
    }

    public List<String> getRelatedIds() {
        return JSONArrayToList(relatedIds);
    }

    private List<String> JSONArrayToList(String s) {
        JSONArray ja = JSON.parseArray(s);
        List<String> res = new ArrayList<>();
        int count = 0;
        for (Object item : ja) {
            JSONObject jo = (JSONObject) (item);
            res.add(jo.getString(String.valueOf(count++)));
        }
        return res;
    }
}
