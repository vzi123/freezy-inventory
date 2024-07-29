package freezy.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;

public class FreazyMultipartFile implements MultipartFile {

    private byte[] input;
    private String name;

    public FreazyMultipartFile(byte[] dcFile, String name) {
        this.input = dcFile;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return input == null || input.length == 0;
    }

    @Override
    public long getSize() {
        return input.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return input;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(input);
    }

    @Override
    public Resource getResource() {
        return MultipartFile.super.getResource();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileUtils.writeByteArrayToFile(dest, input);
    }
}
