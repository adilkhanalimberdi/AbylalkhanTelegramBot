package com.taspa.studios.bot.repositories;

import com.taspa.studios.bot.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
