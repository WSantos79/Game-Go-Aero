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

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class program extends Application {

	private static final Random RAND = new Random();

	private int pontos = 0;

	private final String IMG_FUNDO = "recursos/1280.png";
	private final String IMG_AVIAO = "recursos/aviao.png";

	private final Image imgFundo = new Image(getClass().getResourceAsStream(IMG_FUNDO));
	private final Image imgAviao = new Image(getClass().getResourceAsStream(IMG_AVIAO));

	private final ImageView viewFundo = new ImageView(imgFundo);
	private final ImageView viewAviao = new ImageView(imgAviao);

	private final world mundo = new world(1262, 825);
	private final aviao aviao0 = new aviao(600, 750);



	List<Circle> pShoots = new ArrayList<Circle>();
	Circle dotR = new Circle();
	List<ImageView> enemies0 = new ArrayList<ImageView>();
	List<ImageView> enemies1 = new ArrayList<ImageView>();
	Pane root = new Pane();
	AnimationTimer timer;

	Timer timer2 = new Timer();

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
			

			
			 //cria inimigos 
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
		enemiesMove(2);
		enemiesDeath();

	}
	


	public Circle shoot(double x, double y) {	
			
		
		Circle c = new Circle();
		c.setFill(Color.SPRINGGREEN);		
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setRadius(4);
		
		return c;
	}

	public void aviaoShoot(double x, double y) {
		pShoots.add(shoot((x + 30), y));
		root.getChildren().add(pShoots.get(pShoots.size() - 1));
	}

	private void aviaoShootUpdate() {

		if (!pShoots.isEmpty()) {
			for (int i = 0; i < pShoots.size(); i++) {
				pShoots.get(i).setLayoutY(pShoots.get(i).getLayoutY() - 7); // numero representa velocidade do tiro
				if (pShoots.get(i).getLayoutY() <= 0) {
					root.getChildren().remove(pShoots.get(i));
					pShoots.remove(i);
				}
			}
		}
	}

	public ImageView enemies0(double x, double y) {
		ImageView i = new ImageView(new Image(getClass().getResourceAsStream("recursos/enemy0.png")));
		i.setLayoutX(x);
		i.setLayoutY(y);
		i.setFitHeight(50);
		i.setFitWidth(50);
		return i;
	}

	public ImageView enemies1(double x, double y) {
		ImageView j = new ImageView(new Image(getClass().getResourceAsStream("recursos/enemy1.png")));
		j.setLayoutX(x);
		j.setLayoutY(y);
		j.setFitHeight(50);
		j.setFitWidth(50);
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


		timer2.scheduleAtFixedRate(new TimerTask() {

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

		

		timer2.scheduleAtFixedRate(new TimerTask() {

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
		for (int i = 0; i < pShoots.size(); i++) {
			for (int j = 0; j < enemies0.size(); j++) {
				if (((pShoots.get(i).getLayoutX() > enemies0.get(j).getLayoutX())
						&& ((pShoots.get(i).getLayoutX() < enemies0.get(j).getLayoutX() + 50))
						&& ((pShoots.get(i).getLayoutY() > enemies0.get(j).getLayoutY())
								&& ((pShoots.get(i).getLayoutY() < enemies0.get(j).getLayoutY() + 50))))) {

					pontos += 100;
					root.getChildren().remove(enemies0.get(j));
					enemies0.remove(j);
					root.getChildren().remove(pShoots.get(i));
					pShoots.remove(i);

				}
			}
		}
		// verefica se inimigo foi morto e retira ele >> inimigo que atira
		for (int i = 0; i < pShoots.size(); i++) {
			for (int j = 0; j < enemies1.size(); j++) {
				if (((pShoots.get(i).getLayoutX() > enemies1.get(j).getLayoutX())
						&& ((pShoots.get(i).getLayoutX() < enemies1.get(j).getLayoutX() + 50))
						&& ((pShoots.get(i).getLayoutY() > enemies1.get(j).getLayoutY())
								&& ((pShoots.get(i).getLayoutY() < enemies1.get(j).getLayoutY() + 50))))) {

					pontos += 200;
					root.getChildren().remove(enemies1.get(j));
					enemies1.remove(j);
					root.getChildren().remove(pShoots.get(i));
					pShoots.remove(i);
				}
			}
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}