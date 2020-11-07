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
			return false; // Delimita��o da janela do jogo
		}

		return true;
	}

	/**
	 * @param posX posi��o x em que o objeto ser� movido.
	 * @param posY posi��o y em que o objeto ser� movido.
	 */
	public abstract void move(float posX, float posY);

	/**
	 * @param dist dist�ncia a partir da posi��o atual no eixo x que o objeto ser�
	 *             movido.
	 */
	public abstract void moveX(float dist);

	/**
	 * 
	 * @param dist dist�ncia a partir da posi��o atual no eixo y que o objeto ser�
	 *             movido.
	 */
	public abstract void moveY(float dist);
}