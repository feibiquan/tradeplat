package com.xfpay.mapper;


import com.xfpay.entity.SimpleEntity;

import java.util.List;

public interface SimpleEntityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SimpleEntity record);

    int insertSelective(SimpleEntity record);

    SimpleEntity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SimpleEntity record);

    int updateByPrimaryKey(SimpleEntity record);

    List<SimpleEntity> selectAll();
}