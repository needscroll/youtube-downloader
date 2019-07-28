import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class main {

    public static void main(String[] args)
    {
        Downloader downloader = new Downloader();

        String vid1 = "40dJS_LC6S8";
        vid1 = "40dJS_LC6S8";

        VideoSelector selector = new VideoSelector(vid1);
        //selector.listItags();

        Stream audioStream = selector.getHighestAudio();
        Stream videoStream = selector.getHighestVideo();

        System.out.println(audioStream.getItag());
        System.out.println(audioStream.generateVideoURL());
        System.out.println(videoStream.getItag());
        System.out.println(videoStream.generateVideoURL());


        downloader.fileDownload(audioStream.generateVideoURL(), "audio");
        downloader.fileDownload(videoStream.generateVideoURL(), "video.mp4");
    }
}
