import java.io.*;
import java.util.ArrayList;

/**
 * This is the provided NumberTriangle class to be used in this coding task.
 * <p>
 * Note: This is like a tree, but some nodes in the structure have two parents.
 * <p>
 * The structure is shown below. Observe that the parents of e are b and c, whereas
 * d and f each only have one parent. Each row is complete and will never be missing
 * a node. So each row has one more NumberTriangle object than the row above it.
 * <p>
 * a
 * b   c
 * d   e   f
 * h   i   j   k
 * <p>
 * Also note that this data structure is minimally defined and is only intended to
 * be constructed using the loadTriangle method, which you will implement
 * in this file. We have not included any code to enforce the structure noted above,
 * and you don't have to write any either.
 * <p>
 * <p>
 * See NumberTriangleTest.java for a few basic test cases.
 * <p>
 * Extra: If you decide to solve the Project Euler problems (see main),
 * feel free to add extra methods to this class. Just make sure that your
 * code still compiles and runs so that we can run the tests on your code.
 *
 */
public class NumberTriangle {

    private int root;

    private NumberTriangle left;
    private NumberTriangle right;

    public NumberTriangle(int root) {
        this.root = root;
    }

    public void setLeft(NumberTriangle left) {
        this.left = left;
    }


    public void setRight(NumberTriangle right) {
        this.right = right;
    }

    public int getRoot() {
        return root;
    }


    /**
     * [not for credit]
     * Set the root of this NumberTriangle to be the max path sum
     * of this NumberTriangle, as defined in Project Euler problem 18.
     * After this method is called, this NumberTriangle should be a leaf.
     * <p>
     * Hint: think recursively and use the idea of partial tracing from first year :)
     * <p>
     * Note: a NumberTriangle contains at least one value.
     */
    public void maxSumPath() {
        if (this.isLeaf())
            return;
        if (right != null)
            right.maxSumPath();
        if (left != null)
            left.maxSumPath();
        this.root += Math.max(left != null ? left.getRoot() : 0, right != null ? right.getRoot() : 0);
        right = null;
        left = null;
    }


    public boolean isLeaf() {
        return right == null && left == null;
    }


    /**
     * Follow path through this NumberTriangle structure ('l' = left; 'r' = right) and
     * return the root value at the end of the path. An empty string will return
     * the root of the NumberTriangle.
     * <p>
     * You can decide if you want to use a recursive or an iterative approach in your solution.
     * <p>
     * You can assume that:
     * the length of path is less than the height of this NumberTriangle structure.
     * each character in the string is either 'l' or 'r'
     *
     * @param path the path to follow through this NumberTriangle
     * @return the root value at the location indicated by path
     *
     */
    public int retrieve(String path) {
        if(path.isEmpty()){
            return root;
        }
        String rest = path.substring(1);
        if (path.substring(0, 1).equals("l") && left != null) {
            return left.retrieve(rest);
        } else if (path.substring(0, 1).equals("r") && right != null) {
            return right.retrieve(rest);
        }
        return root;
    }

    /**
     * Read in the NumberTriangle structure from a file.
     * <p>
     * You may assume that it is a valid format with a height of at least 1,
     * so there is at least one line with a number on it to start the file.
     * <p>
     * See resources/input_tree.txt for an example NumberTriangle format.
     *
     * @param fname the file to load the NumberTriangle structure from
     * @return the topmost NumberTriangle object in the NumberTriangle structure read from the specified file
     * @throws IOException may naturally occur if an issue reading the file occurs
     */
    public static NumberTriangle loadTriangle(String fname) throws IOException {
        // open the file and get a BufferedReader object whose methods
        // are more convenient to work with when reading the file contents.
        InputStream inputStream = NumberTriangle.class.getClassLoader().getResourceAsStream(fname);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));


        java.util.List<java.util.List<NumberTriangle>> rows = new ArrayList<>();

        // will need to return the top of the NumberTriangle,
        // so might want a variable for that.
        NumberTriangle top = null;

        String line = br.readLine();
        while (line != null) {

            // remove when done; this line is included so running starter code prints the contents of the file
            System.out.println(line);
            ArrayList<NumberTriangle> nums = new ArrayList();
            for (String i : line.split(" ")) {
                nums.add(new NumberTriangle(Integer.parseInt(i)));
            }
            if (!rows.isEmpty()) {
                java.util.List<NumberTriangle> rowAbove = rows.get(rows.size() - 1);
                for (int i = 0; i < rowAbove.size(); i++) {
                    rowAbove.get(i).setLeft(nums.get(i));
                    rowAbove.get(i).setRight(nums.get(i + 1));
                }
            }

            //read the next line
            line = br.readLine();
            rows.add(nums);
        }
        br.close();
        return rows.get(0).get(0);
    }

    public static void main(String[] args) throws IOException {

        NumberTriangle mt = NumberTriangle.loadTriangle("input_tree.txt");

        // [not for credit]
        // you can implement NumberTriangle's maxPathSum method if you want to try to solve
        // Problem 18 from project Euler [not for credit]
        // mt.maxSumPath();
        System.out.println(mt.getRoot());
        System.out.println(mt.retrieve("lrlrlr"));
        System.out.println(mt.retrieve("rrr"));
    }
}
