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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FrontEnd extends Application {

	ObservableList<String> items = FXCollections.observableArrayList ();
	ArrayList<String> temp = new ArrayList<>();
	static double lat;
	static double longi;
	static String add;
	static int tempRev;
	static DataBase data = null;
	Scene lastScene;
	Location tempLocation;
	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		//Error Scene_________________________________________________________________________________
		Stage popupwindow=new Stage();
	      
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Error pop up window");
		      
		      
		Label label1= new Label("An error was detected");
		      
		     
		Button button1= new Button("Close Application");
		     
		     
		button1.setOnAction(e ->{ popupwindow.close();
		stage.close();
		});
		     
		     

		VBox layout= new VBox(10);
		     
		      
		layout.getChildren().addAll(label1, button1);
		      
		layout.setAlignment(Pos.CENTER);
		      
		Scene scene1= new Scene(layout, 300, 250);
		      
		popupwindow.setScene(scene1);
		popupwindow.hide();
	
		//Loading Screen_________________________________________________________________________
		try{data = new DataBase();}
		catch (Exception e1) { popupwindow.showAndWait();}
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
		   Image image = null;
		  try {image = new Image("loading.png");}
		  catch (Exception e1) { popupwindow.showAndWait();}
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
		  MenuBar menu = new MenuBar();
		   Menu file = new Menu("File");
			MenuItem save = new MenuItem("save");
			file.getItems().add(save);
			menu.getMenus().add(file);
			homeScreen.setTop(menu);
			
		   Image logo = null;
		  try{logo= new Image("Loc8r_black.png");}
		  catch (Exception e1) { popupwindow.showAndWait();}
		   ImageView logoView = new ImageView();
		   logoView.setImage(logo);
		   VBox logoMenu = new VBox();
		   logoMenu.setAlignment(Pos.CENTER);
		   logoMenu.getChildren().addAll(menu,logoView);
		   homeScreen.setTop(logoMenu);
		   GridPane homeGrid = new GridPane();
		   
		   ComboBox tag = new ComboBox();
		   tag.getItems().add("Type"); 
		   HashMap<String, ArrayList<Location>> loc = data.getLocData();
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
		   VBox menuLogo2 = new VBox();
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
		   HBox buttonsList = new HBox();
		   Button backwards = new Button("Back");
		   Button enter = new Button("Go");
		   buttonsList.getChildren().addAll(backwards, enter);
		   buttonsList.setSpacing(40);
		   backwards.setAlignment(Pos.CENTER);
		   enter.setAlignment(Pos.CENTER);
		   buttonsList.setAlignment(Pos.CENTER);
		   lists.setBottom(buttonsList);
		   enter.setDisable(true);
		Scene list = new Scene(lists, 700,700);
	//Single Location Screen_________________________________________________________________________
		BorderPane location = new BorderPane();
		TextArea information = new TextArea();
		Label titleLoc = new Label();
		titleLoc.setAlignment(Pos.CENTER);
		Label rating = new Label("Please Rate");
		VBox reviewBox = new VBox();
		reviewBox.setAlignment(Pos.CENTER);
		Button Lstar1 = new Button();
		try{Lstar1.setGraphic(new ImageView(new Image("regular_1.png")));}
		catch (Exception e1) { popupwindow.showAndWait();}
		Button Lstar2 = new Button();
		reviewBox.setSpacing(20);
		try{Lstar2.setGraphic(new ImageView(new Image("regular_2.png")));}
		catch (Exception e1) { popupwindow.showAndWait();}
		Button Lstar3 = new Button();
		try{Lstar3.setGraphic(new ImageView(new Image("regular_3.png")));}
		catch (Exception e1) { popupwindow.showAndWait();}
		Button Lstar4 = new Button();
		try{Lstar4.setGraphic(new ImageView(new Image("regular_4.png")));}
		catch (Exception e1) { popupwindow.showAndWait();}
		Button Lstar5 = new Button();
		try{Lstar5.setGraphic(new ImageView(new Image("regular_5.png")));}
		catch (Exception e1) { popupwindow.showAndWait();}
		Lstar1.setAlignment(Pos.CENTER);
		Lstar2.setAlignment(Pos.CENTER);
		Lstar3.setAlignment(Pos.CENTER);
		Lstar4.setAlignment(Pos.CENTER);
		Lstar5.setAlignment(Pos.CENTER);
		reviewBox.getChildren().addAll(Lstar1,Lstar2,Lstar3,Lstar4,Lstar5);
		reviewBox.setAlignment(Pos.CENTER);
		reviewBox.setPadding(new Insets(100));
		location.setTop(titleLoc);
		location.setLeft(information);
		location.setRight(reviewBox);
		Button exit = new Button("exit");
		VBox Button = new VBox();
		Button.getChildren().add(exit);
		Button.setSpacing(20);
		Button.setAlignment(Pos.CENTER);
		exit.setAlignment(Pos.CENTER);
		location.setBottom(Button);
		Scene locationOne = new Scene(location,800,800);
	//Reviewed Scene___________________________________________________________________________________
		BorderPane reviews = new BorderPane();
		VBox but = new VBox();
		Button star1 = new Button();
		try{star1.setGraphic(new ImageView(new Image("regular_1.png")));}
		catch (Exception e1) { popupwindow.showAndWait();}
		Button star2 = new Button();
		but.setSpacing(20);
		try{star2.setGraphic(new ImageView(new Image("regular_2.png")));}
		catch (Exception e1) { popupwindow.showAndWait();}
		Button star3 = new Button();
		try{star3.setGraphic(new ImageView(new Image("regular_3.png")));}
		catch (Exception e1) { popupwindow.showAndWait();}
		Button star4 = new Button();
		try{star4.setGraphic(new ImageView(new Image("regular_4.png")));}
		catch (Exception e1) { popupwindow.showAndWait();}
		Button star5 = new Button();
		try{star5.setGraphic(new ImageView(new Image("regular_5.png")));}
		catch (Exception e1) { popupwindow.showAndWait();}
		Label rev = new Label("Select type of review");
		but.setAlignment(Pos.CENTER);
		rev.setAlignment(Pos.CENTER);
		reviews.setTop(rev);
		but.getChildren().addAll(star1, star2,star3,star4,star5);
		reviews.setCenter(but);
		Button backs = new Button("Back");
		backs.setAlignment(Pos.CENTER);
		reviews.setBottom(backs);
		Scene reviewScene = new Scene(reviews, 300,300);

		
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
			   
	      //Home Actions____________________________________________________________________________________________
			   //LOC8R Button
			   search.setOnAction( e-> {
	        	stage.setScene(AdressScene);
	        	lastScene = home;
	        });
	            
	        //tag ComboBox
	        tag.setOnAction(e ->{
	        	if(!tag.getValue().equals("Type"))
	        			search.setDisable(false);
	        });
	        
	        reviewed.setOnAction(e->{
	        	stage.setScene(reviewScene);
	        });
	        recents.setOnAction(e ->{
	        	temp.clear();
	        	for(Location l : data.getRecent())
	        		temp.add(l.getName());
	        	items =FXCollections.observableArrayList (temp);
	        	locations.setItems(items);
	        	 locationData.clear();
	        	stage.setScene(list);
	        });
	        save.setOnAction(e ->{
	        	data.save();
	        });
	        //Address Actions_________________________________________________________________
	        
	        //Next Button
	        next.setOnAction(e ->{
	        	try {
	        		add = Address.getText();
					location(Address.getText());
				if(lastScene.equals(home)) {
	        	temp.clear();
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
	        	}
	        	else if(lastScene.equals(reviewScene)) {
	        		temp.clear();
	        		TreeMap<Double ,ArrayList<Location>> sorted = new TreeMap<>();
	        		HashMap<Integer, ArrayList<Location>> reviewedList  = data.getReviews();
	        		for(Location l: reviewedList.get(tempRev)){
	        			double value = score(l,dist(l));
	        			if(sorted.containsKey(value))
		        			sorted.get(value).add(l);
		        		else {
		        			sorted.put(value, new ArrayList<Location>());
		        			sorted.get(value).add(l);
		        		}
	        		}
	        		for(double dist : sorted.keySet())
	        			for(Location l : sorted.get(dist))
	        				temp.add(l.getName());
	        			
	        	}
	        	} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
					popupwindow.showAndWait();
					
					       
				}
	        	finally{
	        	System.out.println(temp);
	        	items =FXCollections.observableArrayList (temp);
	        	locations.setItems(items);
	        	locationData.clear();
	        	stage.setScene(list);
	        	}
	        	
	        });
	        // return Button
	        back.setOnAction(e->{
	        	stage.setScene(home);
	        });
	        
	        //Reviewed Scene Actions_____________________________________________________________________________________________
	        star1.setOnAction(e ->{
	        	temp.clear();
	        	for(Location l :data.getReviews().get(1))
	        		temp.add(l.toString());
	        	items =FXCollections.observableArrayList (temp);
	        	locations.setItems(items);
	        	stage.setScene(AdressScene);
	        	lastScene = reviewScene;
	        	tempRev =1;
	        });
	        star2.setOnAction(e ->{
	        	temp.clear();
	        	for(Location l :data.getReviews().get(2))
	        		temp.add(l.getName());
	        	items =FXCollections.observableArrayList (temp);
	        	locations.setItems(items);
	        	stage.setScene(AdressScene);
	        	lastScene = reviewScene;
	        	tempRev =2;
	        });
	        star3.setOnAction(e ->{
	        	temp.clear();
	        	for(Location l :data.getReviews().get(3))
	        		temp.add(l.getName());
	        	items =FXCollections.observableArrayList (temp);
	        	locations.setItems(items);
	        	stage.setScene(AdressScene);
	        	lastScene = reviewScene;
	        	tempRev =3;
	        });
	        star4.setOnAction(e ->{
	        	temp.clear();
	        	for(Location l :data.getReviews().get(4))
	        		temp.add(l.getName());
	        	items =FXCollections.observableArrayList (temp);
	        	locations.setItems(items);
	        	stage.setScene(AdressScene);
	        	lastScene = reviewScene;
	        	tempRev =4;
	        });
	        star5.setOnAction(e ->{
	        	temp.clear();
	        	for(Location l :data.getReviews().get(5))
	        		temp.add(l.getName());
	        	items =FXCollections.observableArrayList (temp);
	        	locations.setItems(items);
	        	stage.setScene(AdressScene);
	        	lastScene = reviewScene;
	        	tempRev =5;
	        });
	        backs.setOnAction(e-> {
	        	stage.setScene(home);
	        });
	        //Recent Scene Actions_______________________________________________________________________________________________
	        //Location List Actions _______________________________________________________________________________________________
	      // list commands  
	        locations.addEventHandler(MouseEvent.MOUSE_PRESSED,e ->{
	        	
	        	int i = locations.getSelectionModel().getSelectedIndex();
	        	if(i<0)
	        		return;
	        	for(Location l: data.getLocData().get(tag.getValue())) 
	        		if(l.getName().equals(items.get(i))) {
	        			locationData.setText("Name: "+l.getName()+"\n"+"Type: "+l.getType()+"\nYour Review: "+l.getReview()+"\nLongitude: "+l.getLongitude()+"\nLatitude: "+l.getLatitude()+"\nDistance: "+dist(l)+"\nDirections: "+"https://www.google.com/maps/dir/add/"+l.getAddress()+"\n Yelp Reviews: "+"https://www.yelp.com/biz/"+l.getName().replaceAll("\\s","-")+"-austin-crossroads-austin?osq="+l.getAddress());
	        			enter.setDisable(false);
	        			tempLocation = l;
	        		}
	        });
	        locations.addEventHandler(KeyEvent.KEY_PRESSED,e ->{
	        	if(e.getCode().equals(KeyCode.DOWN) ||e.getCode().equals(KeyCode.UP) ) {
	        	if(lastScene.equals(home)) {
	        	
					int s = locations.getSelectionModel().getSelectedIndex();
					if(s<0)
						return;
	        	for(Location l: data.getLocData().get(tag.getValue())) 
	        		if(l.getName().equals(items.get(s))) {
	        			locationData.setText("Name: "+l.getName()+"\n"+"Type: "+l.getType()+"\nYour Review: "+l.getReview()+"\nLongitude: "+l.getLongitude()+"\nLatitude: "+l.getLatitude()+"\nDistance: "+dist(l)+"\nDirections: "+"https://www.google.com/maps/dir/add/"+l.getAddress()+"\n Yelp Reviews: "+"https://www.yelp.com/biz/"+l.getName().replaceAll("\\s","-")+"-austin-crossroads-austin?osq="+l.getAddress());
	        			enter.setDisable(false);
	        			tempLocation = l;
	        		}
	        	}
	        	}
	        	});
	   
	     //backwards Buttons
	        backwards.setOnAction(e->{
	        	stage.setScene(AdressScene);
	        });
	        enter.setOnAction(e->{
	        	stage.setScene(locationOne);
	        	if(tempLocation!= null)
	        	information.setText("Name: "+tempLocation.getName()+"\n"+"Type: "+tempLocation.getType()+"\nYour Review: "+tempLocation.getReview()+"\nLongitude: "+tempLocation.getLongitude()+"\nLatitude: "+tempLocation.getLatitude()+"\nDistance: "+dist(tempLocation)+"\nDirections: "+"https://www.google.com/maps/dir/add/"+tempLocation.getAddress()+"\n Yelp Reviews: "+"https://www.yelp.com/biz/"+tempLocation.getName().replaceAll("\\s","-")+"-austin-crossroads-austin?osq="+tempLocation.getAddress());
	        	data.getRecent().add(tempLocation);
	        });
	   //Location Action Scene___________________________________________________________________________________
	        exit.setOnAction(e ->{
	        	locationData.clear();
	        	stage.setScene(list);
	        });
	        Lstar1.setOnAction(e ->{
	        	for(int j =0;j<data.getLocData().get(tempLocation.getType()).size();j++ ){
	        		Location l = data.getLocData().get(tempLocation.getType()).get(j);	
	        		if(l.equals(tempLocation)) {
	        				boolean isReviewed = true;
						if(l.getReview() == 0 )
	        					isReviewed = false;
	        			if(isReviewed) {
	        				for(int i=0;i<data.getReviews().get(l.getReview()).size();i++)
	        						if(data.getReviews().get(l.getReview()).get(i).equals(tempLocation))
	        							data.getReviews().get(l.getReview()).remove(i);
	        							
	        				}
	        			tempLocation.setReview(1);
	        			System.out.println(tempLocation);
	        			data.getReviews().get(1).add(tempLocation);
	        			data.getLocData().get(l.getType()).get(j).setReview(1);
	        			System.out.println(data.getLocData().get(l.getType()).get(j));	
	        			}
	        	}
	        	
	        });
	        Lstar2.setOnAction(e ->{
	        	for(int j =0;j<data.getLocData().get(tempLocation.getType()).size();j++ ){
	        		Location l = data.getLocData().get(tempLocation.getType()).get(j);	
	        		if(l.equals(tempLocation)) {
	        				boolean isReviewed = true;
						if(l.getReview() == 0 )
	        					isReviewed = false;
	        			if(isReviewed) {
	        				for(int i=0;i<data.getReviews().get(l.getReview()).size();i++)
	        						if(data.getReviews().get(l.getReview()).get(i).equals(tempLocation))
	        							data.getReviews().get(l.getReview()).remove(i);
	        							
	        				}
	        			tempLocation.setReview(2);
	        			data.getReviews().get(2).add(tempLocation);
	        			data.getLocData().get(l.getType()).get(j).setReview(2);
	        				
	        			}
	        	}
	        	
	        });
	        Lstar3.setOnAction(e ->{
	        	for(int j =0;j<data.getLocData().get(tempLocation.getType()).size();j++ ){
	        		Location l = data.getLocData().get(tempLocation.getType()).get(j);	
	        		if(l.equals(tempLocation)) {
	        				boolean isReviewed = true;
						if(l.getReview() == 0 )
	        					isReviewed = false;
	        			if(isReviewed) {
	        				for(int i=0;i<data.getReviews().get(l.getReview()).size();i++)
	        						if(data.getReviews().get(l.getReview()).get(i).equals(tempLocation))
	        							data.getReviews().get(l.getReview()).remove(i);
	        							
	        				}
	        			tempLocation.setReview(3);
	        			data.getReviews().get(3).add(tempLocation);
	        			data.getLocData().get(l.getType()).get(j).setReview(3);
	        				
	        			}
	        	}
	        	
	        });
	        Lstar4.setOnAction(e ->{
	        	for(int j =0;j<data.getLocData().get(tempLocation.getType()).size();j++ ){
	        		Location l = data.getLocData().get(tempLocation.getType()).get(j);	
	        		if(l.equals(tempLocation)) {
	        				boolean isReviewed = true;
						if(l.getReview() == 0 )
	        					isReviewed = false;
	        			if(isReviewed) {
	        				for(int i=0;i<data.getReviews().get(l.getReview()).size();i++)
	        						if(data.getReviews().get(l.getReview()).get(i).equals(tempLocation))
	        							data.getReviews().get(l.getReview()).remove(i);
	        							
	        				}
	        			tempLocation.setReview(4);
	        			data.getReviews().get(4).add(tempLocation);
	        			data.getLocData().get(l.getType()).get(j).setReview(4);
	        				
	        			}
	        	}
	        	
	        });
	        Lstar5.setOnAction(e ->{
	        	for(int j =0;j<data.getLocData().get(tempLocation.getType()).size();j++ ){
	        		Location l = data.getLocData().get(tempLocation.getType()).get(j);	
	        		if(l.equals(tempLocation)) {
	        				boolean isReviewed = true;
						if(l.getReview() == 0 )
	        					isReviewed = false;
	        			if(isReviewed) {
	        				for(int i=0;i<data.getReviews().get(l.getReview()).size();i++)
	        						if(data.getReviews().get(l.getReview()).get(i).equals(tempLocation))
	        							data.getReviews().get(l.getReview()).remove(i);
	        							
	        				}
	        			tempLocation.setReview(5);
	        			data.getReviews().get(5).add(tempLocation);
	        			data.getLocData().get(l.getType()).get(j).setReview(5);
	        				
	        			}
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
