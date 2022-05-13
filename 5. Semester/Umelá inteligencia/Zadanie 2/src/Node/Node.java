package Node;

import java.util.ArrayList;

public class Node {
    public State state;
    public Node parentNode;
    public ArrayList<Node> childNodes;
    public int depth;

    public Node(Node parentNode, State state) {
        this.childNodes =  new ArrayList<>();
        this.parentNode = parentNode;
        this.state = state;
        if (parentNode != null)
            this.depth = parentNode.depth+1;
        else
            this.depth = 1;
    }
}
