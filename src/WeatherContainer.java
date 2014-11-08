import java.util.List;

public class WeatherContainer {
	public WQuery query;
}


class WQuery {
    public int count = 1;
    public String created="2014-11-07T14:12:23Z";
    public String lang="en-US";
    public WResults results;
}


class WResults {
    public Channel channel;
}

class Channel {
    public String title="Yahoo! Weather - Kolkata, IN";
    public String link="http://us.rd.yahoo.com/dailynews/rss/weather/Kolkata__IN/*http://weather.yahoo.com/forecast/INXX0028_c.html";
    public String description="Yahoo! Weather for Kolkata, IN";
    public String language="en-us";
    public String lastBuildDate="Fri, 07 Nov 2014 3:01 pm IST";
    public String ttl="60";

    public Location location;
    public Units units;
    public Wind wind;
    public Atmosphere atmosphere;
    public Astronomy astronomy;
    public Image image;
    public Item item;
}

class Location {
    public String city="Kolkata";
    public String country="India";
    public String region="WB";
}

class Units {
    public String distance="km";
    public String pressure="mb";
    public String speed="km/h";
    public String temperature="C";
}

class Wind {
    public String chill="32";
    public String direction="360";
    public String speed="1.61";
}

class Atmosphere {
    public String humidity="45";
    public String pressure="1008.6";
    public String rising="0";
    public String visibility="7";
}

class Astronomy {
    public String sunrise="6:12 am";
    public String sunset="5:24 pm";
}

class Image {
    public String title="Yahoo! Weather";
    public String width="142";
    public String height="18";
    public String link="http://weather.yahoo.com";
    public String url="http://l.yimg.com/a/i/brand/purplelogo//uh/us/news-wea.gif";
}

class Item {
    public String title="Conditions for Kolkata, IN at 3:01 pm IST";
    public String lat="22.52";
    public String longitude="88.32";
    public String link="http://us.rd.yahoo.com/dailynews/rss/weather/Kolkata__IN/*http://weather.yahoo.com/forecast/INXX0028_c.html";
    public String pubDate="Fri, 07 Nov 2014 3:01 pm IST";

    public Condition condition;
    public String description="\n<img src=\"http://l.yimg.com/a/i/us/we/52/30.gif\"/><br />\n<b>Current Conditions:</b><br />\nPartly Cloudy, 32 C<BR />\n<BR /><b>Forecast:</b><BR />\nFri - Mostly Clear. High: 32 Low: 22<br />\nSat - Partly Cloudy. High: 33 Low: 23<br />\nSun - Partly Cloudy. High: 33 Low: 22<br />\nMon - Sunny. High: 33 Low: 22<br />\nTue - Partly Cloudy. High: 33 Low: 22<br />\n<br />\n<a href=\"http://us.rd.yahoo.com/dailynews/rss/weather/Kolkata__IN/*http://weather.yahoo.com/forecast/INXX0028_c.html\">Full Forecast at Yahoo! Weather</a><BR/><BR/>\n(provided by <a href=\"http://www.weather.com\" >The Weather Channel</a>)<br/>\n";
    public List<Forecast> forecast;
    public Guid guid;
}

class Condition {
    public String code="30";
    public String date="Fri, 07 Nov 2014 3:01 pm IST";
    public String temp="32";
    public String text="Sunny";
}

class Forecast {
    public String code="32";
    public String date="10 Nov 2014";
    public String day="Mon";
    public String high="33";
    public String low="22";
    public String text="Sunny";
}

class Guid {
    public String isPermaLink="false";
    public String content="INXX0028_2014_11_11_7_00_IST";
}






















