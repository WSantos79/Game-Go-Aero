package core;

/**
 * Classe que modela o aviao principal.
 * 
 * @author Wellington Santos - WSantos79
 */
public class aviao extends objetos {

	private float velocidade = 50;

	public float getVelocidade() {
		return velocidade;
	}

	public int getPosicaoX() {
		return (int) this.posicaoX;
	}

	public int getPosicaoY() {
		return (int) this.posicaoY;
	}

	public void setPosicaoX(float posicaoX) {
		this.posicaoX = posicaoX;
	}

	public void setPosicaoY(float posicaoY) {
		this.posicaoY = posicaoY;
	}

	public aviao(float posX, float posY) {

		super.posicaoX = posX;
		super.posicaoY = posY;
	}

	/**
	 * Move o aviao para a posi��o x e y.
	 * 
	 * @param posX que o aviao ser� movido.
	 * @param posY que o aviao ser� movido.
	 * @throws IllegalArgumentException exce��o lan�ada quando o argumento for NaN
	 *                                  ou infinita
	 * 
	 */
	@Override
	public void move(float posX, float posY) {
		if (Float.isNaN(posX) || Float.isNaN(posY) || Float.isInfinite(posX) || Float.isInfinite(posY)) {
			throw new IllegalArgumentException("Argumentos n�o v�lidos");
		}
		super.posicaoX = posX;
		super.posicaoY = posY;

	}

	/**
	 * Move o aviao ao longo do eixo x.
	 * 
	 * @param dist dist�ncia a partir da posi��o atual no eixo x que ser� movido.
	 * @throws IllegalArgumentException exce��o lan�ada quando o argumento for NaN
	 *                                  ou infinito
	 */
	@Override
	public void moveX(float dist) {
		if (Float.isNaN(dist) || Float.isInfinite(dist)) {
			throw new IllegalArgumentException("Argumento n�o v�lido");
		}
		super.posicaoX += dist;
	}

	/**
	 * Move o aviao ao longo do eixo y.
	 * 
	 * @param dist dist�ncia a partir da posi��o atual no eixo y que ser� movido.
	 * @throws IllegalArgumentException exce��o lan�ada quando o argumento for NaN
	 *                                  ou infinito
	 */
	@Override
	public void moveY(float dist) {
		if (Float.isNaN(dist) || Float.isInfinite(dist)) {
			throw new IllegalArgumentException("Argumento n�o v�lido");
		}
		super.posicaoY += dist;

	}
}