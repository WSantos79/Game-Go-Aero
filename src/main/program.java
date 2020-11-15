package main;

/**
 * @author Wellington Santos - WSantos79
 * 
 * Version 1.0
 */

import core.world;
import core.aviao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import java.util.TimerTask;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class program extends Application {

	private static final Random RAND = new Random();

	private static final String IMG_FUNDO = "recursos/1280.png";
	private static final String IMG_AVIAO = "recursos/aviao.png";
	private static final String IMG_SHOOT = "recursos/shoot0.png";
	private static final String IMG_SHOOT2 = "recursos/shoot2.png";

	private final Image imgFundo = new Image(getClass().getResourceAsStream(IMG_FUNDO));
	private final Image imgAviao = new Image(getClass().getResourceAsStream(IMG_AVIAO));
	private final Image imgShoot = new Image(getClass().getResourceAsStream(IMG_SHOOT));
	private final Image imgShoot2 = new Image(getClass().getResourceAsStream(IMG_SHOOT2));

	private final ImageView viewFundo = new ImageView(imgFundo);
	private final ImageView viewAviao = new ImageView(imgAviao);

	private final world mundo = new world(1262, 825);
	private final aviao aviao0 = new aviao(600, 750);

	private int pontos = 0;
	Text pontostxt;
	
	// cria array de tiros
	List<Circle> aShoots = new ArrayList<Circle>();
	List<Circle> iShoots = new ArrayList<Circle>();
	List<Circle> iShoots2 = new ArrayList<Circle>();
	List<Circle> iShoots3 = new ArrayList<Circle>();
	
	// cria circle para movimentaçao dos inimigos
	Circle dotR = new Circle();
	
	// cria array para carregar inimigos
	List<ImageView> enemies0 = new ArrayList<ImageView>();
	List<ImageView> enemies1 = new ArrayList<ImageView>();
	
	Pane root = new Pane();
	AnimationTimer timer;

	Timer timer1 = new Timer();
	
	boolean isMove = true;

	// para controlar o add de inimigos
	int b = 0;
	int a = 0;

	@Override
	public void start(Stage primaryStage) {
		try {
			viewFundo.setTranslateX(0);
			viewFundo.setTranslateY(0);

			viewAviao.setTranslateX(aviao0.getPosicaoX());
			viewAviao.setTranslateY(aviao0.getPosicaoY());

			root.getChildren().add(viewFundo);
			root.getChildren().add(viewAviao); // cria aviao principal

			// Cria os pontos
			pontostxt = new Text("Points: 0");
			pontostxt.setLayoutX(30); // << >>
			pontostxt.setLayoutY(810); // ^^ vv
			pontostxt.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 30));
			pontostxt.setFill(Color.WHITE);
			root.getChildren().add(pontostxt);

			// cria inimigos
			addInimigo0();
			addInimigo1();			
			
			Scene scene = new Scene(root, mundo.DIM_X, mundo.DIM_Y);

			primaryStage.setTitle("Go Aero");
			primaryStage.setScene(scene);
			primaryStage.show();

			// animacao
			timer = new AnimationTimer() {
				@Override
				public void handle(long arg0) {
					gameUpdate();

				}
			};
			timer.start();

			// Timeline para fazer os inimigos atirarem a cada segundos

			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
				if (!enemies1.isEmpty()) {
					inimigoShoot();

				}
			}));
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();

			scene.setOnKeyPressed((evt) -> {
				if (evt.getCode() == KeyCode.UP) {

					if (aviao0.avaliaPosicao(aviao0.getPosicaoX(),
							aviao0.getPosicaoY() - (int) aviao0.getVelocidade())) {
						aviao0.setPosicaoY(aviao0.getPosicaoY() - (int) aviao0.getVelocidade());
						viewAviao.setTranslateX(aviao0.getPosicaoX());
						viewAviao.setTranslateY(aviao0.getPosicaoY());

					}
				}
				if (evt.getCode() == KeyCode.DOWN) {

					if (aviao0.avaliaPosicao(aviao0.getPosicaoX(),
							aviao0.getPosicaoY() + (int) aviao0.getVelocidade())) {
						aviao0.setPosicaoY(aviao0.getPosicaoY() + (int) aviao0.getVelocidade());
						viewAviao.setTranslateX(aviao0.getPosicaoX());
						viewAviao.setTranslateY(aviao0.getPosicaoY());

					}
				}
				if (evt.getCode() == KeyCode.LEFT) {

					if (aviao0.avaliaPosicao(aviao0.getPosicaoX() - (int) aviao0.getVelocidade(),
							aviao0.getPosicaoY())) {
						aviao0.setPosicaoX(aviao0.getPosicaoX() - (int) aviao0.getVelocidade());
						viewAviao.setTranslateX(aviao0.getPosicaoX());
						viewAviao.setTranslateY(aviao0.getPosicaoY());

					}
				}
				if (evt.getCode() == KeyCode.RIGHT) {

					if (aviao0.avaliaPosicao(aviao0.getPosicaoX() + (int) aviao0.getVelocidade(),
							aviao0.getPosicaoY())) {
						aviao0.setPosicaoX(aviao0.getPosicaoX() + (int) aviao0.getVelocidade());
						viewAviao.setTranslateX(aviao0.getPosicaoX());
						viewAviao.setTranslateY(aviao0.getPosicaoY());

					}
				}

			});

			scene.setOnKeyReleased((evt) -> {
				if (evt.getCode() == KeyCode.SPACE) {
					aviaoShoot(aviao0.getPosicaoX(), aviao0.getPosicaoY());
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// classe que adciona os inimigos
	public void addenemy0() {

		enemies0.add(enemies0(RAND.nextInt(mundo.DIM_X - 40), -40)); // x = cima... y = lados
		root.getChildren().add((Node) enemies0.get(a));
		a += 1;
		enemies0.add(enemies0(RAND.nextInt(mundo.DIM_X - 40), -40)); // x = cima... y = lados
		root.getChildren().add((Node) enemies0.get(a));
		a += 1;

	}

	// classe que adciona os inimigos que ATIRAM
	public void addenemy1() {

		enemies1.add(enemies1(RAND.nextInt(mundo.DIM_X - 40), -40)); // x = cima... y = lados
		root.getChildren().add((Node) enemies1.get(b));
		b += 1;
		enemies1.add(enemies1(RAND.nextInt(mundo.DIM_X - 40), -40)); // x = cima... y = lados
		root.getChildren().add((Node) enemies1.get(b));
		b += 1;

	}

	public void gameUpdate() {

		// atualizacao do tiro / animacao
		aviaoShootUpdate();
		inimigoShootUpdate();
		enemiesMove(2);
		enemiesDeath();

	}
	// tiro do aviao
	public Circle ashoot(double x, double y) {

		Circle c = new Circle();
		c.setFill(new ImagePattern(imgShoot));
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setRadius(9);

		return c;
	}
	// tiro que sai do lado de <<< do inimigo
	public Circle ishoot(double x, double y) {

		Circle c = new Circle();
		c.setFill(new ImagePattern(imgShoot2));
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setRadius(9);

		return c;
	}
	// tiro que sai do lado do meio do inimigo
	public Circle ishoot2(double x, double y) {

		Circle c = new Circle();
		c.setFill(new ImagePattern(imgShoot2));
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setRadius(9);

		return c;
	}
	// tiro que sai do lado de >>> do inimigo
	public Circle ishoot3(double x, double y) {

		Circle c = new Circle();
		c.setFill(new ImagePattern(imgShoot2));
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setRadius(9);

		return c;
	}

	public void aviaoShoot(double x, double y) {
		aShoots.add(ashoot((x + 30), y));
		root.getChildren().add(aShoots.get(aShoots.size() - 1));
	}

	// adciona os tiros ao inimigo
	public void inimigoShoot() {

		for (int j = 0; j < enemies1.size(); j++) {

			iShoots.add(ishoot(enemies1.get(j).getLayoutX() + 1, // sai o tiro << >>
					enemies1.get(j).getLayoutY() + 67)); // sai o tiro ^^ vv
			root.getChildren().add((Node) iShoots.get(iShoots.size() - 1));

			iShoots2.add(ishoot2(enemies1.get(j).getLayoutX() + 30, // sai o tiro << >>
					enemies1.get(j).getLayoutY() + 67)); // sai o tiro ^^ vv
			root.getChildren().add((Node) iShoots2.get(iShoots2.size() - 1));

			iShoots3.add(ishoot3(enemies1.get(j).getLayoutX() + 60, // sai o tiro << >>
					enemies1.get(j).getLayoutY() + 67)); // sai o tiro ^^ vv
			root.getChildren().add((Node) iShoots3.get(iShoots3.size() - 1));
		}
	}

	private void aviaoShootUpdate() {

		if (!aShoots.isEmpty()) {
			for (int i = 0; i < aShoots.size(); i++) {
				aShoots.get(i).setLayoutY(aShoots.get(i).getLayoutY() - 7); // numero representa velocidade do tiro
				if (aShoots.get(i).getLayoutY() <= 0) {
					root.getChildren().remove(aShoots.get(i));
					aShoots.remove(i);
				}
			}
		}
	}

	private void inimigoShootUpdate() {

		// tiro de lado <<<
		if (!iShoots.isEmpty()) {
			for (int i = 0; i < iShoots.size(); i++) {
				iShoots.get(i).setLayoutY(iShoots.get(i).getLayoutY() + 3); // numero representa velocidade do tiro
				iShoots.get(i).setLayoutX(iShoots.get(i).getLayoutX() - 1);
				if (iShoots.get(i).getLayoutY() <= 0) {
					root.getChildren().remove(iShoots.get(i));
					iShoots.remove(i);
				}
			}
		}

		// tiro do meio
		if (!iShoots2.isEmpty()) {
			for (int i = 0; i < iShoots2.size(); i++) {
				iShoots2.get(i).setLayoutY(iShoots2.get(i).getLayoutY() + 3); // numero representa velocidade do tiro
				if (iShoots2.get(i).getLayoutY() <= 0) {
					root.getChildren().remove(iShoots2.get(i));
					iShoots2.remove(i);
				}
			}
		}

		// tiro de lado >>
		if (!iShoots3.isEmpty()) {
			for (int i = 0; i < iShoots3.size(); i++) {
				iShoots3.get(i).setLayoutY(iShoots3.get(i).getLayoutY() + 3); // numero representa velocidade do tiro
				iShoots3.get(i).setLayoutX(iShoots3.get(i).getLayoutX() + 1);
				if (iShoots3.get(i).getLayoutY() <= 0) {
					root.getChildren().remove(iShoots3.get(i));
					iShoots3.remove(i);
				}
			}
		}
	}

	public ImageView enemies0(double x, double y) {
		ImageView i = new ImageView(new Image(getClass().getResourceAsStream("recursos/enemy0.png")));
		i.setLayoutX(x);
		i.setLayoutY(y);
		i.setFitHeight(70);
		i.setFitWidth(60);
		return i;
	}

	public ImageView enemies1(double x, double y) {
		ImageView j = new ImageView(new Image(getClass().getResourceAsStream("recursos/enemy1.png")));
		j.setLayoutX(x);
		j.setLayoutY(y);
		j.setFitHeight(67);
		j.setFitWidth(60);
		return j;
	}

	public void enemiesMove(double speed) {
		// movendo inimigos

		if (dotR.getLayoutX() >= 40) { // limite >>> // inimigos normais

			for (int i = 0; i < enemies0.size(); i++) {
				enemies0.get(i).setLayoutY(enemies0.get(i).getLayoutY() + speed);

			}
		}
		if (dotR.getLayoutX() >= 40) { // limite >>> // inimigos que atiram

			for (int i = 0; i < enemies1.size(); i++) {
				enemies1.get(i).setLayoutY(enemies1.get(i).getLayoutY() + speed);

			}
		}

		dotR.setLayoutX(dotR.getLayoutX() + speed);
	}

	private void addInimigo0() {

		timer1.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Platform.runLater(() -> {

					addenemy0();

				});
			}
		}, 2000, 2000);

		// DELEY > tempo de espera antes da 1 execucao da tarefa.
		// INTERVAL > intervalo no qual a tarefa sera executada.
	}

	private void addInimigo1() {

		timer1.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Platform.runLater(() -> {

					addenemy1();

				});
			}
		}, 5000, 9000);
		// DELEY > tempo de espera antes da 1 execucao da tarefa.
		// INTERVAL > intervalo no qual a tarefa sera executada.
	}	
	
	private void enemiesDeath() {
		// verefica se inimigo foi morto e retira ele >> inimigo normal
		for (int i = 0; i < aShoots.size(); i++) {
			for (int j = 0; j < enemies0.size(); j++) {
				if (((aShoots.get(i).getLayoutX() > enemies0.get(j).getLayoutX())
						&& ((aShoots.get(i).getLayoutX() < enemies0.get(j).getLayoutX() + 50))
						&& ((aShoots.get(i).getLayoutY() > enemies0.get(j).getLayoutY())
								&& ((aShoots.get(i).getLayoutY() < enemies0.get(j).getLayoutY() + 50))))) {

					pontos += 100;
					pontostxt.setText("Pontos: " + String.valueOf(pontos));
					root.getChildren().remove(enemies0.get(j));
					enemies0.remove(j);
					a -= 1;
					root.getChildren().remove(aShoots.get(i));
					aShoots.remove(i);
					break;

				}
			}
		}
		// verefica se inimigo foi morto e retira ele >> inimigo que atira
		for (int k = 0; k < aShoots.size(); k++) {
			for (int l = 0; l < enemies1.size(); l++) {
				if (((aShoots.get(k).getLayoutX() > enemies1.get(l).getLayoutX())
						&& ((aShoots.get(k).getLayoutX() < enemies1.get(l).getLayoutX() + 50))
						&& ((aShoots.get(k).getLayoutY() > enemies1.get(l).getLayoutY())
								&& ((aShoots.get(k).getLayoutY() < enemies1.get(l).getLayoutY() + 50))))) {

					pontos += 200;
					pontostxt.setText("Pontos: " + String.valueOf(pontos));
					root.getChildren().remove(enemies1.get(l));
					enemies1.remove(l);
					b -= 1;
					root.getChildren().remove(aShoots.get(k));
					aShoots.remove(k);
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}