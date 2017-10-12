package in.ac.iitk.cse.cs252.transactions;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import java.io.File;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.bitcoinj.script.ScriptOpCodes.*;

/**
 * Created by bbuenz on 24.09.15.
 */
public class LinearEquationTransaction extends ScriptTransaction {
    // TODO: Problem 2
    public LinearEquationTransaction(NetworkParameters parameters, File file, String password) {
        super(parameters, file, password);
    }

    @Override
    public Script createInputScript() {
        // TODO: Create a script that can be spend by two numbers x and y such that x+y=first 4 digits of your iitk roll and |x-y|=last 4 digits of your suid (perhaps +1)
    	ScriptBuilder builder = new ScriptBuilder();
        
    	builder.op(110); //110 is op for duplicating top 2 items
        builder.op(OP_SUB);
        builder.op(OP_ABS);
        
        ByteBuffer secondpart = ByteBuffer.allocate(4);
        secondpart.order(ByteOrder.LITTLE_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        secondpart.putInt(3168);
        
        byte[] secondpartroll = secondpart.array();
      
        builder.data(secondpartroll);
        builder.op(OP_NUMEQUALVERIFY);
        builder.op(OP_ADD);
        
        ByteBuffer firstpart = ByteBuffer.allocate(4);
        firstpart.order(ByteOrder.LITTLE_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        firstpart.putInt(1316);
        
        byte[] firstpartroll = firstpart.array();
      
        
    	builder.data(firstpartroll);
    	builder.op(OP_NUMEQUAL);
    	   
        return builder.build();
    }

    @Override
    public Script createRedemptionScript(Transaction unsignedScript) {
        // TODO: Create a spending script
    	
        return null;
    }

    private byte[] encode(BigInteger bigInteger) {
        return Utils.reverseBytes(Utils.encodeMPI(bigInteger, false));
    }
}
