public abstract class FlashAnimation {

	private long totalTime;
	private long flashInterval;
	
	public FlashAnimation(long totalTime, long flashInterval)
	{
		this.totalTime = totalTime;
		this.flashInterval = flashInterval;
	}
	
	protected abstract void doAnimate();
	protected abstract void unAnimate();
	
	public void animate() throws Exception
	{
		new Thread(new Runnable()
		{
			@Override
			public void run() {
				long endTime = System.currentTimeMillis() + totalTime;
				while(System.currentTimeMillis() < endTime)
				{
					doAnimate();
					sleep(flashInterval);
					unAnimate();
				}
			}
		}).start();
		
	}
	
	private static void sleep(long flashInterval)
	{
		try {
			Thread.sleep(flashInterval);
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}	
	}
}


