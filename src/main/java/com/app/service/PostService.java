package com.app.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.dto.PostDto;

@Service
public class PostService {

	private static String BASE_POST_URL = "https://jsonplaceholder.typicode.com/posts";

	public PostDto getPostById(Long id) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<PostDto> responseEntity = restTemplate.getForEntity(BASE_POST_URL + "/" + id, PostDto.class);
		return responseEntity.getBody();
	}

	public List<PostDto> getAllPosts() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> responseEntity = restTemplate.getForEntity(BASE_POST_URL, List.class);
		return responseEntity.getBody();
	}
	
	public PostDto addPost(PostDto postDto) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers=new HttpHeaders();
		headers.add("accept", "application/json");
		HttpEntity<PostDto> request = new HttpEntity<PostDto>(postDto); 
		ResponseEntity<PostDto> responseEntity = restTemplate.postForEntity(BASE_POST_URL, request, PostDto.class);
		return responseEntity.getBody();
	}
	
	public void updatePost(PostDto postDto) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<PostDto> request = new HttpEntity<PostDto>(postDto); 
		restTemplate.put(BASE_POST_URL, request, PostDto.class);
	}
	
	public void deletePostById(Long id) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(BASE_POST_URL+"/"+id, PostDto.class);
	}

}
