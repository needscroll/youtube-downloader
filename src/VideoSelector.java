import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VideoSelector {

    Scraper scraper = new Scraper();
    ArrayList<Stream> streams;
    Map<String, String> itagVideoReference = new HashMap<>();
    Map<String, String> itagAudioMp4Reference = new HashMap<>();
    Map<String, String> itagAudioWebmReference = new HashMap<>();

    public VideoSelector(String videoID)
    {
        initAudioItagReferences();
        initVideoItagReferences();

        streams = scraper.scrape(videoID);
        if (streams.size() == 0)
        {
            System.err.println("There are no detected streams");
        }
    }

    public String getURL(String itag)
    {
        for (int i = 0; i < streams.size(); i++)
        {
            Stream stream = streams.get(i);
            if (stream.getItag() == itag)
            {
                return stream.generateVideoURL();
            }
        }

        return "error value";
    }

    public boolean hasStreams()
    {
        return !streams.isEmpty();
    }

    public Stream getHighestVideo()
    {
        Stream highest = null;

        if (hasStreams())
        {
            highest = streams.get(1);

            for (int i = 0; i < streams.size(); i++)
            {
                Stream stream = streams.get(i);
                String key = keyFromVideoItag(itagVideoReference, stream.getItag());

                if (key != null)
                {
                    int rankOrder = Integer.valueOf(key);
                    int currentRankOrder = highest.getItagValue();
                    if (rankOrder < currentRankOrder)
                    {
                        highest = stream;
                    }
                }
            }
        }

        return highest;
    }

    public Stream getHighestAudio()
    {
        //for some reason there doesn't seem to be a difference between the audio streams
        if (hasStreams())
        {
            for (Stream s : streams)
            {
                if (itagAudioMp4Reference.get(s.getItag()) != null)
                {
                    return s;
                }
            }
        }
        return null;
    }

    public static <K, V> K keyFromVideoItag(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void listItags()
    {
        for(Stream s : streams)
        {
            System.out.println(s.getItag());
        }
    }

    public void initVideoItagReferences()
    {
        itagVideoReference.put("138","4320:mp4");
        itagVideoReference.put("266","2160:mp4");
        itagVideoReference.put("264","1440:mp4");
        itagVideoReference.put("299","1080:mp4");
        itagVideoReference.put("217","1080:mp4");
        itagVideoReference.put("216","1080:mp4");
        itagVideoReference.put("137","1080:mp4");
        itagVideoReference.put("214","720:mp4");
        itagVideoReference.put("215","720:mp4");
        itagVideoReference.put("298","720:mp4");
        itagVideoReference.put("136","720:mp4");
        itagVideoReference.put("135","480:mp4");
        itagVideoReference.put("212","480:mp4");
        itagVideoReference.put("213","480:mp4");
        itagVideoReference.put("134","360:mp4");
        itagVideoReference.put("133","240:mp4");
        itagVideoReference.put("160","144:mp4");
    }

    public void initAudioItagReferences()
    {
        itagAudioMp4Reference.put("139","mp4");
        itagAudioMp4Reference.put("140","mp4");
        itagAudioMp4Reference.put("141","mp4");
        itagAudioMp4Reference.put("256","mp4");
        itagAudioMp4Reference.put("258","mp4");
        itagAudioMp4Reference.put("325","mp4");
        itagAudioMp4Reference.put("328","mp4");

        itagAudioWebmReference.put("171","webm");
        itagAudioWebmReference.put("172","webm");
        itagAudioWebmReference.put("249","webm");
        itagAudioWebmReference.put("250","webm");
        itagAudioWebmReference.put("251","webm");
    }


}
