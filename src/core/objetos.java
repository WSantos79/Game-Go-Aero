package core;

/**
 * Classe que modela todos objetos do mundo 2d.
 * 
 * @author Wellington Santos - WSantos79
 */

public abstract class objetos {

	protected float posicaoX;
	protected float posicaoY;
	private final int altura = 72;
	private final int largura = 60;

	public boolean avaliaPosicao(int posX, int posY) {
		posX = posX + largura / 2;
		posY = posY + altura / 2;

		// lado << || lado >> || lado ^^^ || lado vvv
		if (posX < 30 || posX > 1279.90 || posY < 30 || posY > 835.00) {
			return false; // Delimitação da janela do jogo
		}

		return true;
	}

	/**
	 * @param posX posição x em que o objeto será movido.
	 * @param posY posição y em que o objeto será movido.
	 */
	public abstract void move(float posX, float posY);

	/**
	 * @param dist distância a partir da posição atual no eixo x que o objeto será
	 *             movido.
	 */
	public abstract void moveX(float dist);

	/**
	 * 
	 * @param dist distância a partir da posição atual no eixo y que o objeto será
	 *             movido.
	 */
	public abstract void moveY(float dist);
}