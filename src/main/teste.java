package main;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;

public class teste extends Application {

	public static void main(String[] args) {

		try {
			launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start(Stage primaryStage) {
		primaryStage.setTitle("GO Aero");

		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);

		Canvas canvas = new Canvas(800, 600);
		GraphicsContext context = canvas.getGraphicsContext2D();
		root.setCenter(canvas);

		ArrayList<String> keyPressedList = new ArrayList<String>();

	
		
		Sprite fundo = new Sprite("main/recursos/1280.png");
		fundo.position.set(400, 300);
		fundo.render(context);

		Sprite aviao = new Sprite("main/recursos/aviao.png");
		aviao.position.set(100, 300);
		aviao.velocity.set(50, 0);
		aviao.render(context);

		
		//moving player
		scene.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.RIGHT) {
				aviao.setLayoutX(aviao.getLayoutX() + 5);
			}
            if(e.getCode() == KeyCode.LEFT) {
            	aviao.setLayoutX(aviao.getLayoutX() - 5);
			}
            if(e.getCode() == KeyCode.SPACE) {
				playerShoot(aviao.getLayoutX());
			}
		});
		
		AnimationTimer gameloop = new AnimationTimer() {
			public void handle(long nanotime) {
				
				if (keyPressedList.contains("LEFT"))
					aviao.rotation -= 3;
				
				if (keyPressedList.contains("RIGHT"))
					aviao.rotation += 3;
				
		
				aviao.update(1 / 60.0);

				fundo.render(context);
				aviao.render(context);
			}
		};

		gameloop.start();

		primaryStage.show();

	}
}
