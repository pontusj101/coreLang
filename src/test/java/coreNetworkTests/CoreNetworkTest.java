package coreNetworkTests;

import org.junit.Test;
import org.junit.After;

import auto.*;
import core.*;

public class CoreNetworkTest {

   @Test
   public void testRouterAccess() {
      Router router = new Router();

      Attacker attacker = new Attacker();
      attacker.addAttackPoint(router.connect);
      attacker.addAttackPoint(router.authenticate);
      
      attacker.attack();

      router.access.assertCompromisedInstantaneously();
      router.denialOfService.assertCompromisedInstantaneously();
   	router.forwarding.assertCompromisedInstantaneously();
	}

   @Test
   public void testDataflow1() {
      Dataflow dataflow = new Dataflow("Dataflow");
		NetworkClient client = new NetworkClient("Client");
		NetworkService service = new NetworkService("Service");

		client.addDataflows(dataflow);
		service.addDataflows(dataflow);

      Attacker attacker = new Attacker();
      attacker.addAttackPoint(client.access);
	
      attacker.attack();

      dataflow.request.assertCompromisedInstantaneously();
      service.connect.assertCompromisedInstantaneously();
	}

   @Test
   public void testMultiDataflowRequest() {
      Dataflow dataflow = new Dataflow("Dataflow");
		NetworkClient client1 = new NetworkClient("Client1");
		NetworkClient client2 = new NetworkClient("Client2");
		NetworkService service1 = new NetworkService("Service1");
		NetworkService service2 = new NetworkService("Service2");

		client1.addDataflows(dataflow);
		client2.addDataflows(dataflow);
		service1.addDataflows(dataflow);
		service2.addDataflows(dataflow);

      Attacker attacker = new Attacker();
      attacker.addAttackPoint(client1.access);
	
      attacker.attack();

      dataflow.request.assertCompromisedInstantaneously();
      service1.connect.assertCompromisedInstantaneously();
      service2.connect.assertCompromisedInstantaneously();
		client2.connect.assertUncompromised();
	}

   @Test
   public void testMultiDataflowResponse() {
      Dataflow dataflow = new Dataflow("Dataflow");
		NetworkClient client1 = new NetworkClient("Client1");
		NetworkClient client2 = new NetworkClient("Client2");
		NetworkService service1 = new NetworkService("Service1");
		NetworkService service2 = new NetworkService("Service2");

		client1.addDataflows(dataflow);
		client2.addDataflows(dataflow);
		service1.addDataflows(dataflow);
		service2.addDataflows(dataflow);

      Attacker attacker = new Attacker();
      attacker.addAttackPoint(service1.access);
	
      attacker.attack();

      dataflow.respond.assertCompromisedInstantaneously();
      dataflow.request.assertUncompromised();
      client1.connect.assertCompromisedInstantaneously();
      client2.connect.assertCompromisedInstantaneously();
		service2.connect.assertUncompromised();
	}

   @Test
   public void testMitmNetwork1() {
      Dataflow dataflow = new Dataflow("Dataflow");
		NetworkClient client = new NetworkClient("Client");
		NetworkService service = new NetworkService("Service");
		Network network = new Network("Network");

		client.addDataflows(dataflow);
		service.addDataflows(dataflow);
		network.addDataflows(dataflow);

      Attacker attacker = new Attacker();
      attacker.addAttackPoint(network.manInTheMiddle);
	
      attacker.attack();

      dataflow.manInTheMiddle.assertCompromisedInstantaneously();
      dataflow.request.assertCompromisedInstantaneously();
      service.connect.assertCompromisedInstantaneously();
	}


	@After
	public void deleteModel() {
		Asset.allAssets.clear();
		AttackStep.allAttackSteps.clear();
		Defense.allDefenses.clear();
	}


}


