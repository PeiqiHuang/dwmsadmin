package com.dwms.birthday.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dwms.birthday.dao.WishMapper;
import com.dwms.birthday.domain.Wish;
import com.dwms.birthday.domain.form.WishForm;
import com.dwms.birthday.domain.vo.WishVo;
import com.dwms.birthday.service.WishService;
import com.dwms.common.service.impl.BaseService;
import com.dwms.common.util.DateUtil;

@Service("wishService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WishServiceImpl extends BaseService<Wish> implements WishService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WishMapper wishMapper;

	@Override
	public List<WishVo> findAllWishs(WishForm wish) {
		return wishMapper.findAllWishs(wish);

	}

	@Override
	public WishVo findById(Integer wishId) {
		return wishMapper.findById(wishId);
	}

    @Override
    public void updateWishes(Wish wish) {
        this.updateNotNull(wish);
    }

    @Override
    public void addWish(Wish wish) {
        String wishYear = DateUtil.getDateFormat(new Date(), "yyyy");
        wish.setWishYear(wishYear);
        this.wishMapper.insertSelective(wish);
    }

}
