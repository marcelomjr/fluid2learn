package pt.c02classes.s01knowledge.s02app.actors;

import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerMaze implements IEnquirer
{
	private String localizacao, 
				   parede;
	
	IResponder responder;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	/** O seguinte metodo utiliza a estrategia de sempre acompanhar a parede para encontrar a saida do labirinto.
	 *  Dessa forma, o jogador percorre o labirinto no sentido horario, sempre considerendo a localizacao da parede externa.
	 */
	public boolean discover()
	{
		/* Determina a localizacao do jogador */
		localizacao = responder.ask("aqui");
		
		/* Repete enquanto nao encontrar a saida */
		while (!localizacao.equalsIgnoreCase("saida"))
		{
			/* Verifica se o jogador ja saiu da entrada*/
			if (!localizacao.equalsIgnoreCase("entrada"))
			{
				/* Verifica se a parede mudou de orintacao */
				if (!responder.ask(parede).equalsIgnoreCase("parede"))
				{
					/* Mova-se no direcao em que ela estava, pois isso eh uma "esquina",
					 * e o jogador nao deve deixar de saber a localizacao da parede */
					responder.move(parede);
					
					/* Atualiza o posicao da parede */
					switch (parede)
					{
					case "norte":
						parede = "oeste";
						break;
						
					case "leste":
						parede = "norte";
						break;
						
					case "sul":
						parede = "leste";
						break;
						
					default:
						parede = "sul";
					}
				}
				
				/* Caso a parede ainda se encontre na mesma orientacao */
				else
				{
					boolean moveu = false;
					
					/* Mova-se no labirinto, margeando seus limites, no sentido horario */
					/* Caso nao seja possivel se mover, deve se alterar o sentido da parede em 90 graus */
					switch (parede)
					{
					case "oeste":
						moveu = responder.move("norte");
						if (!moveu)
							parede = "norte";
						break;
							
					case "sul":
						moveu = responder.move("oeste");
						if (!moveu)
							parede = "oeste";
						break;
						
					case "norte":
						moveu = responder.move("leste");
						if (!moveu)
							parede = "leste";
						break;
						
					default:
						moveu = responder.move("sul");
						if (!moveu)
							parede = "sul";
					}
				}
			}
			
			/* Nesse caso, o jogador estah na entrada */
			else
			{
				/** Definicao da posicao inicial em relacao ao labirinto */
				
				/* Caso exista uma parede a esquerda */
				if (!responder.ask("oeste").equalsIgnoreCase("passagem"))
				{
					/* Verifica se a entrada esta na parte de baixo do labirinto */
					if (!responder.ask("sul").equalsIgnoreCase("passagem"))
					{
						responder.move("norte");
						parede = "oeste";
					}
					
					/* Nesse caso a entrada esta na parte de cima */
					else
					{
						responder.move("sul");
						parede = "leste";
					}
				}
				/* Caso a entrada esteja nas laterais do labirinto */
				else
				{
					/* Verifica se estah na esquerda */
					if (!responder.ask("oeste").equalsIgnoreCase("passagem"))
					{
						responder.move("leste");
						parede = "norte";
					}
					
					/* Nesse caso ela esta na direita */
					else
					{
						responder.move("oeste");
						parede = "sul";
					}
				}
			}
			/* Atualiza a posicao do jogador */
			localizacao = responder.ask("aqui");
		}
		
		/** Verifica se ele consegui sair */
		
		if (responder.finalAnswer("cheguei"))
			System.out.println("Você encontrou a saida!");
		else
			System.out.println("Fuém fuém fuém!");
		
		return true;
	}	
}