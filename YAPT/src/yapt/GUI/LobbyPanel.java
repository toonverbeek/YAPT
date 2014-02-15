/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yapt.GUI;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.text.Position;
import yapt.GAME.Session;
import yapt.RMI.ILobby;
import yapt.RMI.IPongGame;
import yapt.RMI.ISession;
import yapt.RMI.IYAPTServer;

/**
 *
 * @author tonnu
 */
public class LobbyPanel extends javax.swing.JPanel {

    private String username;
    private IYAPTServer server;
    private Session sessionImpl;
    private YAPTPanel gamePanel;
    private CardLayout cl;
    private JPanel cards;
    private List<ISession> onlinePlayers;
    private DefaultListModel players, pongGames;
    //private ILobby lobby;

    /**
     * Creates new form LobbyPanel
     */
    LobbyPanel(CardLayout cl) {
        initComponents();
        this.cl = cl;

        onlinePlayers = new ArrayList<>();
        players = new DefaultListModel<String>();
        pongGames = new DefaultListModel<String>();

    }

    public void setPongGames(List<IPongGame> _pongGames) {
        this.lst_currentGames = new JList(pongGames);
    }

    public void setOnlinePlayers(Collection<ISession> onlinePlayers) throws RemoteException {
        for (ISession s : onlinePlayers) {
            if (players.contains(s.getUsername())) {
                System.out.println("List contains " + s.getUsername());
            } else {
                System.out.println("List does not contain " + s.getUsername());
                players.addElement(s.getUsername());
            }
        }
        this.lst_onlinePlayers.setModel(players);
    }

    public void newMessage(String chatMessage) {
        //System.out.println("Someone is calling newMessage()");
        this.jTextArea1.append(chatMessage + "\n");
    }

    public void setGameList(List<String> newGameList) throws RemoteException {
        pongGames.clear();
        for (String _game : newGameList) {
            pongGames.addElement(_game);
        }
        this.lst_currentGames.setModel(pongGames);
    }

    public void addPlayer(ISession player) throws RemoteException {
        this.players.addElement(player.getUsername());
        this.lst_onlinePlayers.setModel(players);

    }

    public void removeGame(IPongGame game) {
        this.pongGames.removeElement("");
    }

    public void removePlayer(ISession player) {
        this.onlinePlayers.remove(player);
    }

    public void tryLogin(String serverAddress, String username, YAPTPanel gamepanel, JPanel cards) throws RemoteException, NotBoundException, MalformedURLException {
        this.username = username;
        this.gamePanel = gamepanel;
        this.cards = cards;
        //unwise
        System.setSecurityManager(null);
        //String serverAddress = (GameFrame.ARGS.length < 1) ? "localhost" : GameFrame.ARGS[0];
        //aserverAddress = "188.226.136.184";
        //when trying to find a game, try to connect to server first

        //register clientStub at remote server
        //Registry remoteRegistry = LocateRegistry.getRegistry(serverAddress, RMI_PORT);
        server = (IYAPTServer) Naming.lookup(IYAPTServer.class.getSimpleName());
        //server = (IYAPTServer) remoteRegistry.lookup(IYAPTServer.class.getSimpleName());
        //lobby = (ILobby) remoteRegistry.lookup(ILobby.class.getSimpleName());
        //create RMI-stub for a ClientImpl
        //lobby = (ILobby) Naming.lookup(ILobby.class.getSimpleName());

        sessionImpl = new Session(username, server, gamepanel, this);
        final ISession sessionStub = (ISession) UnicastRemoteObject.exportObject(sessionImpl, 0);

        server.register(sessionStub);

        //start pushing messages to the server
        server.onMessage("Connected", sessionImpl);
    }

    public void showPanel() {
        this.cl.show(cards, "Lobby");
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
        jScrollPane2 = new javax.swing.JScrollPane();
        lst_currentGames = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        lst_onlinePlayers = new javax.swing.JList();
        btn_joinGame = new javax.swing.JButton();
        btn_startGame = new javax.swing.JButton();
        btn_Challenge = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        lst_currentGames.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lst_currentGames);

        lst_onlinePlayers.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(lst_onlinePlayers);

        btn_joinGame.setText("Join Game");
        btn_joinGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_joinGameActionPerformed(evt);
            }
        });

        btn_startGame.setLabel("Start New Game");
        btn_startGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_startGameActionPerformed(evt);
            }
        });

        btn_Challenge.setText("Challenge Player");
        btn_Challenge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChallengeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1256, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btn_startGame, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(184, 184, 184)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(306, 306, 306)
                .addComponent(btn_joinGame, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Challenge)
                .addGap(286, 286, 286))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_startGame))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_joinGame)
                    .addComponent(btn_Challenge))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
        //jTextArea1.append("" + evt.getKeyCode());       
    }//GEN-LAST:event_jTextField1KeyTyped

    private void btn_joinGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_joinGameActionPerformed
        try {
            if (this.lst_currentGames.getSelectedIndex() != -1) {
                this.cl.show(cards, "Game");
                this.gamePanel.start(sessionImpl, this, cards);
            }

        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            Logger.getLogger(LobbyPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_joinGameActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && !jTextField1.getText().equals("")) {
            try {
                this.sessionImpl.onMessage("SendPublicChatMessage", username + ": " + jTextField1.getText());
                this.jTextField1.setText("");
            } catch (RemoteException ex) {
                Logger.getLogger(LobbyPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void btn_startGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startGameActionPerformed
        // TODO add your handling code here:
        try {
            this.cl.show(cards, "Game");
            this.gamePanel.start(sessionImpl, this, cards);
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            Logger.getLogger(LobbyPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_startGameActionPerformed

    private void btn_ChallengeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChallengeActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btn_ChallengeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Challenge;
    private javax.swing.JButton btn_joinGame;
    private javax.swing.JButton btn_startGame;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JList lst_currentGames;
    private javax.swing.JList lst_onlinePlayers;
    // End of variables declaration//GEN-END:variables

}
