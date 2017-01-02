
class Test1 {
   int var = 1;
   Test1() {
     var = 2; 
     System.out.println(this.var);
     System.out.println(var);
   }
}

class Test2 {
   int var = 1;
   Test2() {
     int var = 2; 
     System.out.println(this.var); 
     System.out.println(var);
   }
}



class MyClass {
    public static void main(String[ ] args) {
        Test1 myTest1 = new Test1();     // displays 2 then 2
        System.out.println(myTest1.var); // displays 2
        
        System.out.println();
        
        Test2 myTest2 = new Test2();     // displays 1 then 2
        System.out.println(myTest2.var); // displays 1
    }
}
