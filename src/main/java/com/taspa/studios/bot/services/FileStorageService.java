package com.taspa.studios.bot.services;

import com.taspa.studios.bot.s3.S3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class FileStorageService {

	private final S3Client s3Client;
	private final S3Properties s3Properties;

	public void upload(String key, byte[] content) {
		PutObjectRequest request = PutObjectRequest.builder()
				.key(key)
				.bucket(s3Properties.getBucket())
				.acl(ObjectCannedACL.PRIVATE)
				.build();

		s3Client.putObject(
				request,
				RequestBody.fromBytes(content)
		);
	}

	public byte[] download(String key) {
		GetObjectRequest request = GetObjectRequest.builder()
				.key(key)
				.bucket(s3Properties.getBucket())
				.build();

		return s3Client.getObjectAsBytes(request).asByteArray();
	}

}
