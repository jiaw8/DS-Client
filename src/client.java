

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.control.*;
import javafx.scene.input.TouchPoint;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.net.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class client extends Application implements EventHandler {

    Scene login;
    Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{


//        this.primaryStage= primaryStage;
//        Label user=new Label("User");
//        TextField textUser=new TextField();
//        Label pwd=new Label("Password");
//        TextField textPwd=new TextField();
//        Button button=new Button("Login");
//        button.setOnAction(this);
//
//        primaryStage.setTitle("Login");
//        VBox layout=new VBox();
////        layout.getChildren().addAll(user,textUser,pwd,textPwd,button);
//        layout.getChildren().addAll(button);
//        login=new Scene(layout,200,200);
//        primaryStage.setScene(login);
//        primaryStage.show();
        String response=executePost();

        Scene scene = new Scene(new Group());

        VBox root = new VBox();

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(browser);


        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    @Override public void changed(ObservableValue ov, State oldState, State newState) {

                        if (newState == Worker.State.SUCCEEDED) {

                            if (webEngine.getLocation().contains("https://www.facebook.com/connect/login_success.html")){

                                Pattern p = Pattern.compile(".*code=(?<code>.*)");
                                Matcher m = p.matcher(webEngine.getLocation());
                                if (m.find()){
                                    String code =m.group("code");
                                    String token = getToken(code);
                                    String userName = getUserName(token);


                                    Parent root = null;
                                    try {
                                        root = FXMLLoader.load(getClass().getResource("client.fxml"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    Scene scene = new Scene(root, 300, 275);
                                    primaryStage.setScene(scene);
                                    primaryStage.show();


                                }

                            }
                        }

                    }
                });

        webEngine.load(response);
        root.getChildren().addAll(scrollPane);
        scene.setRoot(root);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void handle(Event event) {

        String response=executePost();

        Scene scene = new Scene(new Group());

        VBox root = new VBox();

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(browser);


        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                    @Override public void changed(ObservableValue ov, State oldState, State newState) {

                        if (newState == Worker.State.SUCCEEDED) {

                            if (webEngine.getLocation().contains("https://www.facebook.com/connect/login_success.html")){

                                Pattern p = Pattern.compile(".*code=(?<code>.*)");
                                Matcher m = p.matcher(webEngine.getLocation());
                                if (m.find()){
                                    String code =m.group("code");
                                    String token = getToken(code);
                                    String userName = getUserName(token);


                                    Parent root = null;
                                    try {
                                        root = FXMLLoader.load(getClass().getResource("client.fxml"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    Scene scene = new Scene(root, 300, 275);
                                    primaryStage.setScene(scene);
                                    primaryStage.show();


                                }

                            }
                        }

                    }
                });

        webEngine.load(response);
        root.getChildren().addAll(scrollPane);
        scene.setRoot(root);

        primaryStage.setScene(scene);
        primaryStage.show();


    }
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = client.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(client.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        return (Initializable) loader.getController();
    }

    public static String executePost() {
        HttpURLConnection connection = null;
        String targetURL="https://www.facebook.com/dialog/oauth?client_id=199224123844359&redirect_uri=https://www.facebook.com/connect/login_success.html";
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);


            //Send request


            //Get Response
            InputStream is = connection.getInputStream();

            String newUrl = connection.getURL().toString();


            return newUrl;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public static String getToken(String code) {
        HttpURLConnection connection = null;
        String targetURL=" https://graph.facebook.com/oauth/access_token?";
        try {
        HashMap<String, String> postDataParams = new HashMap<>();
        postDataParams.put("client_id","199224123844359");
        postDataParams.put("client_secret","02b305adc33b23309aff71db3076e02d");
        postDataParams.put("redirect_uri","https://www.facebook.com/connect/login_success.html");
        postDataParams.put("code",code);
        String paramater= getPostDataString(postDataParams);
        String str=targetURL+paramater;
            String reponse="";
            URL url = new URL(str);
            connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                reponse = br.readLine();
                br.close();

            } else {
                reponse= "fail";
            }
            connection.disconnect();


            return reponse;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    public static String getUserName(String token) {
        HttpURLConnection connection = null;
        String targetURL="https://graph.facebook.com/me?";
        try {
//            HashMap<String, String> postDataParams = new HashMap<>();
//            postDataParams.put("access_token",token);

//            String paramater= getPostDataString(postDataParams);
            String str=targetURL+token;
            String reponse="";
            URL url = new URL(str);
            connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                reponse = br.readLine();
                br.close();

            } else {
                reponse= "fail";
            }
            connection.disconnect();


            return reponse;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public  static String getPostDataString(HashMap<String, String> params){
        try {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                if(entry.getValue()==null){
                    result.append(URLEncoder.encode("", "UTF-8"));
                }
                else {result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));}

            }
            return result.toString();
        } catch (UnsupportedEncodingException e) {
            System.out.print(e.getMessage());
        }return  "";
    }
}
