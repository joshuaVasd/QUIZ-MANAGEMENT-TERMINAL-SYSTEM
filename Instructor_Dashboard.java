import java.util.*;

class Instructor_Dashboard {
    
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        
            ArrayList <String> enterSubjects = new ArrayList <String> ();

            Instructor_Dashboard_OBJECTS Main = new Instructor_Dashboard_OBJECTS();

                System.out.println("\n===SELECT TASK===");
                System.out.println("\n1. Create Quiz");
            
                System.out.print("\nEnter choice: ");
                String taskNum = sc.next();
            
                switch(taskNum){
                
                    case "1":
                        
                        System.out.print("Enter Subject: ");
                            String enterSubject = sc.next();
                            System.out.println("Subject Selected: "+ enterSubject);

                        System.out.println("\nChoose Quiz Type: ");
                            
                            System.out.println("\n1. MULTIPLE CHOICE");
                            System.out.println("2. TRUE OR FALSE");
                                
                                System.out.print("\nEnter Quiz Type: ");
                                String enterQuizType = sc.next();
                                    
                                    switch(enterQuizType){

                                        case "1":
                                            System.out.print("Enter number of items: (1-30): ");
                                             Main.enterNumItems = sc.nextInt();

                                                while(Main.enterNumItems < 1 || Main.enterNumItems > 30){
                                                    System.out.println("\nInvalid no. of Items!! ");

                                                    System.out.print("\nPlease re-enter no. of items! (1-30 only): ");
                                                    Main.enterNumItems = sc.nextInt();

                                                        if(Main.enterNumItems >=1 && Main.enterNumItems <=30){
                                                            break;
                                                        }
                                                }
                                            
                                            
                                            break;
                                        
                                        case "2":
                                            System.out.print("Enter number of items: (1-30): ");
                                             Main.enterNumItems = sc.nextInt();

                                                while(Main.enterNumItems < 1 || Main.enterNumItems > 30){
                                                    System.out.println("\nInvalid no. of Items!! ");

                                                    System.out.print("\nPlease re-enter no. of items! (1-30 only): ");
                                                    Main.enterNumItems = sc.nextInt();

                                                        if(Main.enterNumItems >=1 && Main.enterNumItems <=30){
                                                            break;
                                                        }
                                                }
                                            
                                            
                                            break;


                                                //======FOR QUIZ TYPE==========


                                        default:
                                            System.out.println("Invalid Choice!! ");
                                    }



                                    //======FOR SELECT TASK==========
                    break; 

                    default:
                        System.out.print("\nInvalid Choice!!");
                }

                System.out.print("Enter Timer: (in seconds, equal to or between 60 and 1200) meaning: 60s x 5m = 300 [value]: ");
                int[] quizTimer = {sc.nextInt()};

            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                
                @Override 
                    
                    public void run(){
                        
                        while(quizTimer[0] < 60 || quizTimer[0] > 1200){ //1200 = 20m
                            System.out.print("\nPlease enter values between 60 and 1200!!\n");
                                
                                if(quizTimer[0] >= 60 || quizTimer[0] <= 1200){
                                    
                                    quizTimer[0]--;
                                    
                                    timer.cancel();
                                    
                                    sc.close();    
                                }       
                        }       //use java thread to make it run in BG
                    }
            };                  timer.schedule(task, 0, 1000);
                                
    }

}
