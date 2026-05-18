package level1;

import java.util.ArrayList;
import java.util.List;

public class Ans1 {
    public static void main(String[] args) {
        List<String> students = new ArrayList<>();
        
        students.add("Jhon");
        students.add("Alisa");
        students.add("Micheal");
        students.add("Tony");
        students.add("Jenny");


        for (String s : students) {
            System.out.println(s);
        }

        System.out.println(students.get(2));
    }
}