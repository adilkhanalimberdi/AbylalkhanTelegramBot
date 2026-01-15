package com.taspa.studios.bot.entities;

import com.taspa.studios.bot.enums.BotState;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "chat_id")
	private Long chatId;

	@Column(name = "state", nullable = false)
	@Enumerated(EnumType.STRING)
	private BotState state;

	@Column(name = "username")
	private String username;

	@Column(name = "is_admin")
	private boolean isAdmin;

	@Column(name = "instagram_username")
	private String instagramUsername;

	@CreatedDate
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(name = "last_modified")
	private LocalDateTime lastModified;

}
