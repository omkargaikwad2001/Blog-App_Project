package com.blog.services;

import java.util.List;

import com.blog.entities.Post;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {

	// create
	PostDto createPost(PostDto postDto,Integer userId,Integer categorytId);

	// update
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get single post
	PostDto getPostById(Integer postId);

	// get all post
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy);

	// get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);

	// get all posts by user
	List<PostDto> getPostsByUser(Integer useryId);

}
