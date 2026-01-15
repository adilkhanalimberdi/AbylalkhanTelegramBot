package com.taspa.studios.bot.services;

import com.taspa.studios.bot.entities.Post;
import com.taspa.studios.bot.enums.PostState;
import com.taspa.studios.bot.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	public List<Post> getAllPostsByAuthorId(Long authorId) {
		return postRepository.findAll()
				.stream()
				.filter(post -> post.getAuthorId().equals(authorId))
				.toList();
	}

	public Post getPost(Long postId) {
		return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
	}

	public void createPost(Long authorId, String title, String content) {
		Post post = Post.builder()
				.title(title)
				.content(content)
				.state(PostState.DRAFT)
				.authorId(authorId)
				.build();

		postRepository.save(post);
	}

	public void updatePost(Long postId, Post post) {
		post.setId(postId);
		postRepository.save(post);
	}

	public void deletePost(Long postId) {
		postRepository.deleteById(postId);
	}

}
