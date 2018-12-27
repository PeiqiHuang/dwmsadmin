package com.dwms.advert.service;

import java.util.List;

import com.dwms.advert.domain.Advert;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;

public interface AdvertService extends IService<Advert> {

    List<Advert> findAllAdverts(Advert advert);

    ResponseBo addAdvert(Advert advert);
    
    ResponseBo updateAdvert(Advert advert);

    ResponseBo deleteAdverts(String ids);
    
    Advert findById(Integer adId);

}
