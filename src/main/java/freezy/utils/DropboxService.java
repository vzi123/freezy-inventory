package freezy.utils;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class DropboxService {

    @Autowired
    private DbxClientV2 dropboxClient;

    public FileMetadata uploadFile(MultipartFile file, String path) throws IOException {
        try (InputStream in = file.getInputStream()) {
            return dropboxClient.files().uploadBuilder(path)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(in);
        } catch (UploadErrorException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return null;
    }
}

