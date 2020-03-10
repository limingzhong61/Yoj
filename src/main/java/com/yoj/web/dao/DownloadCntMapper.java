package com.yoj.web.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DownloadCntMapper {


    @Update("UPDATE download_cnt SET cnt = cnt + 1 WHERE user_id = #{uid}")
    Integer updateById(Integer uid);

    @Select("SELECT cnt from download_cnt where user_id = #{uid}")
    Integer getDownloadCnt(Integer uid);

    @Insert("INSERT INTO download_cnt(user_id,cnt) VALUES(#{userId},1)")
    Integer insert(Integer userId);

    @Delete("DELETE FROM download_cnt")
    void deleteAll();
}
