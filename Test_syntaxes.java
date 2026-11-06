public class Instructor_Dashboard implements Runnable {
    
    public void run (){
        
        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};

            for (int i = 0; i < 100; i++) {
                System.out.println(cars[i]);
            }
    }
}
