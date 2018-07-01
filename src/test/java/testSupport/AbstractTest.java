package testSupport;
import org.junit.After;

import core.Asset;
import core.AttackStep;
import core.Component;

public class AbstractTest {

   @After
   public void tearDown() {
      Component.allComponents.clear();
      Asset.allAssets.clear();
      AttackStep.allAttackSteps.clear();
   }

}
