package wuxian.me.ner.parser.node;

import java.util.List;

public interface Node {

    /**
     * Gets the vector of children nodes. This is used in the graph walker
     * algorithms.
     *
     * @return List<? extends Node>
     */
    List<? extends Node> getChildren();

    /**
     * Gets the name of the node. This is used in the rule dispatchers.
     *
     * @return String
     */
    String getName();
}