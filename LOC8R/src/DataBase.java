import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DataBase {
	private HashMap<String, ArrayList<Location>> locData = new HashMap<>();
	private HashMap<Integer, ArrayList<Location>> reviews = new HashMap<>();
	private ArrayList<Location> recent  = new ArrayList<>();
	private File user;
	private File data;
	public DataBase() {
		reviews.put(1, new ArrayList<Location>());
		reviews.put(2, new ArrayList<Location>());
		reviews.put(3, new ArrayList<Location>());
		reviews.put(4, new ArrayList<Location>());
		reviews.put(5, new ArrayList<Location>());
		 user = new File("user.txt");
		 data = new File("locations.txt");
		try {
			Scanner s = new Scanner(data);
			while(s.hasNextLine()) {
				String[] location = s.nextLine().split("\\t");
				Location tempLoc;
				if(location.length == 5) {
					tempLoc = new Location(location[0],location[1],location[2],Double.parseDouble(location[3]),Double.parseDouble(location[4]),0);
				}
				else {
					tempLoc = new Location(location[0],location[1],location[2],Double.parseDouble(location[3]),Double.parseDouble(location[4]),Integer.parseInt(location[5]));
				}
				if(locData.get(tempLoc.getType())==null) {
					locData.put(tempLoc.getType(), new ArrayList<Location>());
				}
				locData.get(tempLoc.getType()).add(tempLoc);
				if(tempLoc.getReview() !=0)
				reviews.get(tempLoc.getReview()).add(tempLoc);
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("No file found");
			e.printStackTrace();
		}
		try {
			Scanner s= new Scanner(user);
			while(s.hasNextLine()) {
				String[] location = s.nextLine().split("\\t");
				Location tempLoc = tempLoc = new Location(location[0],location[1],location[2],Double.parseDouble(location[3]),Double.parseDouble(location[4]),Integer.parseInt(location[5]));;
				recent.add(tempLoc);
			}
		} catch (FileNotFoundException e) {
			System.out.println("No File");
			e.printStackTrace();
		}
		
	}
	public HashMap<String, ArrayList<Location>> getLocData() {
		return locData;
	}
	public void setLocData(HashMap<String, ArrayList<Location>> locData) {
		this.locData = locData;
	}
	public HashMap<Integer, ArrayList<Location>> getReviews() {
		return reviews;
	}
	public void setReviews(HashMap<Integer, ArrayList<Location>> reviews) {
		this.reviews = reviews;
	}
	public ArrayList<Location> getRecent() {
		return recent;
	}
	public void setRecent(ArrayList<Location> recent) {
		this.recent = recent;
	}
	public void save() {
		try {
			PrintWriter out = new PrintWriter(user);
			for(Location l:recent) {
				out.println(l.toString());
			}
		} catch (FileNotFoundException e) {
			System.out.println("NO file found");
			e.printStackTrace();
		}
		try {
			PrintWriter out = new PrintWriter(data);
		for(String s: locData.keySet()) {
			for(Location l:locData.get(s)) {
				out.println(l.toString());
			}
		}
		
	} catch (FileNotFoundException e) {
		System.out.println("NO file found");
		e.printStackTrace();
	}
	}
	
}
