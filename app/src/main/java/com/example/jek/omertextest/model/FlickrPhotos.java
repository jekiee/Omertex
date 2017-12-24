package com.example.jek.omertextest.model;

public class FlickrPhotos {
    private FlickrPhoto photos;

    public FlickrPhoto getPhotos() {
        return photos;
    }

    @Override
    public String toString() {
        return "FlickrPhotos{" +
                "photos=" + photos +
                '}';
    }

    public class FlickrPhoto {
        private FlickrPhotoSettings[] photo;

        public FlickrPhotoSettings[] getPhoto() {
            return photo;
        }
    }

    public class FlickrPhotoSettings {
        private String id;
        private String secret;
        private long server;
        private long farm;

        public String getId() {
            return id;
        }

        public String getSecret() {
            return secret;
        }

        public long getServer() {
            return server;
        }

        public long getFarm() {
            return farm;
        }

        //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{o-secret}_n.jpg
        public String getUrl() {
            StringBuilder sb = new StringBuilder();
            sb.append("https://farm")
                    .append(String.valueOf(getFarm()))
                    .append(".staticflickr.com/")
                    .append(String.valueOf(getServer()))
                    .append("/")
                    .append(String.valueOf(getId()))
                    .append("_")
                    .append(secret)
                    .append("_z.jpg");
            return sb.toString();
        }
    }
}
