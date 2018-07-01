package testSupport;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses();

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }

      System.out.println("Executed " + result.getRunCount() + " cases.");
       if (result.wasSuccessful()) {
         System.out.println("All were successful.");
      }
      else {
         System.out.println("Some failed.");
      }

   }
}
