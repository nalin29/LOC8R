import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FrontEnd extends Application {

	ObservableList<String> items = FXCollections.observableArrayList ();
	ArrayList<String> temp = new ArrayList<>();
	static double lat;
	static double longi;
	static String add; 
	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
				
	//Loading Screen_________________________________________________________________________
		DataBase data = new DataBase();
		stage.setTitle("Loading");
		BorderPane loadingBar = new BorderPane();
		   ProgressBar progress = new ProgressBar();
		   VBox bar = new VBox();
		   bar.getChildren().addAll(progress);
		   loadingBar.setBottom(bar);
		   bar.setPadding(new Insets(30));
		   bar.setAlignment(Pos.CENTER);
		   Scene loadingScreen = new Scene(loadingBar,906,515);
		   stage.setScene(loadingScreen);
		   String bip = "music.mp3";
		   Media hit = new Media(new File(bip).toURI().toString());
		   MediaPlayer mediaPlayer = new MediaPlayer(hit);
		   mediaPlayer.play();
		   Image image = new Image("loading.png");
		   //Image image = new Image("loading.png");
			 // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
			
		   BackgroundSize backgroundSize = new BackgroundSize(906, 515, true, true, true, false);
			
			 // new BackgroundImage(image, repeatX, repeatY, position, size)
			 BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
			 // new Background(images...)
			 Background background = new Background(backgroundImage);
		   loadingBar.setBackground(background);
		   stage.setResizable(true);
		   stage.show();
		   
	//HomePage_____________________________________________________________________________________
		  
		   BorderPane homeScreen = new BorderPane();
		  Image logo= new Image("Loc8r_black.png");
		   ImageView logoView = new ImageView();
		   logoView.setImage(logo);
		 
		   homeScreen.setTop(logoView);
		   GridPane homeGrid = new GridPane();
		   
		   ComboBox tag = new ComboBox();
		   tag.getItems().add("Type"); 
		   Set<String> keys = data.getLocData().keySet();
		   for(String s: keys) {
			   tag.getItems().add(s);
		   }
		   
		   Button search = new Button("LOC8");
		   search.setDisable(true);
		   search.setAlignment(Pos.CENTER);
		   Button reviewed = new Button("Reviewed");
		   reviewed.setAlignment(Pos.CENTER);
		   Button recents = new Button("Recents");
		   recents.setAlignment(Pos.CENTER);
		   homeGrid.add(tag, 0, 0);
		   homeGrid.add(search, 1,0);
		   homeGrid.add(reviewed, 1,1);
		   homeGrid.add(recents, 1,2);
		   homeGrid.setHgap(20);
		   homeGrid.setVgap(20);
		   homeGrid.setAlignment(Pos.CENTER);
		   homeScreen.setCenter(homeGrid);
		   Scene home = new Scene(homeScreen, 600,600 );
		   //stage.setScene(home);
	//Address Scene___________________________________________________________________________________
		   BorderPane address =  new BorderPane();
		   ImageView logoView2 = new ImageView();
		   logoView2.setImage(logo);
		   logoView2.setTranslateX(135);
		   address.setTop(logoView2);
		   GridPane info = new GridPane();
		   TextField Address =  new TextField("Enter Address Here");
		   Label label = new Label("Address: ");
		   info.add(Address, 1, 0);
		   info.add(label, 0, 0);
		   info.setVgap(10);
		   info.setAlignment(Pos.CENTER);
		   address.setCenter(info);
		   HBox buttons = new HBox();
		   Button next = new Button("Enter");
		   next.setPadding(new Insets(20));
		   Button back = new Button("Return");
		   back.setPadding(new Insets(20));
		   buttons.getChildren().addAll(back, next);
		   buttons.setAlignment(Pos.CENTER);
		   address.setBottom(buttons);
		   Scene AdressScene = new Scene(address, 600,600);
	//List Scene for Locations________________________________________________________________________
		   BorderPane lists = new BorderPane();
		   ListView<String> locations = new ListView<String>();
		   locations.setItems(items);
		   lists.setLeft(locations);
		   TextArea locationData = new TextArea();
		   locationData.setEditable(false);
		   lists.setRight(locationData);
		Scene list = new Scene(lists, 600,600);
	//Reviewed Scene
		BorderPane reviews = new BorderPane();
		VBox but = new VBox();
	//Actions______________________________________________________________________________________
		    Timeline timeline = new Timeline(
			        new KeyFrame(Duration.ZERO, new KeyValue(progress.progressProperty(), 0)),
			        new KeyFrame(Duration.seconds(5), e-> {
			            // do anything you need here on completion...
			            System.out.println("loadi over");
			            stage.setScene(home);
			            mediaPlayer.stop();
			        }, new KeyValue(progress.progressProperty(), 1))    
			    );
			    timeline.setCycleCount(1);
			   timeline.play();
			   // stage.setScene(AdressScene);
	            //searching
	            search.setOnAction( e-> {
	        	stage.setScene(AdressScene);
	        });
	            
	        //tag ComboBox
	        tag.setOnAction(e ->{
	        	if(!tag.getValue().equals("Type"))
	        			search.setDisable(false);
	        });
	        
	        
	        next.setOnAction(e ->{
	        	try {
	        		add = Address.getText();
					location(Address.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	TreeMap<Double ,ArrayList<Location>> sorted = new TreeMap<>();
	        	HashMap<String, ArrayList<Location>> locData = data.getLocData();
	        	for(int i =0; i<data.getLocData().get(tag.getValue()).size();i++){
	        		double value = score(data.getLocData().get(tag.getValue()).get(i),dist(data.getLocData().get(tag.getValue()).get(i)));
	        		if(sorted.containsKey(value))
	        			sorted.get(value).add((data.getLocData().get(tag.getValue()).get(i)));
	        		else {
	        			sorted.put(value, new ArrayList<Location>());
	        			sorted.get(value).add(data.getLocData().get(tag.getValue()).get(i));
	        		}
	        	}
	        	int i=0;
	        	for(double v: sorted.keySet()) {
	        		for(Location l : sorted.get(v)) {
	        			temp.add(l.getName());
	        				i++;
	        				if(i==8)
	        					break;
	        		}
	        		if(i == 8)
	        			break;
	        	}
	        	System.out.println(temp);
	        	items =FXCollections.observableArrayList (temp);
	        	locations.setItems(items);
	        	stage.setScene(list);
	        	
	        });
	        locations.addEventHandler(MouseEvent.MOUSE_PRESSED,e ->{
	        	int i = locations.getSelectionModel().getSelectedIndex();
	        	for(Location l: data.getLocData().get(tag.getValue()))
	        		if(l.getName().equals(items.get(i)))
	        			locationData.setText("Name: "+l.getName()+"\n"+"Type: "+l.getType()+"\nYour Review: "+l.getReview()+"\nLongitude: "+l.getLongitude()+"\nLatitude: "+l.getLatitude()+"\nDistance: "+dist(l)+"\nDirections: "+"https://www.google.com/maps/dir/add/"+l.getAddress()+"\n Yelp Reviews: "+"https://www.yelp.com/biz/"+l.getName().replaceAll("\\s","-")+"-austin-crossroads-austin?osq="+l.getAddress());
	        });
	        locations.addEventHandler(KeyEvent.KEY_PRESSED,e ->{
	        	if(e.getCode().equals(KeyCode.DOWN) ||e.getCode().equals(KeyCode.UP) ) {
					int s = locations.getSelectionModel().getSelectedIndex();
				if(s <0)
					return;
	        	for(Location l: data.getLocData().get(tag.getValue()))
	        		if(l.getName().equals(items.get(s)))
	        			locationData.setText("Name: "+l.getName()+"\n"+"Type: "+l.getType()+"\nYour Review: "+l.getReview()+"\nLongitude: "+l.getLongitude()+"\nLatitude: "+l.getLatitude()+"\nDistance: "+dist(l)+"\nDirections: "+"https://www.google.com/maps/dir/add/"+l.getAddress()+"\n Yelp Reviews: "+"https://www.yelp.com/biz/"+l.getName().replaceAll("\\s","-")+"-austin-crossroads-austin?osq="+l.getAddress());
	        	}
	        	});
	        
	}
	public static double dist( Location l ) {
		return Math.sqrt(Math.pow(lat-l.getLatitude(), 2)+Math.pow(longi-l.getLongitude(), 2));
	}
	public static double score(Location l, double dist) {
		if(l.getReview() ==0)
			return 1+dist;
		if(l.getReview() <=3)
			return 1+dist*l.getReview();
		return 1+dist*1/Math.sqrt(l.getReview());
	}
	public static void location(String add) throws Exception {
		//add = "11608 spicewood pkwy";
		String address = add.replaceAll("\\s", "%");
		URL url = new URL("http://www.mapquestapi.com/geocoding/v1/address?key=AAAUwZvzc7sPNu0bKvipXlFG8p6g4adI&location=" +address);		
        BufferedReader br = null;
        
        try {

            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {

                sb.append(line);            }
          //  System.out.println(sb.toString());
        	JSONObject obj = new JSONObject(sb.toString());
        	JSONArray res = obj.getJSONArray("results");
        	//System.out.println(res.toString());
        	JSONArray loc = res.getJSONObject(0).getJSONArray("locations");
        	//System.out.println(loc.toString());
        	JSONObject latLng = loc.getJSONObject(0);
        	//System.out.println(latLng.toString());
        	lat = latLng.getJSONObject("latLng").getDouble("lat");
        	longi = latLng.getJSONObject("latLng").getDouble("lng");
        	System.out.println("latitude: "+ lat);
        	System.out.println("Longitude: "+longi);
        } finally {

            if (br != null) {
                br.close();
            }
        }
		
			  
		  }

	}
