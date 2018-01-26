package com.daishumovie.dao.mapper.smallbronze;

import com.daishumovie.dao.model.DsmWechatUser;
import com.daishumovie.dao.model.DsmWechatUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DsmWechatUserMapper {
    long countByExample(DsmWechatUserExample example);

    int deleteByExample(DsmWechatUserExample example);

    int deleteByPrimaryKey(String openid);

    int insert(DsmWechatUser record);

    int insertSelective(DsmWechatUser record);

    List<DsmWechatUser> selectByExample(DsmWechatUserExample example);

    DsmWechatUser selectByPrimaryKey(String openid);

    int updateByExampleSelective(@Param("record") DsmWechatUser record, @Param("example") DsmWechatUserExample example);

    int updateByExample(@Param("record") DsmWechatUser record, @Param("example") DsmWechatUserExample example);

    int updateByPrimaryKeySelective(DsmWechatUser record);

    int updateByPrimaryKey(DsmWechatUser record);
}