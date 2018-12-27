package com.dwms.birthday.service.impl;

import java.util.List;

import com.dwms.birthday.domain.WishTpl;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;

public interface WishTplService extends IService<WishTpl> {

    List<WishTpl> findAll(WishTpl tpl);

    ResponseBo addWishTpl(WishTpl tpl);

    ResponseBo updateWishTpl(WishTpl tpl);

    ResponseBo deleteWishTpls(String ids);

}
