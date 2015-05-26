package DFSPermutationThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class DFSPermutationThread {

	//private static List<String> itemList = new ArrayList(Arrays.asList("c", "a", "t", "s"));
	private static List<String> itemList = new ArrayList(Arrays.asList("m", "o", "n", "k", "e", "y", "s", "."));
	//private static List<String> itemList = new ArrayList(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "#", "*"));
	
	static ConcurrentLinkedDeque<dataStocker> stack = new ConcurrentLinkedDeque<dataStocker>();
	
	//private static int numThreadSize = Runtime.getRuntime().availableProcessors();
	public static int cnt = 0;
	
	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		
		dataStocker rootNode = new dataStocker();

		for (String itm : itemList){
			rootNode.targetList.add(itm);
		}

		stack.push(rootNode);
		//stack.addLast(seedDts);
		
		while(!stack.isEmpty()) {
			dataStocker node = stack.pop();
			//dataStocker tgtDts = stack.getFirst();
			SingleDepthPermutation(node);
		}
		long endTime = System.currentTimeMillis();
		long differenceTime = endTime - startTime;
		System.out.println("It took " + TimeUnit.MILLISECONDS.toSeconds(differenceTime) + " seconds.");
		System.out.println("Total : " + cnt + " patterns.");
		
		/*
		 // when no threading
		It took 3106 seconds.
		Total : 479001600 patterns.
		*/
	}
	

	static void SingleDepthPermutation(dataStocker node) {

		if (node.targetList.size() > 0)
		{
			
			 			
			List<String> nodes = node.targetList;
			
			
			ExecutorService executor = Executors.newFixedThreadPool(nodes.size());
			List<Callable<Object>>  todo = new ArrayList<Callable<Object>>(nodes.size());
			
			//ExecutorService executor = Executors.newFixedThreadPool(numThreadSize);
			//List<Callable<Object>>  todo = new ArrayList<Callable<Object>>(numThreadSize);

				 
			for (String __node : nodes)
			{
				
				dataStocker tmp_node = new dataStocker();
				tmp_node.res = node.res + __node;
				
				class myRunnable implements Runnable {
					
					String __item;
					List<String> __nodes;
					dataStocker __tmp_node;
					
					public myRunnable (String item, List<String> nodes, dataStocker tmp_node) {
						__item = item;
						__nodes = nodes;
						__tmp_node = (dataStocker) tmp_node.clone();
					}
					
					public void run(){
					
						for (int k=0; k < __nodes.size(); k++)
						{
		
							if (__item != __nodes.get(k))
							{
								__tmp_node.targetList.add(__nodes.get(k));
							}
					
						}
						
						stack.push(__tmp_node);
						//stack.addLast(__tmp_node);
					}
				}
				
				todo.add(Executors.callable(new myRunnable(__node, nodes, tmp_node)));

				
				/*
				if (numPooled == numTgtLstSize || numPooled == tgtLst.size() ) {
					try {
						List<Future<Object>> futures = executor.invokeAll(todo);
						executor.shutdown();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				*/
				
			}
			
			/* */
			try {
				List<Future<Object>> futures = executor.invokeAll(todo);
				executor.shutdown();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/* */	

			


		}
		else if  (node.targetList.size() <= 0)
		{
			System.out.println("cnt : " + cnt + ", result : " + node.res);
			cnt++;
		}
			
	}
}

