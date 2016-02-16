package com.baidu.vmonitor.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

public class MultipartHttpServletRequest {

    private static final String CONTENT_DISPOSITION = "Content-Disposition";

    private static final String FILENAME_KEY = "filename=";

    /**
     * Constant for HTTP POST method.
     */
    private static final String POST_METHOD = "POST";

    /**
     * Part of HTTP content type header.
     */
    private static final String MULTIPART = "multipart/";

    private MultipartHttpServletRequest() {
        super();
    }

    public static List<MultipartFile> getMultipartFiles(HttpServletRequest request) throws Exception {
        List<MultipartFile> multipartFiles;
        try {
            Collection<Part> parts = request.getParts();
            multipartFiles = new ArrayList<MultipartFile>(parts.size());
            for (Part part : parts) {
                String filename = extractFilename(part.getHeader(CONTENT_DISPOSITION));
                if (filename != null) {
                    multipartFiles.add(new MultipartFile(part, filename));
                }
            }
        } catch (Exception ex) {
            throw new Exception("Could not parse multipart servlet request", ex);
        }
        return multipartFiles;
    }

    public static boolean isMultipart(HttpServletRequest request) {
        // Same check as in Commons FileUpload...
        if (!POST_METHOD.equalsIgnoreCase(request.getMethod())) {
            return false;
        }
        String contentType = request.getContentType();
        return (contentType != null && contentType.toLowerCase().startsWith(MULTIPART));
    }

    private static String extractFilename(String contentDisposition) {
        if (contentDisposition == null) {
            return null;
        }
        // TODO: can only handle the typical case at the moment
        int startIndex = contentDisposition.indexOf(FILENAME_KEY);
        if (startIndex == -1) {
            return null;
        }
        String filename = contentDisposition.substring(startIndex + FILENAME_KEY.length());
        if (filename.startsWith("\"")) {
            int endIndex = filename.indexOf("\"", 1);
            if (endIndex != -1) {
                return filename.substring(1, endIndex);
            }
        } else {
            int endIndex = filename.indexOf(";");
            if (endIndex != -1) {
                return filename.substring(0, endIndex);
            }
        }
        return filename;
    }

    /**
     * Spring MultipartFile adapter, wrapping a Servlet 3.0 Part object.
     */
    public static class MultipartFile {

        private String name;
        private String originalFilename;
        private String contentType;
        private long size;

        public MultipartFile(Part part, String filename) {
            this.name = part.getName();
            this.originalFilename = filename;
            this.contentType = part.getContentType();
            this.size = part.getSize();
        }
        
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOriginalFilename() {
            return originalFilename;
        }

        public void setOriginalFilename(String originalFilename) {
            this.originalFilename = originalFilename;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "MultipartFile [name=" + name + ", originalFilename=" + originalFilename + ", contentType="
                            + contentType + ", size=" + size + "]";
        }

//        public byte[] getBytes() throws IOException {
//            return FileCopyUtils.copyToByteArray(this.part.getInputStream());
//        }
//
//        public InputStream getInputStream() throws IOException {
//            return this.part.getInputStream();
//        }
//
//        public void transferTo(File dest) throws IOException, IllegalStateException {
//            this.part.write(dest.getPath());
//        }
    }

}
