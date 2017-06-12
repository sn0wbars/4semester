import java.util.ArrayList;
import java.util.List;  

public class TreeNode<T extends Comparable<T>>
{
    private T data;
    private TreeNode<T> parent;
    private ArrayList<TreeNode<T>> children;
    private int NumMaxThread; //final
    private boolean mark;

    public T getData()
    {
        return data;
    }

    public void setMark()
    {
        mark = true;
    }

    public void dropMark()
    {
        mark = false;
    }

    public TreeNode<T> findMaxLel()
    {
        final Object lock = new Object();
        final TreeNode<T> maxNode = (new TreeNode<T>(this));
        final class NumObject
        {
            private int val;
            public NumObject(int n)
            {
                val = n;
            }
        };
        NumObject NumActThreads = new NumObject(1);
        class SeekThread extends Thread
        {
            private TreeNode<T> curNode;

            public SeekThread(TreeNode cNode)
            {
                curNode = cNode;
            }

            private void eval()
            {
                synchronized(maxNode)
                {
                    if (curNode.data.compareTo(maxNode.data) > 0)
                        maxNode.set(curNode);
                }

                for (TreeNode<T> child : curNode.children)
                {
                    if (NumActThreads.val < NumMaxThread)
                    {
                        ++NumActThreads.val;
                        (new SeekThread(child)).start();
                    }
                    else 
                    {
                        curNode = child;
                        eval();
                    }
                }
            }

            public void run()
            {
                eval();
                    synchronized(lock)
                    {
                        --NumActThreads.val;
                        System.out.println(NumActThreads.val); //debug
                        if (NumActThreads.val == 0)
                        lock.notify();
                    }
            }
        };

        (new SeekThread(this)).start();

        try
        {
            synchronized (lock)
            {
               while (NumActThreads.val != 0) 
                    lock.wait();
            }
            return maxNode;
        } catch(InterruptedException e)
        {
            return null;   
        }
    }

    public boolean isRoot()
    {
        return parent == null;
    }

    public boolean isLeaf() 
    {
        return children.size() == 0;
    }

    public TreeNode(T d, int k) 
    {
        data = d;
        children = new ArrayList<TreeNode<T>>();
        NumMaxThread = k;
    }

    public TreeNode(T d) 
    {
        data = d;
        children = new ArrayList<TreeNode<T>>();
        NumMaxThread = 2;
    }

    public void set(TreeNode<T> sourceNode)
    {
        parent = sourceNode.parent;
        children = new ArrayList<TreeNode<T>>();
        for (TreeNode<T> child : sourceNode.children)
        {
            children.add(child);
        }
        data = sourceNode.data;
        NumMaxThread = sourceNode.NumMaxThread;
    }

    public TreeNode(TreeNode<T> sourceNode)
    {
        set(sourceNode);
    }

    public TreeNode<T> add(T childVal)
    {
        TreeNode<T> childNode = new TreeNode<T>(childVal, NumMaxThread);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    public TreeNode<T> get(int index)
    {
        return children.get(index);
    }

    public void print()
    {
        print("",true);
    }

    public void print(String prefix, boolean isTail) 
    {

        if (!isTail)
        {
            System.out.println(prefix + "├── " + data + ((mark) ? "!" : ""));
            for (int i = 0; i < children.size() - 1; i++) 
                children.get(i).print(prefix + "│   ", false);
            if (children.size() > 0) 
                children.get(children.size() - 1).print(prefix + ("│   "), true);
        }
        else
        {
            System.out.println(prefix + "└── " + data + ((mark) ? "!" : ""));
            for (int i = 0; i < children.size() - 1; i++)
                children.get(i).print(prefix + "    ", false);
            if (children.size() > 0) 
                children.get(children.size() - 1).print(prefix + ("    "), true);
        }
    }
}