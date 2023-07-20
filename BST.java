import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;


/**
 * Your implementation of a BST.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 * @userid YOUR USER ID HERE (i.e. kpeynabard3)
 * @GTID YOUR GT ID HERE (i.e. 903353136)
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Collection can not be null");
        }
        for (T obj: data
             ) {
            add(obj);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        root = addHelper(root, data);
    }
    /**
     * Recursive Add helper method. Takes in the data to be added and
     * initial root.
     * @param data The data to be added
     * @param currNode the node which we use to travel
     * @return a BSTNode contains elements to be chained
     */
    private BSTNode<T> addHelper(BSTNode<T> currNode, T data) {
        if (currNode == null) {
            size++;
            return new BSTNode<T>(data);
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(addHelper(currNode.getRight(), data));
        } else if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(addHelper(currNode.getLeft(), data));
        }
        return currNode;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null, real data is needed to be removed");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        removeHelper(root, data, dummy);
        if (dummy.getData() == null) {
            throw new NoSuchElementException("Data is not presented in the tree");
        }
        size--;
        return dummy.getData();
    }
    /**
     * Recursive helper method for remove.
     * the starting root.
     * @param data The data to be removed
     * @param currNode The starting node usually the root
     * @param dummyNode the dummy to cary the deleted data out
     * @return a BSTNode that data is removed from.
     */
    private BSTNode<T> removeHelper(BSTNode<T> currNode, T data, BSTNode<T> dummyNode) {
        if (currNode == null) {
            dummyNode.setData(null);
            return currNode;
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(removeHelper(currNode.getRight(), data, dummyNode));
        } else if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(removeHelper(currNode.getLeft(), data, dummyNode));
        } else {
            dummyNode.setData(currNode.getData());
            if (currNode.getLeft() == null && currNode.getRight() == null) {
                return null;
            } else if (currNode.getLeft() != null && currNode.getRight() == null) {
                return currNode.getLeft();
            } else if (currNode.getLeft() == null && currNode.getRight() != null) {
                return currNode.getRight();
            } else {
                BSTNode<T> dumDumNode = new BSTNode<>(null);
                currNode.setRight(removeSuccessor(currNode.getRight(), dumDumNode));
                currNode.setData(dumDumNode.getData());
            }
        }
        return currNode;
    }
    /**
     * Recursive helper method to find the successor to replace the removed note
     * @param currNode The node to be replaced
     * @param dummyNode The node to cary the successor's data above.
     * @return a BSTNode that was added
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> currNode, BSTNode<T> dummyNode) {
        if (currNode.getLeft() == null) {
            dummyNode.setData(currNode.getData());
            return currNode.getRight();
        } else {
            currNode.setLeft(removeSuccessor(currNode.getLeft(), dummyNode));
        }
        return currNode;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        BSTNode<T> dummyNode = new BSTNode<T>(null);
        root = getHelper(root, data, dummyNode);
        return dummyNode.getData();
    }

    /**
     * Recursive helper method for getting the node.
     * the starting root.
     * @param data The data to be found
     * @param currNode The starting node usually the root
     * @param dummyNode the dummy to cary the data out
     * @return a BSTNode that has the data
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private BSTNode<T> getHelper(BSTNode<T> currNode, T data, BSTNode<T> dummyNode) {
        if (currNode == null) {
            throw new NoSuchElementException("Given data is not in the Tree");
        } else if (data.equals(currNode.getData())) {
            dummyNode.setData(currNode.getData());
            return currNode;
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(getHelper(currNode.getRight(), data, dummyNode));
        } else if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(getHelper(currNode.getLeft(), data, dummyNode));
        }
        return currNode;
    }

    /**
     * Returns whether data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data could not be null");
        }
        if (root.getData() == data) {
            return true;
        }
        BSTNode<T> dummyNode = new BSTNode<>(null);
        root = containsHelper(root, data, dummyNode);
        return dummyNode.getData() != null;
    }

    /**
     * Recursive helper method for searching the node.
     * the starting root.
     * @param data The data to be searched
     * @param currNode The starting node usually the root
     * @param dummyNode the dummy to cary the data or null value out
     * @return a BSTNode that has the data or null value
     */
    private BSTNode<T> containsHelper(BSTNode<T> currNode, T data, BSTNode<T> dummyNode) {
        if (currNode == null) {
            dummyNode.setData(null);
            return currNode;
        } else if (data.equals(currNode.getData())) {
            dummyNode.setData(currNode.getData());
            return currNode;
        } else if (data.compareTo(currNode.getData()) > 0) {
            currNode.setRight(containsHelper(currNode.getRight(), data, dummyNode));
        } else if (data.compareTo(currNode.getData()) < 0) {
            currNode.setLeft(containsHelper(currNode.getLeft(), data, dummyNode));
        }
        return currNode;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        ArrayList<T> container = new ArrayList<>();
        preorderHelper(root, container);
        return container;
    }
    /**
     * Recursive helper method for searching the node.
     * the starting root.
     * @param currNode The starting node usually the root
     * @param container the list to contain the such order
     */
    private void preorderHelper(BSTNode<T> currNode, ArrayList<T> container) {
        if (currNode == null) {
            return;
        }
        container.add(currNode.getData());
        preorderHelper(currNode.getLeft(), container);
        preorderHelper(currNode.getRight(), container);
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        ArrayList<T> container = new ArrayList<>();
        inorderHelper(root, container);
        return container;
    }
    /**
     * Recursive helper method for searching the node.
     * the starting root.
     * @param currNode The starting node usually the root
     * @param container the list to contain the such order
     */
    private void inorderHelper(BSTNode<T> currNode, ArrayList<T> container) {
        if (currNode == null) {
            return;
        }
        inorderHelper(currNode.getLeft(), container);
        container.add(currNode.getData());
        inorderHelper(currNode.getRight(), container);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        ArrayList<T> container = new ArrayList<>();
        postorderHelper(root, container);
        return container;
    }
    /**
     * Recursive helper method for searching the node.
     * the starting root.
     * @param currNode The starting node usually the root
     * @param container the list to contain the such order
     */
    private void postorderHelper(BSTNode<T> currNode, ArrayList<T> container) {
        if (currNode == null) {
            return;
        }
        postorderHelper(currNode.getLeft(), container);
        postorderHelper(currNode.getRight(), container);
        container.add(currNode.getData());
    }
    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {

        List<T> lvlOrdered = new ArrayList<>(size);
        Queue<BSTNode<T>> containerQ = new LinkedList<>();
        containerQ.add(root);
        while (containerQ.size() != 0) {
            if (containerQ.peek() != null) {
                containerQ.add(containerQ.peek().getLeft());
                containerQ.add(containerQ.peek().getRight());
                lvlOrdered.add(containerQ.remove().getData());
            } else {
                containerQ.remove();
            }
        }
        return lvlOrdered;

    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return heightHelper(root, 0);
        }
    }
    /**
     * Recursive helper method for searching the node.
     * the starting root.
     * @param currNode The target node to get the height
     * @param i the integer representing the nodes height
     * @return int which is the height of this node
     */
    private int heightHelper(BSTNode<T> currNode, int i) {
        if (currNode == null) {
            return -1;
        } else {
            if (currNode.getLeft() == null && currNode.getRight() == null) {
                return 0;
            } else {
                return 1 + Math.max(heightHelper(currNode.getLeft(), i),
                        heightHelper(currNode.getRight(), i));
            }
        }

    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("one Of the give data was null."
                    + " Please provide real data");
        }
        if (root == null) {
            throw new NoSuchElementException("data1 and data2 can not be found because tree is empty");
        }
        if (data1.compareTo(data2) == 0) {
            LinkedList<T> path = new LinkedList<>();
            path.add(data1);
            return path;
        }
        LinkedList<T> path = new LinkedList<>();
        LinkedList<BSTNode<T>> dummy = new LinkedList<>();
        findCommonA(root, data1, data2, dummy);
        BSTNode<T> currDummy = dummy.getFirst();
        if (currDummy.getData().compareTo(data1) == 0) {
            pathTo(currDummy, data2, path);
            path.removeLast();
        } else if (currDummy.getData().compareTo(data2) == 0) {
            pathFrom(currDummy, data1, path);
            path.add(currDummy.getData());
            path.removeLast();
        } else {
            pathFrom(currDummy, data1, path);
            path.removeLast();
            pathTo(currDummy, data2, path);
        }
        return path;
    }
    /**
     * Recursive helper method for searching the node.
     * the starting root.
     * @param currNode usually the root
     * @param data1 the data to start from
     * @param data2 the data to end
     * @param dummy the curr dummy
     */
    private void findCommonA(BSTNode<T> currNode, T data1, T data2, LinkedList<BSTNode<T>> dummy) {
        if (currNode.getData().compareTo(data1) < 0
                && currNode.getData().compareTo(data2) < 0) {
            findCommonA(currNode.getRight(), data1, data2, dummy);
        } else if (currNode.getData().compareTo(data1) > 0
                && currNode.getData().compareTo(data2) > 0) {
            findCommonA(currNode.getLeft(), data1, data2, dummy);
        }
        dummy.add(currNode);
    }
    /**
     * Recursive helper method for searching the node.
     * the starting root.
     * @param currNode usually the common ancestor
     * @param data the data to start from
     * @param pathList the path
     */
    private void pathFrom(BSTNode<T> currNode, T data, LinkedList<T> pathList) {
        if (currNode == null || currNode.getData() == null) {
            throw new NoSuchElementException("Data1 does not exist in the tree");
        } else {
            if (currNode.getData().compareTo(data) < 0) {
                pathFrom(currNode.getRight(), data, pathList);
            } else if (currNode.getData().compareTo(data) > 0) {
                pathFrom(currNode.getLeft(), data, pathList);
            }
            pathList.add(currNode.getData());
        }
    }
    /**
     * Recursive helper method for searching the node.
     * the starting root.
     * @param currNode usually the common ancestor
     * @param data the data to go to
     * @param pathList the path
     */
    private void pathTo(BSTNode<T> currNode, T data, LinkedList<T> pathList) {
        if (currNode == null || currNode.getData() == null) {
            throw new NoSuchElementException("Data1 does not exist in the tree");
        } else {
            if (currNode.getData().compareTo(data) < 0) {
                pathList.add(currNode.getData());
                pathFrom(currNode.getRight(), data, pathList);
            } else if (currNode.getData().compareTo(data) > 0) {
                pathList.add(currNode.getData());
                pathFrom(currNode.getLeft(), data, pathList);
            }


        }

    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
