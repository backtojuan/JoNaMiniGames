//_________________________________________________________________________________________________________________________________________
	package threads;
//_________________________________________________________________________________________________________________________________________
	import gui.gamegui.GameController;
//_________________________________________________________________________________________________________________________________________
	public class LoadingThread extends Thread{
		
		private GameController gamecontroller;
	//_____________________________________________________________________________________________________________________________________
		public LoadingThread(GameController gamecontroller) {
			this.gamecontroller = gamecontroller;
		}
	//_____________________________________________________________________________________________________________________________________
		@Override
		public void run() {
			while(true) {
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}	
//_________________________________________________________________________________________________________________________________________
}
	