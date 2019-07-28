import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Stream {

    String rawdata;
    String[] splitrawdata;
    Map<String, String> varmap = new HashMap<>();

    //contains list of variables in final url
    private final String[] videoVars = {"expire", "ei", "ip", "id", "itag", "aitags", "source", "requiressl",
    "mm", "mn", "ms", "mv", "mvi", "pl", "initcwndbps", "mime", "gir", "clen", "dur", "lmt", "mt",
    "fvip", "keepalive", "c", "txp", "sparams", "sig", "lsparams", "lsig", "init", "projection_type"};//, "type"};

    public Stream(String rawdata)
    {
        this.rawdata = rawdata;
        splitrawdata = rawdata.split("&");
        for (String s : splitrawdata)
        {
            int firstequal = s.indexOf("=");
            if (firstequal < s.length() - 1)
            {
                String variable = s.substring(0, firstequal);
                String value = s.substring(firstequal + 1, s.length());
                //System.out.println(variable);
                //System.out.println(value);
                varmap.put(variable, value);
            }
        }
    }

    public String generateVideoURL()
    {
        String base = varmap.get("url");
        String vars = "";

        for (String s : videoVars)
        {
            if (varmap.get(s) != null)
            {
                vars += "&";
                vars += s;
                vars += "=";
                vars += varmap.get(s);
            }
        }
        return "" + base + vars;
    }

    public String getItag()
    {
        return "" + varmap.get("itag");
    }

    public int getItagValue()
    {
        return Integer.valueOf(varmap.get("itag"));
    }

    public String getvalue(String key)
    {
        return "" + varmap.get(key);
    }

    public void printmap()
    {
        for (Map.Entry<String, String> entry : varmap.entrySet())
        {
            String variable = entry.getKey();
            String value = entry.getValue();
            System.out.println(variable + "=" + value);
        }
    }



         /* things needed to generate final url for video
    not all of these may be needed but it will work if all are included
    1. url (obviously)
    2. expire
    3. ei
    4. ip
    5. id
    6. itag
    7. aitags
    8. source
    9. requiressl
    10. mm
    11. mn
    12. ms
    13. mv
    14. mvi
    15. pl
    16. initcwndbps
    17. mine
    18. gir
    19. clen
    20. dur
    21. lmt
    22. mt
    23. fvip
    24. keepalive
    25. c
    26. txp
    27. sparms
    28. sig
    29. lsparams (that is a L and not a one)
    30. lsig (that's a L and not a one)
    31. init
    32. projection_type
    33. type */
}
