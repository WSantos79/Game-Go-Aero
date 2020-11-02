package main;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class pastebin extends Application {
	AnimationTimer timer;
	Pane root = new Pane();
	List<ImageView> monsters = new ArrayList<ImageView>();
	List<Circle> mShoots = new ArrayList<Circle>();
	List<Circle> pShoots = new ArrayList<Circle>();
	ImageView player;
	Circle dotR = new Circle();
	boolean toRight = true;
	Text lives;
	Text points;
	int numPoints = 0;
	int numLives = 3;

	public static void main(String[] args) {
		launch();

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// life and points

		lives = new Text("Lives: 3");
		lives.setLayoutX(20);
		lives.setLayoutY(30);
		lives.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		lives.setFill(Color.WHITE);
		points = new Text("Points: 0");
		points.setLayoutX(350);
		points.setLayoutY(30);
		points.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		points.setFill(Color.WHITE);
		root.getChildren().addAll(lives, points);

		// dot that regulates moving of monsters
		dotR.setLayoutX(0);

		// creating player
		player = player();
		root.getChildren().add(player);

		// create monsters
		addMonsters();

		// AnimationTimer
		timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				gameUpdate();
			}
		};
		timer.start();

		// timeline for making monsters shoots every few seconds

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
			if (!monsters.isEmpty()) {
				monstersShoot();

			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		// setting up stage
		Scene scene = new Scene(root, 500, 700);
		scene.setFill(Color.BLACK);
		// moving player
		scene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.RIGHT) {
				player.setLayoutX(player.getLayoutX() + 5);
			}
			if (e.getCode() == KeyCode.LEFT) {
				player.setLayoutX(player.getLayoutX() - 5);
			}
			if (e.getCode() == KeyCode.SPACE) {
				playerShoot(player.getLayoutX());
			}
		});
		primaryStage.setScene(scene);
		primaryStage.setTitle("Space Invaders");
		primaryStage.show();

	}

	public void gameUpdate() {
		// updating monsters shoots
		monstersShootUpdate();
		// updating players Shoots
		playersShootUpdate();
		// checking if player is hit
		isPlayerDestroyed();
		// checking if monster is hit
		isMonsterDestroyed();
		// moving monsters
		monstersMove();
		// is Player win
		isWin();
		// is Player lost
		isLost();
	}

	public int rand(int min, int max) {
		return (int) (Math.random() * max + min);
	}

	public void monstersShoot() {
		int getShootingMonsterIndex = rand(0, monsters.size() - 1);
		mShoots.add(shoot(monsters.get(getShootingMonsterIndex).getLayoutX() + 25,
				monsters.get(getShootingMonsterIndex).getLayoutY() + 25));
		root.getChildren().add((Node) mShoots.get(mShoots.size() - 1));
	}

	public void addMonsters() {
		for (int i = 0, w = 40; i < 6; i++, w += 70) {
			monsters.add(monster(w, 50));
			root.getChildren().add((Node) monsters.get(i));
		}
		for (int i = 0, w = 40; i < 6; i++, w += 70) {
			monsters.add(monster(w, 120));
			root.getChildren().add((Node) monsters.get(i + 6));
		}
		for (int i = 0, w = 40; i < 6; i++, w += 70) {
			monsters.add(monster(w, 190));
			root.getChildren().add((Node) monsters.get(i + 12));
		}
	}

	public void monstersMove() {
		// MONSTERS MOVING
		double speed;

		if (toRight)
			speed = 0.6;
		else
			speed = -0.6;

		if (dotR.getLayoutX() >= 40) {
			toRight = false;
			for (int i = 0; i < monsters.size(); i++) {
				monsters.get(i).setLayoutY(monsters.get(i).getLayoutY() + 8);
			}

		}
		if (dotR.getLayoutX() <= -20) {
			toRight = true;
			for (int i = 0; i < monsters.size(); i++) {
				monsters.get(i).setLayoutY(monsters.get(i).getLayoutY() + 8);
			}
		}

		for (int i = 0; i < monsters.size(); i++) {
			monsters.get(i).setLayoutX(monsters.get(i).getLayoutX() + speed);
		}
		dotR.setLayoutX(dotR.getLayoutX() + speed);
	}

	public ImageView player() {
		ImageView i = new ImageView(new Image(getClass().getResourceAsStream("recursos/aviao.png")));
		i.setLayoutX(225);
		i.setLayoutY(650);
		i.setFitHeight(50);
		i.setFitWidth(50);
		return i;
	}

	public ImageView monster(double x, double y) {
		ImageView i = new ImageView(new Image(getClass().getResourceAsStream("recursos/aviao.png")));
		i.setLayoutX(x);
		i.setLayoutY(y);
		i.setFitHeight(50);
		i.setFitWidth(50);
		return i;
	}

	public Circle shoot(double x, double y) {
		Circle c = new Circle();
		c.setFill(Color.GREENYELLOW);
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setRadius(3);
		return c;
	}

	public void playerShoot(double x) {
		pShoots.add(shoot((x + 25), 650));
		root.getChildren().add(pShoots.get(pShoots.size() - 1));
	}

	private void playersShootUpdate() {

		if (!pShoots.isEmpty()) {
			for (int i = 0; i < pShoots.size(); i++) {
				pShoots.get(i).setLayoutY(pShoots.get(i).getLayoutY() - 3);
				if (pShoots.get(i).getLayoutY() <= 0) {
					root.getChildren().remove(pShoots.get(i));
					pShoots.remove(i);
				}
			}
		}
	}

	private void monstersShootUpdate() {

		if (!mShoots.isEmpty()) {
			for (int i = 0; i < mShoots.size(); i++) {
				mShoots.get(i).setLayoutY(mShoots.get(i).getLayoutY() + 3);
				if (mShoots.get(i).getLayoutY() <= 0) {
					root.getChildren().remove(mShoots.get(i));
					mShoots.remove(i);
				}
			}
		}
	}

	private void isMonsterDestroyed() {

		for (int i = 0; i < pShoots.size(); i++) {
			for (int j = 0; j < monsters.size(); j++) {
				if (((pShoots.get(i).getLayoutX() > monsters.get(j).getLayoutX())
						&& ((pShoots.get(i).getLayoutX() < monsters.get(j).getLayoutX() + 50))
						&& ((pShoots.get(i).getLayoutY() > monsters.get(j).getLayoutY())
								&& ((pShoots.get(i).getLayoutY() < monsters.get(j).getLayoutY() + 50))))) {
					root.getChildren().remove(monsters.get(j));
					monsters.remove(j);
					root.getChildren().remove(pShoots.get(i));
					pShoots.remove(i);
					numPoints += 100;
					points.setText("Points: " + String.valueOf(numPoints));
				}
			}
		}
	}

	private void isPlayerDestroyed() {

		for (int i = 0; i < mShoots.size(); i++) {

			if (((mShoots.get(i).getLayoutX() > player.getLayoutX())
					&& ((mShoots.get(i).getLayoutX() < player.getLayoutX() + 50))
					&& ((mShoots.get(i).getLayoutY() > player.getLayoutY())
							&& ((mShoots.get(i).getLayoutY() < player.getLayoutY() + 50))))) {
				player.setLayoutX(225);
				numLives -= 1;
				lives.setText("Lives: " + String.valueOf(numLives));

			}
		}
	}

	public void isWin() {
		if (monsters.isEmpty()) {
			Text text = new Text();
			text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
			text.setX(180);
			text.setY(300);
			text.setFill(Color.YELLOW);
			text.setStrokeWidth(3);
			text.setStroke(Color.GOLD);
			text.setText("WIN");
			root.getChildren().add(text);
			timer.stop();
		}
	}

	public void isLost() {
		if (numLives <= 0) {
			Text text = new Text();
			text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
			text.setX(180);
			text.setY(300);
			text.setFill(Color.RED);
			text.setStrokeWidth(3);
			text.setStroke(Color.CRIMSON);
			text.setText("LOST");
			root.getChildren().add(text);
			timer.stop();
		}
	}

}
