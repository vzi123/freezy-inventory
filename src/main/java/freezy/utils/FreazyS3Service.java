package freezy.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;

@Service
public class FreazyS3Service {

    private final S3Client s3Client;
    private final S3Presigner presigner;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${s3.accessKeyId}")
    private String accessKey;

    @Value("${s3.secretKey}")
    private String secretKey;

    public FreazyS3Service() {
        // Initialize the S3 client with credentials and region
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create("AKIAQEFWAJOAZTP3KWSO", "272miwFkNGyqnIkviylgZ731vxV3XC74x6stPCmL");
        this.s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.EU_NORTH_1) // Set your desired region
                .build();

        // Initialize S3 presigner for generating presigned URLs
        this.presigner = S3Presigner.builder()
                .region(Region.EU_NORTH_1) // Set your desired region
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getName();

        // Upload the file to S3
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // Generate a presigned URL for the uploaded file
        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60))
                .getObjectRequest(req -> req.bucket(bucketName).key(fileName))
                .build();

        URL url = presigner.presignGetObject(getObjectPresignRequest).url();
        return url.toString();
    }
}

