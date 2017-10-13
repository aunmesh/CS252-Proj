import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Address;

class GenerateAddress {
  public static void main(String[] args)
  {

    NetworkParameters main = MainNetParams.get(); // main bitcoin network

    String target="1unmeasdfasdfaasaaaadasds11111111";

    byte[] bytes25 = Base58.decode(target);
    System.out.println("Size = " + bytes25.length);

    //checking first byte is 0x00
    System.out.println("bytes25[0] = " + bytes25[0]);
    
    // retrieving the first 21 bytes
    byte[] bytes21 = new byte[21];
    
    System.arraycopy(bytes25, 0, bytes21, 0, 21);

    // Let us get the double Sha256 hash of these 21 bytes
    byte[] hash = Sha256Hash.hashTwice(bytes21); 

    // Let us create the bytes of the address
    byte[] addr = new byte[25];

    // copying first 21 bytes to address
    System.arraycopy(bytes21, 0, addr, 0, 21);

    // appending first 4 bytes of hash to address
    System.arraycopy(hash, 0, addr, 21, 4);

    // encoding this 25 bytes address in Base 58
    String strAddr = Base58.encode(addr);

    System.out.println("Address = " + strAddr);

    Address address = Address.fromBase58(main, strAddr); 

  }
}
