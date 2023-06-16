package com.example.cognito.mercado.Blog.service;

import com.example.cognito.mercado.Blog.dto.CommentsDto;
import com.example.cognito.mercado.Blog.exception.BlogException;
import com.example.cognito.mercado.Blog.mapper.CommentMapper;
import com.example.cognito.mercado.Blog.model.Comment;
import com.example.cognito.mercado.Blog.model.Post;
import com.example.cognito.mercado.Blog.repository.CommentRepository;
import com.example.cognito.mercado.Blog.repository.PostRepository;
import com.example.cognito.mercado.security.entity.User;
import com.example.cognito.mercado.security.repository.UserRepository;
import com.example.cognito.mercado.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserRepository userRepository;

    public void save(CommentsDto commentsDto){
       Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new BlogException("Post not found"));
       Comment comment = commentMapper.map(commentsDto,post, userService.getCurrentUser());
       commentRepository.save(comment);

    }

    public List<CommentsDto> getAllCommentsPosts(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BlogException("Post not found"));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsUsers(String userName) {
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new BlogException("User not found"));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
