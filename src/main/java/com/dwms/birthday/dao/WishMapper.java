package com.dwms.birthday.dao;

import java.util.List;

import com.dwms.birthday.domain.Wish;
import com.dwms.birthday.domain.form.WishForm;
import com.dwms.birthday.domain.vo.WishVo;
import com.dwms.common.config.MyMapper;

public interface WishMapper extends MyMapper<Wish> {

    List<WishVo> findAllWishs(WishForm wish);

    WishVo findById(Integer wishId);
}