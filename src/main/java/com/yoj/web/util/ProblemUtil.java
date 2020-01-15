package com.yoj.web.util;

import com.alibaba.fastjson.JSON;
import com.yoj.web.pojo.Problem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProblemUtil {

    public void changeDataToList(Problem problem){
        List<List<String>> data = new ArrayList<>();
        List<String> judgeData = JSON.parseArray(problem.getJudgeData(), String.class);
        for (int judgeNumber = 0; judgeNumber < judgeData.size(); judgeNumber++) {
            List<String> judgeCase = JSON.parseArray(judgeData.get(judgeNumber), String.class);
            data.add(judgeCase);
        }
        problem.setJudgeData(null);
        problem.setData(data);
    }

    public void changeDataToJSON(Problem problem){
        List<List<String>> data = problem.getData();
        for (List<String> judgeCase : data) {
            boolean empty = true;
            // in and out are only two file
            if(judgeCase.size() > 2){
                data.remove(judgeCase);
            }
            for (int i = 0; i < judgeCase.size(); i++) {
                String str = judgeCase.get(i);
                if(!str.isEmpty()){
                    empty = false;
                    // remove both side all blank char
                    StringBuffer stringBuffer = new StringBuffer(str.trim());
                    // add a new line char
                    stringBuffer.append('\n');
                    judgeCase.set(i,stringBuffer.toString());
                }
            }
            // in file and out file both are empty
            if (empty) {
                data.remove(judgeCase);
            }
        }
        problem.setJudgeData(JSON.toJSONString(data));
        problem.setData(null);
    }
}
