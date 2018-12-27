package com.dwms.birthday.service;

import java.util.List;

import com.dwms.birthday.domain.Wish;
import com.dwms.birthday.domain.form.WishForm;
import com.dwms.birthday.domain.vo.WishVo;
import com.dwms.common.service.IService;

public interface WishService extends IService<Wish> {

	List<WishVo> findAllWishs(WishForm wish);

	WishVo findById(Integer wishId);

    void updateWishes(Wish wish);

    void addWish(Wish wish);

}
