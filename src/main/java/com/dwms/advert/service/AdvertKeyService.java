package com.dwms.advert.service;

import java.util.List;

import com.dwms.advert.domain.AdvertKey;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;

public interface AdvertKeyService extends IService<AdvertKey> {

    List<AdvertKey> findAll(AdvertKey key);

    ResponseBo addAdKey(AdvertKey key);
    
    ResponseBo updateAdKey(AdvertKey key);
    
    ResponseBo deleteAdKeys(String ids);

    AdvertKey findByKey(String adKey);
}
