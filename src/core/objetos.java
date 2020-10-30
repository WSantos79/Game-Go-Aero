package core;

/**
 * Classe que modela todos objetos do mundo 2d.
 * 
 * @author Wellington Santos - WSantos79
 */

public abstract class objetos {

	protected float posicaoX;
	protected float posicaoY;

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
