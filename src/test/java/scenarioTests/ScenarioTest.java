package scenarioTests;

import org.junit.Test;
import org.junit.After;

import auto.*;
import core.*;

public class ScenarioTest {

   @Test
   public void test1() {
		User hacker = new User("Hacker");
		Account hackerAccount = new Account("HackerAccount");
		Machine hackBook = new Machine("HackBook");
		Software hackOS = new Software("HackOS");
		NetworkService metasploit = new NetworkService("Metasploit");
		hacker.addAccounts(hackerAccount);
		hackOS.addExecutor(hackBook);
		hackOS.addAccounts(hackerAccount);
		metasploit.addExecutor(hackOS);
		metasploit.addAccounts(hackerAccount);

		NetworkClient firefoxWebContentProcess = new NetworkClient("FireFoxWebContentProcess");
		Account webServerPrivileges = new Account("WebServerPrivileges");
		firefoxWebContentProcess.addConnectPrivileges(webServerPrivileges);
		firefoxWebContentProcess.addAccounts(webServerPrivileges);
		
		Client firefox = new Client("Firefox");
		firefoxWebContentProcess.addExecutor(firefox);	
		Account firefoxPrivileges = new Account("FirefoxPrivileges");
		firefox.addAccounts(firefoxPrivileges);
	
		Vulnerability webIDLVulnerability = new Vulnerability("WebIDLVulnerability");
		firefoxWebContentProcess.addAccessVulnerabilities(webIDLVulnerability);
		webIDLVulnerability.addPrivileges(firefoxPrivileges);

		Dataflow https = new Dataflow("https");
		metasploit.addDataflows(https);
		firefoxWebContentProcess.addDataflows(https);

      Attacker attacker = new Attacker();
		attacker.addAttackPoint(hacker.compromise);
      attacker.addAttackPoint(hackBook.access);
      
      attacker.attack();

		hackerAccount.compromise.assertCompromisedInstantaneously();
      hackOS.connect.assertCompromisedInstantaneously();
		hackOS.authenticate.assertCompromisedInstantaneously();
		metasploit.access.assertCompromisedInstantaneously();
		https.respond.assertCompromisedInstantaneously();
		firefoxWebContentProcess.connect.assertCompromisedInstantaneously();
		webServerPrivileges.compromise.assertCompromisedInstantaneously();
		firefoxWebContentProcess.access.assertCompromisedInstantaneously();
		webIDLVulnerability.exploit.assertCompromisedWithEffort();
		firefoxPrivileges.compromise.assertCompromisedWithEffort();
		firefox.access.assertCompromisedWithEffort();
	}


	@After
	public void deleteModel() {
		Asset.allAssets.clear();
		AttackStep.allAttackSteps.clear();
		Defense.allDefenses.clear();
	}


}


