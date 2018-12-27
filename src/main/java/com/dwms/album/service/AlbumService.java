package com.dwms.album.service;

import java.util.List;

import com.dwms.album.domain.Album;
import com.dwms.album.domain.AlbumImg;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;

public interface AlbumService extends IService<Album> {

    List<Album> findAllAlbums(Album album);

    ResponseBo addAlbum(Album album);
    
    ResponseBo updateAlbum(Album album);

    ResponseBo deleteAlbums(String ids);

	Album findById(Integer albumId);
}
