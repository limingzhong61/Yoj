package com.yoj.web.util;

import com.yoj.web.pojo.Contest;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ContestUtil {
    public boolean isStarted(Contest contest) {
        return new Date().getTime() > contest.getStartTime().getTime();
    }

    public boolean isEnd(Contest contest) {
        return new Date().getTime() > contest.getEndTime().getTime();
    }

    /**
     *
     * @param contest
     * @return -1 not started,
     *          0 contesting,
     *          1 end.
     */
    public int computeContestStatus(Contest contest){
        if(!isStarted(contest) ){
            return -1;
        }else if(isEnd(contest)){
            return 1;
        }
        return 0;
    }
}
