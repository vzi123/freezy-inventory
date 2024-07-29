package freezy.utils;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class DropboxService {

    @Autowired
    private DbxClientV2 dropboxClient;

    public String uploadFile(MultipartFile file, String name) throws IOException {
        try{
            InputStream in = file.getInputStream();

            // Upload file to Dropbox
            FileMetadata metadata = dropboxClient.files().uploadBuilder("/freazy/dc/" + file.getName() + ".pdf")
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(in);

            System.out.println("File uploaded successfully: " + metadata.getPathLower());

            // Check if a shared link already exists
            List<SharedLinkMetadata> sharedLinks = dropboxClient.sharing().listSharedLinksBuilder()
                    .withPath(metadata.getPathLower())
                    .withDirectOnly(true)
                    .start()
                    .getLinks();

            if (sharedLinks != null && !sharedLinks.isEmpty()) {
                // Return the existing shared link
                return (sharedLinks.get(0).getUrl());
            } else {
                // Create a shared link for the file
                SharedLinkMetadata sharedLinkMetadata = dropboxClient.sharing().createSharedLinkWithSettings(metadata.getPathLower(), SharedLinkSettings.newBuilder().build());
                return (sharedLinkMetadata.getUrl());
            }
        } catch (UploadErrorException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private String convertToDirectDownloadLink(String sharedLink) {
//        return sharedLink.replace("www.dropbox.com", "dl.dropboxusercontent.com").replace("?dl=0", "").split("\\?")[0];
//    }
}

