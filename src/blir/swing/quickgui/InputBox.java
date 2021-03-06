package blir.swing.quickgui;

import blir.swing.listener.InputListener;
import blir.swing.listener.InputOrCancelListener;

/**
 *
 * @author Blir
 */
public class InputBox extends javax.swing.JFrame {
    
    private InputListener listener;

    /**
     * Creates new form InputBox
     */
    public InputBox(String title, String query, InputListener listener, boolean isEssential) {
        this(title, query, null, listener, isEssential);
    }

    /**
     * Creates new form InputBox
     */
    public InputBox(String title, String query, String init, InputListener listener, boolean isEssential) {
        super(title);
        initComponents();
        jTextArea1.setText(query);
        jTextField1.setText(init);
        setDefaultCloseOperation(isEssential ? EXIT_ON_CLOSE : DISPOSE_ON_CLOSE);
        this.listener = listener;
        setLocationRelativeTo(null);
        jTextField1.grabFocus();
        if (init != null) {
            jTextField1.setSelectionStart(0);
            jTextField1.setSelectionEnd(init.length());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                InputBox.this.windowClosing(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onInput(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onInput(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onInput
        listener.onInput(jTextField1.getText());
        dispose();
    }//GEN-LAST:event_onInput

    private void windowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_windowClosing
        if (listener instanceof InputOrCancelListener) {
            ((InputOrCancelListener) listener).onCancel();
        }
    }//GEN-LAST:event_windowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
