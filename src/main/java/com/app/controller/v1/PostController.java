package com.app.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.PostDto;
import com.app.dto.Response;
import com.app.service.PostService;

@RestController
@RequestMapping(path = RequestPath.POST)
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping("/{id}")
	public ResponseEntity<Response> getPostById(@PathVariable Long id) {
		return ResponseEntity.ok(Response.builder().data(postService.getPostById(id)).build());
	}

	@GetMapping
	public ResponseEntity<Response> getAllPosts() {
		return ResponseEntity.ok(Response.builder().data(postService.getAllPosts()).build());
	}

	@PostMapping
	public ResponseEntity<Response> addPost(@RequestBody PostDto postDto) {
		return ResponseEntity.ok(Response.builder().data(postService.addPost(postDto)).build());
	}

	@PutMapping
	public ResponseEntity<Response> updatePost(@RequestBody PostDto postDto) {
		Response response = Response.builder().data("Post updated").build();
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deletePostById(@PathVariable Long id) {
		postService.deletePostById(id);
		Response response = Response.builder().data("Post deleted").build();
		return ResponseEntity.ok(response);
	}

}