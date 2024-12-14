// collection is a parent interface
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class Practice_1 {  
    public static void main(String[] args) {
        //Collection c = new ArrayList();
        List<String> c = new ArrayList();
        int temp_int = 0;
        int i = 0;
        // Array is not dynamic that is not store more than defined size
        // List interface is child interface of Collection
        // ArrayList is not sutiable for frequent add and delete
    //     Object val[] = new Object[5];
    //     c.add(20);
    //    // abcdefghijkmnopqrstuvwxyz  zyxwvutsrqponmlkjihgfedcba                // Array can't store different data types
    //     c.add(30.4f);
    //     c.add("rithul");
    //     System.out.println(c);
    //     System.out.println(c.size());
        c.add("206");
        c.add("331");
        c.add("114");
        c.add("417");
        
        Comparator d = new Define();
        Collections.sort(c, d);
        // Iterator i = c.iterator();
        // while(i.hasNext()){
        //     System.out.println(i.next());
        // }
        // for (Object elem : c) {
        //  System.out.println(elem);   
        // }
        System.out.println(c);


    }   
}
