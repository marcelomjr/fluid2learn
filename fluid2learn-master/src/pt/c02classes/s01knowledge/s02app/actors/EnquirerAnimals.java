/* Classe Enquirer da Primeira Etapa do trabalho de MC302 */

package pt.c02classes.s01knowledge.s02app.actors;

import java.util.ArrayList;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IDeclaracao;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;
import pt.c02classes.s01knowledge.s01base.impl.Declaracao;

public class EnquirerAnimals implements IEnquirer
{
	IResponder responder;
	ArrayList <IDeclaracao> perguntasfeitas;
    IObjetoConhecimento obj;
    
    public void connect(IResponder responder) {
		this.responder = responder;
	}
	
    public boolean discover()
	{
		int animal, questao, total;
		boolean encontrou = false, perguntou;
        IBaseConhecimento bc = new BaseConhecimento();
        
        bc.setScenario("animals");
                
        /* Construcao de um ArrayList para arquivar as perguntas feitas */
        perguntasfeitas = new ArrayList<IDeclaracao>(); 
        
        /* Vetor que armazena a lista dos animais do jogo */
        String listaAnimais[] = bc.listaNomes();
        
        
        /* Considera um animal por vez como o correto, e utiliza suas propriedes para compara-las com as 
         * propriedades do animal escolhido do Responder, caso esse nao seja o correto, verifica-se o proximo */ 
        for (animal = 0; (animal < listaAnimais.length && encontrou == false); animal++)
        {
        	/* Recupera o arquivo texto de cada animal e o carrega no objeto "obj" */
        	obj = bc.recuperaObjeto(listaAnimais[animal]);
        	IDeclaracao decl = obj.primeira();
        	boolean animalEsperado = true;      	
        	
        	/* Enquanto existir declaracoes e ainda for possivel que o animal esperado seja o correto */
        	while (decl != null && animalEsperado)
        	{
        		/* Registra a propriedade na String "pergunta". */ 
        		String pergunta = decl.getPropriedade();
        		/* Armazena a valor da tal propriedade na String "respostaEsperada". */
    			String respostaEsperada = decl.getValor();
    			
    			/* Verifica se a pergunta jah nao foi realizada */
    			total = perguntasfeitas.size();
    			for (questao = 0, perguntou = false; (questao < total && !perguntou); questao++)
    			{
    				/* Caso alguma pergunta seja repetida */
    				if (pergunta.equalsIgnoreCase(perguntasfeitas.get(questao).getPropriedade()))
    					perguntou = true;	
    			}
    			String resposta;
    			/* Caso essa pergunta nao tenha sido feita ela deve ser 
    			 * realizada e armazenada no controle de perguntas feitas */
    			if (total == 0 || !perguntou)
    			{
    				/* Obtem a resposta do Responder e a salva na String "resposta". */
        			resposta = responder.ask(pergunta);
        			
        			/* Constroe um objeto chamado "novaResposta" para armazenar a propriedade e o valor */
        			IDeclaracao novaPergunta = new Declaracao(pergunta, resposta);
        			/* Adiciona esse objeto ao ArrayList das perguntas feitas */
        			perguntasfeitas.add(novaPergunta);
    			}
    			/* Nesse caso a pergunta nao deve ser feita para o Responder, 
    			 * jah que o Enquirer jah a possui em seu controle de perguntas */
    			else
    				resposta = perguntasfeitas.get(questao - 1).getValor();
    			
    			/* Caso a resposta dada pelo Responder seja a mesma da esperada 
    			 *  pelo Enquirer deve ser realizada a proxima pergunta */
    			if (resposta.equalsIgnoreCase(respostaEsperada))
    				decl = obj.proxima();
    			
    			/* Caso contrario, fica comprovado que o animal esperado nao eh o correto, 
    			 * portanto deve-se verificar o proximo animal do banco de dados do Enquirer */
    			else
    				animalEsperado = false;
    			}
        	
        	/* Se apos a verificacao de todas as perguntas nenhuma estiver 
        	 * incorreta, conclui-se que o animal esperado eh o correto */ 
        	if (animalEsperado)
        	{
        		/* Portanto a busca por ele se encerra aqui */
        		encontrou = true;
        	}
        }
        
		/* O metodo finalAnswer recebe o animal que o Enquirer 
		 * julga ser o correto e retorna se ele acertou ou nao */
		boolean acertei = responder.finalAnswer(listaAnimais[animal - 1]);
		
		/* Impressao do resultado conforme o desempenho do Enquirer */
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");
		
		return acertei;

	}

}