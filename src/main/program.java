package main;

/**
 * @author Wellington Santos - WSantos79
 * 
 * Version 1.0
 */

import core.world;
import core.aviao;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class program extends Application {

	private final String IMG_FUNDO = "recursos/1280.png";
	private final String IMG_AVIAO = "recursos/aviao.png";

	private final Image imgFundo = new Image(getClass().getResourceAsStream(IMG_FUNDO));
	private final Image imgAviao = new Image(getClass().getResourceAsStream(IMG_AVIAO));

	private final ImageView viewFundo = new ImageView(imgFundo);
	private final ImageView viewAviao = new ImageView(imgAviao);

	private final world mundo = new world(1262, 825);
	private final aviao aviao0 = new aviao(32, 300);

	@Override
	public void start(Stage primaryStage) {
		try {
			viewFundo.setTranslateX(0);
			viewFundo.setTranslateY(0);

			viewAviao.setTranslateX(aviao0.getPosicaoX());
			viewAviao.setTranslateY(aviao0.getPosicaoY());

			Group grupo = new Group();
			grupo.getChildren().add(viewFundo);
			grupo.getChildren().add(viewAviao);

			Scene scene = new Scene(grupo, mundo.DIM_X, mundo.DIM_Y);

			primaryStage.setTitle("Go Aero");
			primaryStage.setScene(scene);
			primaryStage.show();

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

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
