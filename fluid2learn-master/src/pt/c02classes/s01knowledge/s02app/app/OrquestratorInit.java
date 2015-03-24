package pt.c02classes.s01knowledge.s02app.app;

import java.util.Scanner;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.impl.Statistics;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;
import pt.c02classes.s01knowledge.s01base.inter.IStatistics;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerAnimals;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerMaze;
import pt.c02classes.s01knowledge.s02app.actors.ResponderAnimals;
import pt.c02classes.s01knowledge.s02app.actors.ResponderMaze;

public class OrquestratorInit {

	public static void main(String[] args)
	{
		IEnquirer enquirer;
		IResponder responder;
		IStatistics statistics;
		boolean achou = false;
		Scanner scanner = new Scanner(System.in);
		
		/* Objeto para o acesso ao banco de dados */
		IBaseConhecimento base = new BaseConhecimento();
				
		System.out.println("Digite o nome do jogo desejado, (A)nimals ou (M)aze:");
		String jogo = scanner.nextLine();
		
		switch (jogo.toUpperCase())
		{
		/* Caso o Animals Game seja selecionado */
		case "A":
		{
			/* Define a base de dados que sera utilizada */
			base.setScenario("animals");
			
			String listaAnimais[] = base.listaNomes();
			
			System.out.println("Digite o nome do animal a ser adivinhado:");
			String animal = scanner.nextLine();
			
			/* Verifica se esse animal existe na base da dados */
	        for (int busca = 0; busca < listaAnimais.length && !achou; busca++)
	        {
	        	if (listaAnimais[busca].equalsIgnoreCase(animal))
	        		achou = true;
	        }
	        if (achou)
	        {
				System.out.println("Enquirer com " + animal + "...");
				
				/* Instancia os objetos que serao utilizados */
				statistics = new Statistics();
				responder = new ResponderAnimals(statistics, animal);
				enquirer = new EnquirerAnimals();
				
				/* Utiliza as informacoes do "responder" no "EnquirerAnimals" */
				enquirer.connect(responder);
				enquirer.discover();
	        }
	        
	        /* Caso nao tenha achado o animal */
	        else
	        {
	        	System.out.println("Animal nao encontrado na base de dados!");
	        }
	        break;
		}
		
		/* Nesse caso o jogo selecionado eh o Maze */
		case "M":
		{
			/* Definicao da base de dados para o jogo escolhido */
			base.setScenario("maze");
			String listaMazes[] = base.listaNomes();
			
			System.out.println("Digite o nome do labirinto que serah utilizado:");
			String maze = scanner.nextLine();
			
			/* Verifica se esse maze existe na base da dados */
			for (int busca = 0; busca < listaMazes.length && !achou; busca++)
			{
				if (listaMazes[busca].equalsIgnoreCase(maze))
	        		achou = true;
	        }
			
			/* Se o maze foi localizado */
			if (achou)
			{
				System.out.println("Enquirer com " + maze + "...");
				
				/* Instancia os objetos que serao utilizados */
				statistics = new Statistics();
				responder = new ResponderMaze(statistics, maze);
				enquirer = new EnquirerMaze();
				
				/* comunicacao entre o enquirer e o responder */
				enquirer.connect(responder);
				enquirer.discover();
			}
			
			/* Nesse caso o maze nao foi achado */
			else
			{
				System.out.println("Maze nao encontrado na base de dados!");
			}
			break;
		}

		default:
			System.out.println("Jogo nao identificado!");
			
		}
<<<<<<< HEAD
=======
			
>>>>>>> ee2560e51509aee61eaf41d3cbe3c05f8df9d617
		scanner.close();
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.println("##### GAME OVER #####");
	}

}