package collection;

// 1,2,3,4,5, = 15, 3 missed
// 1,2,4,5 = 12
// sum2-sum1 = 3


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// forEach to print void method in java we can't assign to any variable
public class ListIntegerDemo8 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(9,12,54,18,10,17,9,87,12,57,17);
        Set<Integer> set = new HashSet<>();

         //check number starts with 1
        list.stream().map(x->x+"").filter(x->x.startsWith("1")).forEach(System.out::println);  // x.equal(1) will produce 1
          // checking even number from list
         list.stream().filter(x->x%2==0).forEach(System.out::println);

        // print duplicate element in list & use set for duplicate remove x-> !set.add(x), x is present in set return boolean as true
        list.stream().filter(x-> !set.add(x)).forEach(System.out::println);
        // print unique element & remove duplicate
        list.stream().filter(x-> set.add(x)).forEach(System.out::println);

        // find first element in given list
        Integer firstNum = list.stream().findFirst().get();
       // System.out.println("First number in list : "+firstNum);
         // second approach by ifPresent() method in Optional class, if value not present then return empty
        list.stream()
                .findFirst()
                .ifPresent(System.out::println);

       Long count =  list.stream().count();
       // System.out.println("total number in list: "+count);

        // find max number in list
      Integer maxNum = list.stream().max(Comparator.comparingInt(x->x)).get();
       // Integer maxNum2 = list.stream().max(Integer::compare).get(); 2nd way of max function
       // System.out.println("Max number in list: "+maxNum);

        // sort ascending order
        List<Integer> sortedList = list.stream().sorted().toList();
       // System.out.println(sortedList);
       // list.stream().sorted().forEach(System.out::println);  // second approach
        // sort descending order
        list.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);


     /*   // Map Iteration with List of String

        Map<String, List<String>> mapIterate = new HashMap<>();
        // create list of electronic devices category
        List<String> mobile = Arrays.asList("Iphone", "Samsung", "Vivo","Redmi");
        mapIterate.put("Mobiles",mobile);*/
    }
}
