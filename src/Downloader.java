import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Downloader {

    final static int size=262144;

    public static void fileDownload(String fAddress, String destinationDir, String fileName)
    {

        int slashIndex =fAddress.lastIndexOf('/');
        int periodIndex =fAddress.lastIndexOf('.');

        //String fileName=fAddress.substring(slashIndex + 1);

        if (periodIndex >=1 &&  slashIndex >= 0
                && slashIndex < fAddress.length()-1)
        {
            System.out.println("address being accessed: " + fAddress);
            //System.out.println("downloading as filename: " + fileName);
            fileUrl(fAddress,fileName,destinationDir);
        }
        else
        {
            System.err.println("path or file name.");
        }
    }

    public static void fileDownload(String fAddress, String fileName)
    {
        String destinationDir = System.getProperty("user.dir");
        int slashIndex =fAddress.lastIndexOf('/');
        int periodIndex =fAddress.lastIndexOf('.');

        //String fileName=fAddress.substring(slashIndex + 1);

        if (periodIndex >=1 &&  slashIndex >= 0
                && slashIndex < fAddress.length()-1)
        {
            System.out.println("address being accessed: " + fAddress);
            //System.out.println("downloading as filename: " + fileName);
            fileUrl(fAddress,fileName,destinationDir);
        }
        else
        {
            System.err.println("path or file name.");
        }
    }


    public static void
    fileUrl(String fAddress, String
            localFileName, String destinationDir) {
        OutputStream outStream = null;
        URLConnection uCon = null;

        InputStream is = null;
        try {
            URL Url;
            byte[] buf;
            int ByteRead,ByteWritten=0;
            Url= new URL(fAddress);
            outStream = new BufferedOutputStream(new
                    FileOutputStream(destinationDir+"\\"+localFileName));

            uCon = Url.openConnection();
            is = uCon.getInputStream();
            buf = new byte[size];
            while ((ByteRead = is.read(buf)) != -1) {
                outStream.write(buf, 0, ByteRead);
                ByteWritten += ByteRead;
            }
            System.out.println("Downloaded Successfully.");
            System.out.println
                    ("File name:\""+localFileName+ "\"\nNo ofbytes :" + ByteWritten);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
                outStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }}}
}
