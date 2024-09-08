package view;

import java.util.concurrent.Semaphore;

import controller.ThreadsSimultaneas;
public class Main {

	public static void main(String[] args) {
				
		Semaphore semaforo = new Semaphore(1);
		for(int idThread = 1; idThread <= 21; idThread++) {
			Thread tOperacao = new ThreadsSimultaneas(idThread, semaforo);
			tOperacao.start();
		}
	}

}
