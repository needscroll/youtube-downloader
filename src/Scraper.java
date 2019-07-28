import java.net.URLDecoder;
import java.util.ArrayList;

public class Scraper {

    String currdir = System.getProperty("user.dir");
    String ytmetadatabase = "https://www.youtube.com/get_video_info?video_id=";
    String ytembedbase = "https://www.youtube.com/embed/";

    Downloader downloader = new Downloader();
    UserIO io = new UserIO();

    //need to extract / parse metadata to find the stream to download from
    //might also need to get the itag to get stream properties
    //download the stream with the itag properties
    public ArrayList<Stream> scrape(String id)
    {
        String[] rawMetadata = scrapeRawMetadata(id);
        String[] finalMetadata = formatRawMetadata(rawMetadata);
        String[] embeddedMetadata = scrapeRawEmbeddedMetadata(id); //may be needed for use in decoding

        ArrayList<String> streams = new ArrayList<>();
        ArrayList<Stream> streamobjects = new ArrayList<>();

        for (String line : finalMetadata)
        {
            if (line.contains("itag"))
            {
                streams.add(line);
            }
        }

        streams.forEach((a)-> streamobjects.add(new Stream(a)));
        //streamobjects.forEach((a) -> System.out.println(a.generateVideoURL()));

        return streamobjects;
    }

    public String[] scrapeRawEmbeddedMetadata(String id)
    {
        String videoEmbeddedMetadata = ytembedbase + id;
        downloader.fileDownload(videoEmbeddedMetadata, currdir, "embedded metadata");
        String[] embeddedMetadata = io.get_file_contents("embedded metadata");
        return embeddedMetadata;
    }

    /*
    grabs the raw metadata, does not format, returns as one single large line
     */
    public String[] scrapeRawMetadata(String id)
    {
        String videometadata = ytmetadatabase + id;

        //downloads the raw video metadata
        downloader.fileDownload(videometadata, currdir, "raw metadata");

        //scrapes the metadata and writes it out to final metadata file
        String[] metadata = io.get_file_contents("raw metadata");

        return metadata;
    }

    /*
    formats raw metadata, writes one pass to file along with final data, returns final data
     */
    public String[] formatRawMetadata(String[] metadata)
    {
        ArrayList<String> finaldata = new ArrayList<>();
        ArrayList<String> finaldataonepass = new ArrayList<>();

        String [] splitmetadata = metadata[0].split("&");
        for (String s : splitmetadata)
        {
            String decodedmetadata = URLDecoder.decode(s);
            String[] splitdecoded = decodedmetadata.split(",");
            for (String s1 : splitdecoded)
            {
                String furtherdecoded1 = URLDecoder.decode(s1);
                String furtherdecoded2 = URLDecoder.decode(furtherdecoded1);

                finaldataonepass.add(s1);
                finaldata.add(furtherdecoded2);
            }
        }
        io.forceWriteFile(finaldataonepass.toArray(new String[1]), "final metadata one pass");
        io.forceWriteFile(finaldata.toArray(new String[1]), "final metadata");

        return finaldata.toArray(new String[1]);
    }
}
