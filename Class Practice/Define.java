import java.util.Comparator;
public class Define implements Comparator<String> {
    @Override
    public int  compare(String i , String j) { 
        int q= 0;  
        int w= 0;

       if((i.substring(q)).equals(j.substring(w))){
            return 0;
       }

       else{
            return -1;
       }
 
}   
    public int  calc_len(String i_string){
        int length = 0;
		for (int i = 0;i_string !=null ; i++,length++) {
            
        }
        
		return length;
    }
}
