package blir.swing;

import blir.swing.listener.*;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

/**
 * A utility class for creating basic GUI's in a single method call.
 *
 * @author Blir
 */
public class QuickGUI {
    
	/*
	 * Used to control the Font used by all windows created by this class.
	 * Not yet fully implemented.
	 */
    public static final Font FONT = new Font("Times New Roman", Font.PLAIN, 12);
    
    /**
     * Searches for the specified look and feel and passes it to
     * javax.swing.UIManager.setLookAndFeel(String).
     *
     * @param lookAndFeel the name of the look and feel to find
     * @return true if the look and feel was changed
     * @throws ClassNotFoundException if the LookAndFeel class could not be
     * found
     * @throws InstantiationException if a new instance of the class couldn't be
     * created
     * @throws IllegalAccessException if the class or initializer isn't
     * accessible
     * @throws UnsupportedLookAndFeelException if lnf.isSupportedLookAndFeel()
     * is false
     */
    public static boolean setLookAndFeel(String lookAndFeel) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals(lookAndFeel)) {
                if (info.getName().equals(UIManager.getLookAndFeel().getName())) {
                    continue;
                }
                UIManager.setLookAndFeel(info.getClassName());
                return true;
            }
        }
        return false;
    }
    
    /**
     * Creates a simple message box intended for informing the user of
     * something.
     *
     * @param title The title of the message box
     * @param content The content the message box will display
     * @param task What to do when the user presses OK, nothing if null is
     * passed
     * @throws NullPointerException if title or content are null
     */
    public static JFrame msgBox(String title, String content, final Runnable task) {
        if (title == null) {
            throw new NullPointerException("Passed null title");
        }
        if (content == null) {
            throw new NullPointerException("Passed null content");
        }
        final JFrame frame = new JFrame(title);
        frame.setFont(FONT);
        JButton bugfix = new JButton();
        JTextArea text = new JTextArea(content);
        JScrollPane pane = new JScrollPane(text);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        pane.setBounds(5, 5, 300, 100);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JButton ok = new JButton("OK");
        ok.setBounds(270, 120, 50, 30);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (task != null) {
                    task.run();
                }
                frame.dispose();
            }
        });
        bugfix.setVisible(false);
        frame.add(pane);
        frame.add(ok);
        frame.add(bugfix);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }

    /**
     * Creates a simple message box intended for informing the user of something. <p>
     * Equivalent to msgBox(title, content, null);
     *
     * @param title The title of the info box
     * @param content The content the info box will display
     * @return the JFrame created
     * @throws NullPointerException if title or content are null
     */
    public static JFrame msgBox(String title, String content) {
        return msgBox(title, content, null);
    }

    /**
     * Creates an error box intended for notifying the user of an exceptional
     * error and then the application exits.
     *
     * @param title The title of the error box
     * @param content The message telling the user about the error
     * @param code The Java result for the application to exit with
     * @throws NullPointerException if title or content are null
     * @throws IllegalArgumentException if i is 0
     */
    public static void errorBox(String title, String content, final int code) {
        if (title == null) {
            throw new NullPointerException("Passed null title");
        }
        if (content == null) {
            throw new NullPointerException("Passed null content");
        }
        if (code == 0) {
            throw new IllegalArgumentException("Cannot use exit value of 0 for irregular termination");
        }
        JFrame frame = new JFrame(title);
        JButton bugfix = new JButton();
        JTextArea text = new JTextArea(content);
        JScrollPane pane = new JScrollPane(text);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        pane.setBounds(5, 5, 300, 100);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JButton ok = new JButton("OK");
        ok.setBounds(270, 120, 50, 30);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.exit(code);
            }
        });
        bugfix.setVisible(false);
        frame.add(pane);
        frame.add(ok);
        frame.add(bugfix);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent we) {
            }

            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(code);
            }

            @Override
            public void windowClosed(WindowEvent we) {
            }

            @Override
            public void windowIconified(WindowEvent we) {
            }

            @Override
            public void windowDeiconified(WindowEvent we) {
            }

            @Override
            public void windowActivated(WindowEvent we) {
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
            }
        });
        frame.setVisible(true);
    }

    /**
     * Creates a confirm box intended to request the user to confirm something,
     * giving them the option to confirm or cancel and running different tasks
     * depending upon which is pressed.
     *
     * @param title The title of the confirm box
     * @param content The message asking the user to confirm something
     * @param listener The listener called when the user confirms or cancels.
     * @throws NullPointerException if title, content, or listener is null
     */
    public static void confirmBox(String title, String content, final ConfirmOrCancelListener listener) {
        if (title == null) {
            throw new NullPointerException("Passed null title");
        }
        if (content == null) {
            throw new NullPointerException("Passed null content");
        }
        if (listener == null) {
            throw new NullPointerException("Passed null listener");
        }
        final JFrame frame = new JFrame(title);
        JButton bugfix = new JButton();
        JTextArea text = new JTextArea(content);
        JScrollPane pane = new JScrollPane(text);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        pane.setBounds(5, 5, 300, 100);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        ok.setBounds(175, 120, 75, 30);
        cancel.setBounds(260, 120, 75, 30);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                listener.onConfirm();
                frame.dispose();
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                listener.onCancel();
                frame.dispose();
            }
        });
        bugfix.setVisible(false);
        frame.add(pane);
        frame.add(ok);
        frame.add(cancel);
        frame.add(bugfix);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Creates a password box intended to have the user enter a password to gain
     * access to something.
     *
     * @param title The title of the password box
     * @param prompt The message prompting the user for the password
     * @param listener The listener called when the user enters a password
     * @throws NullPointerException if title, prompt, or listener is null
     */
    public static void passwordBox(String title, String prompt, final InputListener listener) {
        if (title == null) {
            throw new NullPointerException("Passed null title");
        }
        if (prompt == null) {
            throw new NullPointerException("Passed null prompt");
        }
        if (listener == null) {
            throw new NullPointerException("Passed null listener");
        }
        final JFrame frame = new JFrame(title);
        final JPasswordField inputBox = new JPasswordField();
        inputBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                listener.onInput(String.valueOf(inputBox.getPassword()));
                frame.dispose();
            }
        });

        JLabel label = new JLabel(prompt);
        JButton bugfix = new JButton();
        JButton ok = new JButton("OK");
        JButton fpButton;

        frame.setLocationRelativeTo(null);
        label.setBounds(5, 5, 200, 30);
        inputBox.setBounds(5, 40, 200, 30);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                listener.onInput(String.valueOf(inputBox.getPassword()));
                frame.dispose();
            }
        });
        bugfix.setVisible(false);

        if (listener instanceof PasswordListener) {
            fpButton = new JButton("Forgot Password");
            frame.setSize(350, 170);
            ok.setBounds(110, 88, 50, 30);
            fpButton.setBounds(170, 88, 150, 30);
            fpButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    ((PasswordListener) listener).onPasswordForgotten();
                    frame.dispose();
                }
            });
            frame.add(fpButton);
        } else {
            frame.setSize(300, 120);
            ok.setBounds(220, 50, 50, 30);
        }

        frame.add(label);
        frame.add(inputBox);
        frame.add(ok);
        frame.add(bugfix);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Creates a password box intended for the user to change a password or
     * create a new password.
     *
     * @param title The title of the password box
     * @param prompt The message prompting the user to create/change the
     * password
     * @param listener The listener called when the user creates a new password
     * @param isEssential If true then if this window is closed then the
     * application will exit.
     * @throws NullPointerException if title, prompt, or listener is null
     */
    public static void newPasswordBox(String title, String prompt, final NewPasswordListener listener, boolean isEssential) {
        if (title == null) {
            throw new NullPointerException("Passed null title");
        }
        if (prompt == null) {
            throw new NullPointerException("Passed null prompt");
        }
        if (listener == null) {
            throw new NullPointerException("Passed null listener");
        }
        final JFrame frame = new JFrame(title);
        final JPasswordField inputBox = new JPasswordField();
        final JPasswordField confirmBox = new JPasswordField();
        confirmBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (String.valueOf(inputBox.getPassword()).equals(String.valueOf(confirmBox.getPassword()))) {
                    listener.onInput(String.valueOf(inputBox.getPassword()));
                    frame.dispose();
                } else {
                    listener.onPasswordMismatch();
                }
            }
        });

        JLabel label = new JLabel(prompt);
        JLabel label2 = new JLabel("Confirm: ");
        JButton bugfix = new JButton();
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");

        frame.setSize(370, 190);
        frame.setLocationRelativeTo(null);
        label.setBounds(5, 5, 200, 30);
        inputBox.setBounds(5, 40, 200, 30);
        label2.setBounds(5, 75, 200, 30);
        confirmBox.setBounds(5, 110, 200, 30);
        ok.setBounds(220, 120, 50, 30);
        cancel.setBounds(280, 120, 75, 30);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (String.valueOf(inputBox.getPassword()).equals(String.valueOf(confirmBox.getPassword()))) {
                    listener.onInput(String.valueOf(inputBox.getPassword()));
                    frame.dispose();
                } else {
                    listener.onPasswordMismatch();
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.onCancel();
                frame.dispose();
            }
        });
        bugfix.setVisible(false);

        frame.add(label);
        frame.add(inputBox);
        frame.add(label2);
        frame.add(confirmBox);
        frame.add(ok);
        frame.add(cancel);
        frame.add(bugfix);

        frame.setResizable(false);
        frame.setDefaultCloseOperation((isEssential ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE));
        frame.setVisible(true);
    }

    /**
     * Creates a box giving the user the option to respond with yes or no to
     * something.
     *
     * @param title The title of the yes or no box
     * @param content The message the yes or no box will display, generally
     * should ask a yes or no question
     * @param listener The listener called when the user presses yes or no
     * @throws NullPointerException if title, content, or listener is null
     */
    public static void yesOrNoBox(String title, String content, final YesOrNoListener listener) {
        if (title == null) {
            throw new NullPointerException("Passed null title");
        }
        if (content == null) {
            throw new NullPointerException("Passed null content");
        }
        if (listener == null) {
            throw new NullPointerException("Passed null listener");
        }
        final JFrame frame = new JFrame(title);
        JButton bugfix = new JButton();
        JTextArea text = new JTextArea(content);
        JScrollPane pane = new JScrollPane(text);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        pane.setBounds(5, 5, 300, 100);
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");
        yes.setBounds(175, 120, 75, 30);
        no.setBounds(260, 120, 75, 30);
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                listener.onYes();
                frame.dispose();
            }
        });
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                listener.onNo();
                frame.dispose();
            }
        });
        bugfix.setVisible(false);
        frame.add(pane);
        frame.add(yes);
        frame.add(no);
        frame.add(bugfix);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
	 * Creates a simple window asking the user for input.
	 * 
	 * @param title the title of the window
	 * @param query the input to ask the user for
	 * @param init the initial value for the input box, nothing if null
	 * is passed
	 * @param listener the listener that will receive the user's input
	 * @param essential if true, System.exit(0) will be called if the
	 * window is closed
	 */
	public static void inputBox(String title, String query, String init, final InputListener listener, boolean essential) {
        if (title == null) {
            throw new NullPointerException("Passed null title");
        }
        if (query == null) {
            throw new NullPointerException("Passed null query");
        }
        if (listener == null) {
            throw new NullPointerException("Passed null listener");
        }

        final JFrame frame = new JFrame(title);
        frame.setFont(FONT);
        final JTextField inputBox = new JTextField(init);
        JLabel label = new JLabel(" " + query);
        JScrollPane pane = new JScrollPane(label);
        JButton bugfix = new JButton();
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");

        frame.setSize(360, 120);
        frame.setLocationRelativeTo(null);
        pane.setBounds(5, 5, 340, 30);
        inputBox.setBounds(5, 40, 200, 30);
        ok.setBounds(215, 50, 50, 30);
        cancel.setBounds(270, 50, 75, 30);

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                listener.onInput(inputBox.getText());
                frame.dispose();
            }
        };
        inputBox.addActionListener(al);
        ok.addActionListener(al);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener instanceof InputOrCancelListener) {
                    ((InputOrCancelListener) listener).onCancel();
                }
                frame.dispose();
            }
        });
        bugfix.setVisible(false);

        frame.add(pane);
        frame.add(inputBox);
        frame.add(ok);
        frame.add(cancel);
        frame.add(bugfix);

        frame.setResizable(false);
        frame.setDefaultCloseOperation((essential ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE));
        frame.setVisible(true);

        if (pane.getHorizontalScrollBar().isShowing()) {
            pane.setSize(340, 50);
            inputBox.setLocation(5, 60);
            ok.setLocation(215, 70);
            cancel.setLocation(270, 70);
            frame.setSize(360, 140);
        }
    }
    
    /**
     * Creates an input box that prompts the user for input.
     *
     * @param title The title of the input box
     * @param query The message prompting the user for input
     * @param listener What to do when the user inputs a value
     * @param essential If true then if the user closes this window, exit the
     * application
     * @throws NullPointerException if title, query, or listener is null
     */
    public static void inputBox(String title, String query, final InputListener listener, boolean essential) {
        inputBox(title, query, null, listener, essential);
    }

    /**
     * Creates a text box for general use.
     *
     * @param title The title of the text box
     * @param content The initial contents of the text box
     * @param listener What to do when the user presses Done, nothing if null.
     * Will be passed the contents of the text area at time of button press.
     * @param editable If true then the user may edit the text
     * @throws NullPointerException if title is null
     */
    public static void textBox(String title, String content, final InputListener listener, boolean editable) {
        if (title == null) {
            throw new NullPointerException("Passed null title");
        }
        final JFrame frame = new JFrame(title);
        JButton bugfix = new JButton();
        final JTextArea text = new JTextArea(content);
        JScrollPane pane = new JScrollPane(text);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        pane.setBounds(5, 5, 300, 100);
        text.setEditable(editable);
        text.setLineWrap(true);
        JButton done = new JButton("Done");
        done.setBounds(245, 120, 100, 30);
        done.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (listener != null) {
                    listener.onInput(text.getText());
                }
                frame.dispose();
            }
        });
        bugfix.setVisible(false);
        frame.add(pane);
        frame.add(done);
        frame.add(bugfix);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
