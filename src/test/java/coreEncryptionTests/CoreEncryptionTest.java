package coreEncryptionTests;

import org.junit.Test;
import org.junit.After;

import auto.*;
import core.*;

public class CoreEncryptionTest {

   @Test
   public void testKeyDecryption() {
      EncryptedData encryptedData = new EncryptedData("EncryptedData");
		CryptographicKey decryptionKey = new CryptographicKey("DecryptionKey");

		encryptedData.addDecryptionKeys(decryptionKey);
	
      Attacker attacker = new Attacker();
      attacker.addAttackPoint(encryptedData.authenticatedRead);
      attacker.addAttackPoint(decryptionKey.read);
      
      attacker.attack();

      encryptedData.readEncrypted.assertCompromisedInstantaneously();
      encryptedData.read.assertCompromisedInstantaneously();
	}

   @Test
   public void testEncryption() {
      EncryptedData encryptedData = new EncryptedData("EncryptedData");

      Attacker attacker = new Attacker();
      attacker.addAttackPoint(encryptedData.authenticatedRead);
      
      attacker.attack();

		encryptedData.decryptionKeysExist.disable.assertUncompromised();
		encryptedData.readEncrypted.assertUncompromised();
      encryptedData.read.assertUncompromised();
	}


   @Test
   public void testSymmetricEncryption() {
		Information secretInformation = new Information("SecretInformation");
      EncryptedData encryptedData = new EncryptedData("EncryptedData");
		CryptographicKey symmetricKey = new CryptographicKey("SymmetricKey");
		Machine storageMachine = new Machine("StorageMachine");
		Account user = new Account("User");

		storageMachine.addData(encryptedData);
		encryptedData.addInformation(secretInformation);
		encryptedData.addDecryptionKeys(symmetricKey);
		encryptedData.addEncryptionKeys(symmetricKey);
		storageMachine.addAccounts(user);
		encryptedData.addReadingAccounts(user);
		encryptedData.addWritingAccounts(user);	

      Attacker attacker = new Attacker();
      attacker.addAttackPoint(user.compromise);
      attacker.addAttackPoint(symmetricKey.read);
		attacker.addAttackPoint(storageMachine.connect);      

      attacker.attack();

		storageMachine.access.assertCompromisedInstantaneously();
		encryptedData.requestAccess.assertCompromisedInstantaneously();
		encryptedData.anyAccountWrite.assertCompromisedInstantaneously();
      encryptedData.readEncrypted.assertCompromisedInstantaneously();
      encryptedData.read.assertCompromisedInstantaneously();
		secretInformation.read.assertCompromisedInstantaneously();
		secretInformation.write.assertCompromisedInstantaneously();
	}

	@After
	public void deleteModel() {
		Asset.allAssets.clear();
		AttackStep.allAttackSteps.clear();
		Defense.allDefenses.clear();
	}


}


