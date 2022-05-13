// Meno študenta: Lukáš Michalík
// Blockchain by mal na uspokojenie funkcií udržiavať iba obmedzené množstvo uzlov
// Nemali by ste mať všetky bloky pridané do blockchainu v pamäti  
// pretože by to spôsobilo pretečenie pamäte.
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Blockchain {
    public static final int CUT_OFF_AGE = 12;
    private ArrayList<BlockNode> blockList;
    private TransactionPool transactionPool;
    public int maxHeight;

    // všetky potrebné informácie na spracovanie bloku v reťazi blokov
    private class BlockNode {
        public Block b;
        public BlockNode parent;
        public ArrayList<BlockNode> children;
        public int height;
        // utxo pool na vytvorenie nového bloku na vrchu tohto bloku
        private UTXOPool uPool;

        public BlockNode(Block b, BlockNode parent, UTXOPool uPool) {
            this.b = b;
            this.parent = parent;
            children = new ArrayList<BlockNode>();
            this.uPool = uPool;
            if (parent != null) {
                height = parent.height + 1;
                parent.children.add(this);
            } else {
                height = 1;
            }
        }
        public UTXOPool getUTXOPoolCopy() {
            return new UTXOPool(uPool);
         }
    }
    /**
     * vytvor prázdny blockchain iba s Genesis blokom. Predpokladajme, že
     * {@code genesisBlock} je platný blok
     */
    public Blockchain(Block genesisBlock) {
        // IMPLEMENTOVAŤ
        this.maxHeight = 1;
        this.blockList = new ArrayList<BlockNode>();
        this.transactionPool = new TransactionPool();

        Transaction coinbase = genesisBlock.getCoinbase();
        UTXO coinbaseUtxo = new UTXO(coinbase.getHash(), 0);
        UTXOPool newUtxoPool = new UTXOPool();
        newUtxoPool.addUTXO(coinbaseUtxo, coinbase.getOutput(0));

        BlockNode genesisBlockNode = new BlockNode(genesisBlock, null, newUtxoPool);
        this.blockList.add(genesisBlockNode);
    }

    /** Získaj maximum height blok */
    public Block getBlockAtMaxHeight() {
        // IMPLEMENTOVAŤ
        BlockNode blockNode = this.blockList.stream()
            .filter(b -> b.height == maxHeight)
                .findFirst().orElse(null);
        if (blockNode != null) {
            return blockNode.b;
        }
        return null;
    }

    /** Získaj UTXOPool na ťaženie nového bloku na vrchu max height blok */
    public UTXOPool getUTXOPoolAtMaxHeight() {
        // IMPLEMENTOVAŤ
        BlockNode blockNode = this.blockList.stream()
                .filter(b -> b.height == maxHeight)
                .findFirst().orElse(null);
        if (blockNode != null) {
            return blockNode.getUTXOPoolCopy();
        }
        return null;
    }

    /** Získaj pool transakcií na vyťaženie nového bloku */
    public TransactionPool getTransactionPool() {
        // IMPLEMENTOVAŤ
        return this.transactionPool;
    }

    /**
     * Pridaj {@code block} do blockchainu, ak je platný. Kvôli platnosti by mali
     * byť všetky transakcie platné a blok by mal byť na
     * {@code height > (maxHeight - CUT_OFF_AGE)}.
     *
     * Môžete napríklad vyskúšať vytvoriť nový blok nad blokom Genesis (výška bloku
     * 2), ak height blockchainu je {@code <=
     * CUT_OFF_AGE + 1}. Len čo {@code height > CUT_OFF_AGE + 1}, nemôžete vytvoriť
     * nový blok vo výške 2.
     *
     * @return true, ak je blok úspešne pridaný
     */
    public boolean blockAdd(Block block) {
        // IMPLEMENTOVAŤ
        // Check block parent
        if (block.getPrevBlockHash() == null) {
            return false;
        }
        // Find block parent
        BlockNode parentBlock = this.blockList.stream()
            .filter(blockNode -> Arrays.equals(blockNode.b.getHash(), block.getPrevBlockHash()))
                .findFirst()
                .orElse(null);

        if (parentBlock == null) {
            return false;
        }

        // Check all block transactions
        UTXOPool parentUtxoPool = parentBlock.getUTXOPoolCopy();
        HandleTxs handleTxs = new HandleTxs(parentUtxoPool);
        Transaction[] possibleTxs = block.getTransactions().toArray(new Transaction[0]);
        Transaction[] successfulTxs = handleTxs.handler(possibleTxs);

        // Add coinbase to utxoPool
        UTXOPool newUtxoPool = new UTXOPool(handleTxs.UTXOPoolGet());
        Transaction coinbase = block.getCoinbase();
        UTXO coinbaseUtxo = new UTXO(coinbase.getHash(), 0);
        newUtxoPool.addUTXO(coinbaseUtxo, coinbase.getOutput(0));

        // Check if all transactions were valid
        if (possibleTxs.length != successfulTxs.length) {
            return false;
        }

        // Check height
        if ((parentBlock.height + 1) <= (this.maxHeight - CUT_OFF_AGE)) {
            return false;
        }

        // Create new BlockNode
        BlockNode newBlockNode = new BlockNode(block, parentBlock, newUtxoPool);

        // Add newBlockNode to ArrayList of blockNodes
        this.blockList.add(newBlockNode);

        // Remove transactions form transactionPool
        for (Transaction tx : successfulTxs) {
            this.transactionPool.removeTransaction(tx.getHash());
        }

        // Update max height
        if (newBlockNode.height > this.maxHeight) {
            this.maxHeight = newBlockNode.height;
        }

        // Check CUT_OFF_AGE
        List<BlockNode> cutBlockNodes = blockList.stream()
                .filter(blockNode -> blockNode.height < (this.maxHeight - CUT_OFF_AGE))
                //.collect(Collectors.toList());
                .toList();
        this.blockList.removeAll(cutBlockNodes);

        // Block has been successfully added to blockchain
        return true;
    }

    /** Pridaj transakciu do transakčného poolu */
    public void transactionAdd(Transaction tx) {
        // IMPLEMENTOVAŤ
        this.transactionPool.addTransaction(tx);
    }
}