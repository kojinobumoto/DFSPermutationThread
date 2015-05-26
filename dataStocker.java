package DFSPermutationThread;

import java.util.ArrayList;
import java.util.List;

public class dataStocker implements Cloneable {

	String res = "";
	List<String> targetList = new ArrayList<String>();
	
	public Object clone(){
        try{
            return super.clone();
        }catch(CloneNotSupportedException e){
            throw new InternalError(e.toString());
        }
    }
}
