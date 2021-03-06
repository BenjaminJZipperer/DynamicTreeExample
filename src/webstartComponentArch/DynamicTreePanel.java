package webstartComponentArch;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class DynamicTreePanel extends JPanel implements ActionListener {
    private int newNodeSuffix = 1;
    private static String ADD_COMMAND = "add";
    private static String REMOVE_COMMAND = "remove";
    private static String CLEAR_COMMAND = "clear";
    
    private DynamicTree treePanel;

    public DynamicTreePanel() {
        super(new BorderLayout());
        
        //Create the components.
        treePanel = new DynamicTree();
        populateTree(treePanel);

        JButton addButton = new JButton("Add");
        addButton.setActionCommand(ADD_COMMAND);
        addButton.addActionListener(this);
        
        JButton removeButton = new JButton("Remove");
       
        
        JButton clearButton = new JButton("Clear");
     
        
        //Lay everything out.
        treePanel.setPreferredSize(
            new Dimension(300, 150));
        add(treePanel, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(0,3));
        panel.add(addButton);
        panel.add(removeButton); 
        panel.add(clearButton);
        add(panel, BorderLayout.SOUTH);
    }

	private void populateTree(DynamicTree treePanel2) {
		String p1Name = new String("Parent 1");
	    String p2Name = new String("Parent 2");
	    String c1Name = new String("Child 1");
	    String c2Name = new String("Child 2");

	    DefaultMutableTreeNode p1, p2;

	    p1 = treePanel.addObject(null, p1Name);
	    p2 = treePanel.addObject(null, p2Name);

	    treePanel.addObject(p1, c1Name);
	    treePanel.addObject(p1, c2Name);

	    treePanel.addObject(p2, c1Name);
	    treePanel.addObject(p2, c2Name);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
    // ...
}
class DynamicTree extends JPanel {
	  protected DefaultMutableTreeNode rootNode;
	  protected DefaultTreeModel treeModel;
	  protected JTree tree;
	  private Toolkit toolkit = Toolkit.getDefaultToolkit();

	  public DynamicTree() {
	    super(new GridLayout(1, 0));

	    rootNode = new DefaultMutableTreeNode("Root Node");
	    treeModel = new DefaultTreeModel(rootNode);

	    tree = new JTree(treeModel);
	    tree.setEditable(true);
	    tree.getSelectionModel().setSelectionMode(
	        TreeSelectionModel.SINGLE_TREE_SELECTION);
	    tree.setShowsRootHandles(true);

	    JScrollPane scrollPane = new JScrollPane(tree);
	    add(scrollPane);
	  }

	  /** Remove all nodes except the root node. */
	  public void clear() {
	    rootNode.removeAllChildren();
	    treeModel.reload();
	  }

	  /** Remove the currently selected node. */
	  public void removeCurrentNode() {
	    TreePath currentSelection = tree.getSelectionPath();
	    if (currentSelection != null) {
	      DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection
	          .getLastPathComponent());
	      MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
	      if (parent != null) {
	        treeModel.removeNodeFromParent(currentNode);
	        return;
	      }
	    }

	    // Either there was no selection, or the root was selected.
	    toolkit.beep();
	  }

	  /** Add child to the currently selected node. */
	  public DefaultMutableTreeNode addObject(Object child) {
	    DefaultMutableTreeNode parentNode = null;
	    TreePath parentPath = tree.getSelectionPath();

	    if (parentPath == null) {
	      parentNode = rootNode;
	    } else {
	      parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
	    }

	    return addObject(parentNode, child, true);
	  }

	  public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
	      Object child) {
	    return addObject(parent, child, false);
	  }

	  public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
	      Object child, boolean shouldBeVisible) {
	    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

	    if (parent == null) {
	      parent = rootNode;
	    }

	    // It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
	    treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

	    // Make sure the user can see the lovely new node.
	    if (shouldBeVisible) {
	      tree.scrollPathToVisible(new TreePath(childNode.getPath()));
	    }
	    return childNode;
	  }
}