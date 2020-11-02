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
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class program extends Application {

	private final String IMG_FUNDO = "recursos/1280.png";
	private final String IMG_AVIAO = "recursos/aviao.png";

	private final Image imgFundo = new Image(getClass().getResourceAsStream(IMG_FUNDO));
	private final Image imgAviao = new Image(getClass().getResourceAsStream(IMG_AVIAO));

	private final ImageView viewFundo = new ImageView(imgFundo);
	private final ImageView viewAviao = new ImageView(imgAviao);

	private final world mundo = new world(1262, 825);
	private final aviao aviao0 = new aviao(600, 750);

	List<Circle> pShoots = new ArrayList<Circle>();
	Pane root = new Pane();
	AnimationTimer timer;

	@Override
	public void start(Stage primaryStage) {
		try {
			viewFundo.setTranslateX(0);
			viewFundo.setTranslateY(0);

			viewAviao.setTranslateX(aviao0.getPosicaoX());
			viewAviao.setTranslateY(aviao0.getPosicaoY());

			root.getChildren().add(viewFundo);
			root.getChildren().add(viewAviao);

			Scene scene = new Scene(root, mundo.DIM_X, mundo.DIM_Y);

			primaryStage.setTitle("Go Aero");
			primaryStage.setScene(scene);
			primaryStage.show();

			// animaçao
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

	public void gameUpdate() {

		// atualizaçao do tiro / animaçao
		aviaoShootUpdate();

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

	public static void main(String[] args) {
		launch(args);
	}
}