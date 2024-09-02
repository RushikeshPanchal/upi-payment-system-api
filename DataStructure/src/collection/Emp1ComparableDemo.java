package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Emp1 implements  Comparable<Emp1>{
    int id;
    String name;
    String dept;
    int age;

    public Emp1(int id, String name, String dept, int age){
        this.id = id;
        this.name = name;
        this.dept =dept;
        this.age =age;
    }
    // based on Id sorted
    @Override
    public int compareTo(Emp1 e) {
        if(id==e.id){
            return 0;
        } else if(id > e.id){
            return 1;
        } else {
            return -1;
        }
    }
}

public class Emp1ComparableDemo {
    public static void main(String[] args) {

        List<Emp1> al = new ArrayList<Emp1>();

        al.add(new Emp1(105,"Vijay", "DevOps",23));
        al.add(new Emp1(102,"Ajay","Dev",27));
        al.add(new Emp1(101,"Jai","Tester",21));

        Collections.sort(al);

        for(Emp1 e : al){
            System.out.println("id: "+ e.id+ " Name: "+e.name+ " Dept: "+e.dept+" Age: "+e.age);
        }
    }
}
