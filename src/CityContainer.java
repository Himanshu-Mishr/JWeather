import java.util.List;


public class CityContainer {
	public Query query;
}

class Query {
	public int count;
	public String created;
	public String lang;
	public Result results;
}

class Result {
	public List<Place> place;
}

class Place {
	public String lang="";
	public String uri="";
	public String woeid="";
	public PlaceTypeName placeTypeName;
	public String name="";

	public Country country;
	public Country admin1;
	public Country admin2;
	public Country admin3;
	public Country locality1;
	public Country locality2;

	public Country postal;
	public axis centroid;
	public Box boundingBox;
	public String areaRank="";
	public String popRank="";
	public Country timezone;
}

class PlaceTypeName {
	public String code = "";
	public String content = "";
}

class Country {

	public String code = "";
	public String type = "";
	public String woeid = "";
	public String content = "";
}

class axis {

	public String latitude;
	public String longitude;
}

class Box {

	public axis southWest;
	public axis northEast;
}
