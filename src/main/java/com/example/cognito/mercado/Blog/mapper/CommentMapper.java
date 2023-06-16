package com.example.cognito.mercado.Blog.mapper;

import com.example.cognito.mercado.Blog.dto.CommentsDto;
import com.example.cognito.mercado.Blog.model.Comment;
import com.example.cognito.mercado.Blog.model.Post;
import com.example.cognito.mercado.security.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target="Id", ignore=true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target="createdDate", expression = "java(java.time.Instant.now())")
    Comment map(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getName())")
    CommentsDto mapToDto(Comment comment);
}
