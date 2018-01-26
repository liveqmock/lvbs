package com.daishumovie.dao.mapper.smallbronze;

import org.apache.ibatis.annotations.Param;

public interface UDUserWatchHistoryMapper {
    //去除重复数据
    int removeDuplicateData(@Param("uid") Integer uid);
}