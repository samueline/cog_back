package com.example.cognito.mercado.Blog.service;

import com.example.cognito.mercado.Blog.dto.SubredditDto;
import com.example.cognito.mercado.Blog.exception.BlogException;
import com.example.cognito.mercado.Blog.mapper.SubredditMapper;
import com.example.cognito.mercado.Blog.model.Subreddit;
import com.example.cognito.mercado.Blog.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    @Autowired
    private SubredditRepository subredditRepository;
    @Autowired
    private SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
       Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto));
       subredditDto.setId(save.getId());
       return subredditDto;
    }



    @Transactional
    public List<SubredditDto> getAll(){
       return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }


    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new BlogException("no subreddit found"));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
