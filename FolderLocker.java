import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class FolderLocker extends JFrame implements ActionListener {
    private JButton lockButton;
    private JButton unlockButton;
    private JFileChooser fileChooser;

    public FolderLocker() {
        setTitle("Folder Locker");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the components
        lockButton = new JButton("Lock");
        unlockButton = new JButton("Unlock");
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Set the layout
        setLayout(new FlowLayout());

        // Add components to the frame
        add(lockButton);
        add(unlockButton);

        // Register button actions
        lockButton.addActionListener(this);
        unlockButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lockButton) {
            lockFolder();
        } else if (e.getSource() == unlockButton) {
            unlockFolder();
        }
    }

    private void lockFolder() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File folder = fileChooser.getSelectedFile();

            // Rename the folder by adding a ".locked" extension
            String folderPath = folder.getAbsolutePath();
            String lockedFolderPath = folderPath + ".locked";
            File lockedFolder = new File(lockedFolderPath);
            boolean success = folder.renameTo(lockedFolder);

            if (success) {
                JOptionPane.showMessageDialog(this, "Folder locked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to lock the folder.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void unlockFolder() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File lockedFolder = fileChooser.getSelectedFile();

            // Remove the ".locked" extension from the folder name
            String lockedFolderPath = lockedFolder.getAbsolutePath();
            String folderPath = lockedFolderPath.replace(".locked", "");
            File folder = new File(folderPath);
            boolean success = lockedFolder.renameTo(folder);

            if (success) {
                JOptionPane.showMessageDialog(this, "Folder unlocked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to unlock the folder.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                FolderLocker folderLocker = new FolderLocker();
                folderLocker.setVisible(true);
            }
        });
    }
}
