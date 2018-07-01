package coreMachineTests;

import org.junit.Test;
import org.junit.After;

import auto.*;
import core.*;

public class CoreMachineTest {

   @Test
   public void testMachineAccess() {

      Machine machine = new Machine();

      Attacker attacker = new Attacker();
      attacker.addAttackPoint(machine.connect);
      attacker.addAttackPoint(machine.authenticate);
      
		attacker.attack();

      machine.access.assertCompromisedInstantaneously();
      machine.denialOfService.assertCompromisedInstantaneously();
   }

   @Test
   public void testSoftwareHostToGuest() {

      Machine machine = new Machine("Machine");
		Software software1 = new Software("Software1");
		Software software2 = new Software("Software2");
		Account account = new Account("Account");

		machine.addAccounts(account);
		software1.addExecutor(machine);
		software2.addExecutor(machine);
		software1.addAccounts(account);

      Attacker attacker = new Attacker();
      attacker.addAttackPoint(machine.connect);
      attacker.addAttackPoint(account.compromise);
      
		attacker.attack();

		machine.access.assertCompromisedInstantaneously();
      software1.connect.assertCompromisedInstantaneously();
      software1.access.assertCompromisedInstantaneously();
		software2.connect.assertCompromisedInstantaneously();
		software2.access.assertUncompromised();
		
   }

   @Test
   public void testSoftwareGuestHost() {

      Machine machine = new Machine("Machine12");
		Software software = new Software("Software123");

		software.addExecutor(machine);

      Attacker attacker = new Attacker();
      attacker.addAttackPoint(software.connect);
      attacker.addAttackPoint(software.authenticate);
      
		attacker.attack();

		software.access.assertCompromisedInstantaneously();
      machine.connect.assertCompromisedInstantaneously();
      machine.access.assertUncompromised();
   }

   @Test
   public void testMachineAccountDataRWD() {


      Machine machine = new Machine("Machine");
		Account account = new Account("Account");
		Data data = new Data("Data");
	
		machine.addAccounts(account);
   	machine.addData(data);   
		account.addReadData(data);
	
		Attacker attacker = new Attacker();
      attacker.addAttackPoint(machine.connect);
		attacker.addAttackPoint(account.compromise);      

		attacker.attack();

      data.requestAccess.assertCompromisedInstantaneously();
      data.anyAccountRead.assertCompromisedInstantaneously();
      data.read.assertCompromisedInstantaneously();
      data.anyAccountWrite.assertUncompromised();
      data.write.assertUncompromised();
   }

   @Test
   public void testStealCredsReadData() {


      Machine machine = new Machine("Machine");
		Account userAccount = new Account("UserAccount");
		Account hacker = new Account("Hacker");
		Credentials creds = new Credentials("Credentials");
		Data data = new Data("Data");
		
		userAccount.addCredentials(creds);
		machine.addAccounts(userAccount);
		machine.addAccounts(hacker);
   	machine.addData(data);   
		userAccount.addReadData(data);
		machine.addData(creds);
		hacker.addReadData(creds);
	
		Attacker attacker = new Attacker();
      attacker.addAttackPoint(machine.connect);
		attacker.addAttackPoint(hacker.compromise);      

		attacker.attack();

		creds.requestAccess.assertCompromisedInstantaneously();
		creds.read.assertCompromisedInstantaneously();
		userAccount.compromise.assertCompromisedInstantaneously();
		data.requestAccess.assertCompromisedInstantaneously();
      data.anyAccountRead.assertCompromisedInstantaneously();
      data.read.assertCompromisedInstantaneously();
      data.anyAccountWrite.assertUncompromised();
      data.write.assertUncompromised();
   }

   @Test
   public void testCompromiseAuthenticationService() {

		AuthenticationService ad = new AuthenticationService("AD");
		Account domainAdmin = new Account("DomainAdmin");
		Account userAccount = new Account("UserAccount");

		ad.addAccounts(domainAdmin);
		ad.addAuthenticatedAccounts(userAccount);

      Attacker attacker = new Attacker();
      attacker.addAttackPoint(ad.connect);
      attacker.addAttackPoint(domainAdmin.compromise);
      
		attacker.attack();

      userAccount.compromise.assertCompromisedInstantaneously();
   }


   @Test
   public void testReadDataOnAWSInstance() {


      Machine instance = new Machine("Instance");
      Account sshKeyAccount = new Account("SSHKeyAccount");
      Data someData = new Data("SomeData");

      instance.addAccounts(sshKeyAccount);
      instance.addData(someData);
      sshKeyAccount.addReadData(someData);
      sshKeyAccount.addWrittenData(someData);
      sshKeyAccount.addDeletedData(someData);

      Attacker attacker = new Attacker();
      attacker.addAttackPoint(instance.connect);
      attacker.addAttackPoint(sshKeyAccount.compromise);

      attacker.attack();

      instance.access.assertCompromisedInstantaneously();
		someData.requestAccess.assertCompromisedInstantaneously();
      someData.anyAccountRead.assertCompromisedInstantaneously();
      someData.read.assertCompromisedInstantaneously();
      someData.anyAccountWrite.assertCompromisedInstantaneously();
      someData.write.assertCompromisedInstantaneously();
   }

	@After
	public void deleteModel() {
		Asset.allAssets.clear();
		AttackStep.allAttackSteps.clear();
		Defense.allDefenses.clear();
	}


}


