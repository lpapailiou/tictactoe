package main.java.root;

import main.java.root.rounds.Round;

public class Bot extends Thread implements Runnable {
	
	private Round round;
	private Object lock;
	private volatile boolean running = true;
	private volatile boolean paused = true;
	private volatile boolean blocked = false;
	
	public Bot(Round round, Object lock) {	
		this.round = round;
		this.lock = lock;
	}

	@Override
	public void run() {
		while (running) {
			// check if thread should wait or be interrupted
			synchronized (lock) {
				if (!running) {
					break;
				}
				if (paused || blocked) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						this.interrupt();
					}
				}
			}

			if(!blocked) {
				round.botStep();
			}
		}
	}
	
	public void requestStop() {
		running = false;
		requestResume();
	}
	
	public void requestPause() {
		paused = true;
	}
	
	public void requestResume() {
		synchronized (lock) {
			paused = false;
			lock.notifyAll();
		}
	}
	
	public void block(boolean blockthis) {
		this.blocked = blockthis;
	}
	
}
