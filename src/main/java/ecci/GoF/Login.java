package ecci.GoF;

import javafx.application.Application;
import javafx.event.*;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import sun.font.FontFamily;

import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException{

        Label spaces = new Label("   ");
        Label tabs = new Label("\t\t\t\t\t");
        tabs.setAlignment(Pos.BASELINE_LEFT);

        VBox vb = new VBox();
        vb.setPadding(new Insets(360,20,0,40));
        vb.setSpacing(10);

        HBox hbUser = new HBox();
        hbUser.setSpacing(10);
        hbUser.setAlignment(Pos.BASELINE_LEFT);

        HBox hbPassword = new HBox();
        hbPassword.setSpacing(10);
        hbPassword.setAlignment(Pos.BASELINE_LEFT);

        HBox hbAutMessage = new HBox();
        hbAutMessage.setSpacing(10);
        hbAutMessage.setAlignment(Pos.BASELINE_LEFT);

        HBox hbSaltarLogin = new HBox();
        hbSaltarLogin.setSpacing(10);
        hbSaltarLogin.setAlignment(Pos.BASELINE_LEFT);

        Label username = new Label("Usuario\t\t");
        username.setTextFill(Paint.valueOf("black"));
        username.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 14));

        Label password = new Label("Contraseña\t");
        password.setTextFill(Paint.valueOf("black"));
        password.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 14));

        TextField userTxt = new TextField();

        Hyperlink skipLogin = new Hyperlink("saltar paso");
        skipLogin.setAlignment(Pos.BASELINE_RIGHT);
        skipLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                InicioGUI.main(null);
            }
        });

        final PasswordField passwordTxt = new PasswordField();
        final Label message = new Label("   ");

        Image okImage = new Image(new FileInputStream("src\\images\\ok2.png"));
        Image microscopioImage = new Image(new FileInputStream("src\\images\\mc.jpg"));
        Button enter = new Button();
        enter.setGraphic(new ImageView(okImage));
        enter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(passwordTxt.getText().equals("") && userTxt.getText().equals("")){
                    message.setText("\t\t\t\tdebe ingresar usuario y contraseña!");
                    message.setTextFill(Paint.valueOf("blue"));
                } else if(userTxt.getText().equals("")){
                    message.setText("\t\t\t\tdebe ingresar usuario!");
                    message.setTextFill(Paint.valueOf("blue"));
                } else if (passwordTxt.getText().equals("")) {
                    message.setText("\t\t\t\tdebe ingresar contraseña!");
                    message.setTextFill(Paint.valueOf("blue"));
                }else if (!passwordTxt.getText().equals("b32135") && !userTxt.getText().equals("jct")) {
                    message.setText("\t\t\t\tusuario y/o contraseña incorrecta!");
                    message.setTextFill(Paint.valueOf("red"));
                } else {
                    message.setText("\t\t\t\tcontraseña correcta!");
                    message.setTextFill(Paint.valueOf("green"));
                    InicioGUI.main(null);
                }
                message.setFont(Font.font("Verdana", FontPosture.ITALIC, 10));
                passwordTxt.clear();
            }
        });

        BackgroundSize backgroundSize = new BackgroundSize(800, 800, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(microscopioImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);


        hbUser.getChildren().addAll(username, userTxt);
        hbPassword.getChildren().addAll(password, passwordTxt, spaces,enter);
        hbAutMessage.getChildren().addAll(message);
        hbSaltarLogin.getChildren().addAll(tabs, skipLogin);

        vb.setBackground(background);
        vb.setAlignment(Pos.BASELINE_CENTER);

        vb.getChildren().addAll(hbUser, hbPassword, hbAutMessage, hbSaltarLogin);


        Group root = new Group(vb);

        Scene scene = new Scene(root, 440, 500);
        scene.setFill(Paint.valueOf("white"));


        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

}
