//@author: Daiane Tararam

package controller;

import java.util.concurrent.Semaphore;

public class ThreadsSimultaneas extends Thread {
	private Semaphore semaforo;
	private int idThread;

	public ThreadsSimultaneas(int idThread, Semaphore semaforo) {
		this.semaforo = semaforo;
		this.idThread = idThread;
	}

	@Override
	public void run() {
		int restoDivisao = idThread % 3;
		try{
			if(restoDivisao == 1) {
				executarCalculo(0.2, 1.0);
				transacaoBD(1);
				executarCalculo(0.2, 1.0);
				transacaoBD(1);
			}else if (restoDivisao == 2) {
				executarCalculo(0.5, 1.5);
				transacaoBD(1.5);
				executarCalculo(0.5, 1.5);
				transacaoBD(1.5);
				executarCalculo(0.5, 1.5);
				transacaoBD(1.5);
			}else if (restoDivisao == 0) {
				executarCalculo(1, 2);
				transacaoBD(1.5);
				executarCalculo(1, 2);
				transacaoBD(1.5);
				executarCalculo(1, 2);
				transacaoBD(1.5);
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void executarCalculo(double tempoMin, double tempoMax) throws InterruptedException{
		double duracao = tempoMin + (tempoMax - tempoMin) * Math.random();
		System.out.printf("Thread %d: Realizando cálculo por %.2f segundos.%n", idThread, duracao);
		Thread.sleep((long) (duracao * 1000));
		System.out.printf("Thread %d: CONCLUIU O CÁLCULO!.%n", idThread);
	}

	private void transacaoBD(double duracao)  {
		try {
			semaforo.acquire();
			System.out.printf("Thread %d: Iniciando transação de BD por %.2f segundos.%n", idThread, duracao);
            Thread.sleep((long) (duracao * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			semaforo.release();
            System.out.printf("Thread %d: Finalizou a transação de BD.%n", idThread);
		}
		

	}
}
