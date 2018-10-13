
public class Location {
	private String name;
	private String address;
	private String type;
	private double latitude;
	private double longitude;
	private int review;
	
	public Location(String name , String address, String type, double latitude, double longitude, int review) {
		this.name = name;
		this.address = address;
		this.type = type;
		this.latitude = latitude;
		this.longitude = longitude;
		this.review = review;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getReview() {
		return review;
	}

	public void setReview(int review) {
		this.review = review;
	}
	public String toString() {
		return name+"\t"+address+"\t"+type+"\t"+latitude+"\t"+longitude+"\t"+review;
	}
}
