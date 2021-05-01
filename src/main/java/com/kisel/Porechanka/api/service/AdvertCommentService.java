package com.kisel.Porechanka.api.service;

import com.kisel.Porechanka.model.dto.AdvertCommentCreateDto;
import com.kisel.Porechanka.model.dto.AdvertCommentDto;

import java.util.List;

public interface AdvertCommentService {

    List<AdvertCommentDto> getAllByAdvertId(long id);

    List<AdvertCommentDto> createNewComment(AdvertCommentCreateDto advertCommentCreateDto, long advertId, long currentUserId);

    List<AdvertCommentDto> updateComment(long commentId, String text, long userId);

    List<AdvertCommentDto> deleteComment(long commentId, long userId, boolean isAdmin);


}
