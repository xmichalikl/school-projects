import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Meno študenta: Lukáš Michalík
public class HandleTxs {
    private UTXOPool actualUtxoPool;

    /**
     * Vytvorí verejný ledger, ktorého aktuálny UTXOPool (zbierka nevyčerpaných
     * transakčných výstupov) je {@code utxoPool}. Malo by to vytvoriť obchrannú kópiu
     * utxoPool pomocou konštruktora UTXOPool (UTXOPool uPool).
     */
    public HandleTxs(UTXOPool utxoPool) {
        // IMPLEMENTOVAŤ
        this.actualUtxoPool = new UTXOPool(utxoPool);
    }

    /**
     * @return aktuálny UTXO pool. 
     * Ak nenájde žiadny aktuálny UTXO pool, tak vráti prázdny (n)ie nulový objekt {@code UTXOPool}.
     */
    public UTXOPool UTXOPoolGet() {
        // IMPLEMENTOVAŤ
        if (this.actualUtxoPool != null) {
            return this.actualUtxoPool;
        }
        else {
            return new UTXOPool();
        }
    }

    /**
     * @return true, ak 
     * (1) sú všetky výstupy nárokované {@code tx} v aktuálnom UTXO pool,
     * (2) podpisy na každom vstupe {@code tx} sú platné, 
     * (3) žiadne UTXO nie je nárokované viackrát, 
     * (4) všetky výstupné hodnoty {@code tx}s sú nezáporné a 
     * (5) súčet vstupných hodnôt {@code tx}s je väčší alebo rovný súčtu jej
     *     výstupných hodnôt; a false inak.
     */
    public boolean txIsValid(Transaction tx) {
        // IMPLEMENTOVAŤ
        double totalTxInputValue = 0;
        for (int inputIndex = 0; inputIndex < tx.getInputs().size(); inputIndex++) {
            Transaction.Input txInput = tx.getInput(inputIndex);
            UTXO txInputUtxo = new UTXO(txInput.prevTxHash, txInput.outputIndex);

            // (1) Check if actualUtxoPool contains txInputUtxo
            if (!this.actualUtxoPool.contains(txInputUtxo)) {
                return false;
            }
            // (2) check if txInput has valid signature
            Transaction.Output txOutputForTxInputUtxo = this.actualUtxoPool.getTxOutput(txInputUtxo);
            if (!txOutputForTxInputUtxo.address.verifySignature(tx.getDataToSign(inputIndex), txInput.signature)) {
                return false;
            }
            // (3) Check if every 'txInputUtxo' is unique
            if (tx.getInputs().indexOf(txInput) != tx.getInputs().lastIndexOf(txInput)) {
                return false;
            }
            totalTxInputValue += txOutputForTxInputUtxo.value;
        }
        // (4) check every output in Tx, has positive value
        double totalTxOutputValue = 0;
        for (Transaction.Output txOutput : tx.getOutputs()) {
            if (txOutput.value < (double) 0) {
                return false;
            }
            totalTxOutputValue += txOutput.value;
        }
        // (5) check if totalTxInputValue >= totalTxOutputValue
        return !(totalTxInputValue < totalTxOutputValue);
    }

    /**
     * Spracováva každú epochu prijímaním neusporiadaného radu navrhovaných
     * transakcií, kontroluje správnosť každej transakcie, vracia pole vzájomne 
     * platných prijatých transakcií a aktualizuje aktuálny UTXO pool podľa potreby.
     */
    public Transaction[] handler(Transaction[] possibleTxs) {
        // IMPLEMENTOVAŤ
        ArrayList<Transaction> possibleTransactions = new ArrayList<Transaction>(Arrays.asList(possibleTxs));
        ArrayList<Transaction> validTransactions = new ArrayList<Transaction>();
        ArrayList<Transaction> successfulTransactions = new ArrayList<Transaction>();
        boolean atLeastOneValidTransaction = true;

        // Find at least one valid transaction
        while (atLeastOneValidTransaction) {
            atLeastOneValidTransaction = false;
            for (Transaction tx : possibleTransactions) {
                if (txIsValid(tx)) {
                    atLeastOneValidTransaction = true;
                    // Spend all input Utxos
                    for (Transaction.Input txInput : tx.getInputs()) {
                        UTXO usedTxUtxo = new UTXO(txInput.prevTxHash, txInput.outputIndex);
                        this.actualUtxoPool.removeUTXO(usedTxUtxo);
                    }
                    // Create new output Utxos
                    for (int outputIndex = 0; outputIndex < tx.getOutputs().size(); outputIndex++) {
                        Transaction.Output txOutput = tx.getOutput(outputIndex);
                        UTXO newTxUtxo = new UTXO(tx.getHash(), outputIndex);
                        this.actualUtxoPool.addUTXO(newTxUtxo, txOutput);
                    }
                    successfulTransactions.add(tx);
                    validTransactions.add(tx);
                }
            }
            // Remove all valid transactions form possibleTransactions
            possibleTransactions.removeAll(validTransactions);
            validTransactions.clear();
        }
        // Return all successful transactions
        return successfulTransactions.toArray(new Transaction[0]);
    }
}
