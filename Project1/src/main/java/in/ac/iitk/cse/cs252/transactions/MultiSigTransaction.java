package in.ac.iitk.cse.cs252.transactions;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import java.io.File;
import java.math.BigInteger;

import static org.bitcoinj.script.ScriptOpCodes.*;

/**
 * Created by bbuenz on 24.09.15.
 */
public class MultiSigTransaction extends ScriptTransaction {
    // TODO: Problem 3
        private DeterministicKey keyBank;
        private DeterministicKey keyCustomerA;
        private DeterministicKey keyCustomerB;
        private DeterministicKey keyCustomerC;


    public MultiSigTransaction(NetworkParameters parameters, File file, String password) {
        super(parameters, file, password);

    }

    @Override
    public Script createInputScript() {
        // TODO: Create a script that can be spend using signatures from the bank and one of the customers

    	ScriptBuilder builder = new ScriptBuilder();
        builder.op(OP_CHECKSIGVERIFY);

		ByteBuffer m = ByteBuffer.allocate(4);
        m.order(ByteOrder.LITTLE_ENDIAN);
        m.putInt(1);
        
        byte[] M = m.array();
        builder.data(M);

        builder.data(keyCustomerA);
        builder.data(keyCustomerB);
        builder.data(keyCustomerC);

		ByteBuffer n = ByteBuffer.allocate(4);
        n.order(ByteOrder.LITTLE_ENDIAN);
        n.putInt(3);
        
        byte[] N = n.array();
        builder.data(N);
        builder.op(OP_CHECKMULTISIG);

        return builder.build();

    }

    @Override
    public Script createRedemptionScript(Transaction unsignedTransaction) {
        // Please be aware of the CHECK_MULTISIG bug!
        // TODO: Create a spending script
    	ScriptBuilder builder = new ScriptBuilder();
    	builder.data(keyBank);
    	builder.op(OP_0);
    	builder.data(keyCustomerC);
        return builder.build();
    }
}
