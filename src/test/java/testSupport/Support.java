package testSupport;
import core.AttackStep;
import core.AttackStepMax;
import core.AttackStepMin;

public class Support {

   public Support() {
      // TODO Auto-generated constructor stub
   }

   public static void explain(AttackStep attackStep) {
      explain(attackStep, "");
   }

   private static void explain(AttackStep attackStep, String indent) {
      if (attackStep.ttc == AttackStep.infinity) {
         if (attackStep instanceof AttackStepMax) {
            System.out.println(indent + "didn't reach " + attackStep.assetName + " (" + attackStep.toString() + ") (AND) because ");
            for (AttackStep parent : attackStep.expectedParents) {
               explain(parent, indent + "  ");
            }
         }
         if (attackStep instanceof AttackStepMin) {
            System.out.println(indent + "didn't reach " + attackStep.assetName + " (" + attackStep.toString() + " (OR), because");
            if (attackStep.expectedParents.isEmpty()) {
               System.out.println(indent + "  that attack step has no parents.");
            }
            else {
               for (AttackStep parent : attackStep.expectedParents) {
                  explain(parent, indent + "  ");
               }
            }
         }
      }
   }

}
