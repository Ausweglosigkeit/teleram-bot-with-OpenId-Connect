package com.ausweglosigkeit.loader;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeCallback;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;

import java.io.File;
import java.util.ResourceBundle;

public class Loader {
    private static final ResourceBundle BOT_DATA = ResourceBundle.getBundle("com.ausweglosigkeit.oidc.RegistrationData");

    private String videoID;

    public Loader(String videoID, String id) {
        downloadVideoFile(videoID, id);
    }

    private void downloadVideoFile(String videoID, String id) {
        YoutubeDownloader downloader = new YoutubeDownloader();

        RequestVideoInfo request = new RequestVideoInfo(videoID)
                .callback(new YoutubeCallback<VideoInfo>() {
                    @Override
                    public void onFinished(VideoInfo videoInfo) {
                        System.out.println("Finished parsing");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Error: " + throwable.getMessage());
                    }
                })
                .async();
        Response<VideoInfo> resp = downloader.getVideoInfo(request);
        VideoInfo video = resp.data();
        System.out.println(resp.status());
        VideoDetails details = video.details();

        Format format = video.bestVideoWithAudioFormat();

        File outDir = new File(BOT_DATA.getString("loader.path.videofile"));
        RequestVideoFileDownload request2 = new RequestVideoFileDownload(format)
                .saveTo(outDir)
                .renameTo(id)
                .overwriteIfExists(true)
                .callback(new YoutubeProgressCallback<File>() {
                    @Override
                    public void onDownloading(int progress) {
                        System.out.printf("Downloaded %d%%\n", progress);
                    }

                    @Override
                    public void onFinished(File videoInfo) {
                        System.out.println("Finished file: " + videoInfo);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Error: " + throwable.getLocalizedMessage());
                    }
                })
                .async();
        Response<File> response2 = downloader.downloadVideoFile(request2);
        File data2 = response2.data();
    }

    public void setVideoID(String videoID){
        this.videoID = videoID;
    }
}
