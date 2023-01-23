package pkg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class DontUseBoard extends JDialog implements ActionListener{
        JButton use;
        JButton cancel;
        int cardID;
        JTextArea cardContext;
        JLabel cardContextName;
        
        DontUseBoard(int iD, JFrame owner){
        	super(owner, true);
        	this.cardID = iD;
        	
        	GameInfo.setUsingBoard(true);
        	setLocationRelativeTo(owner);
        	
            setSize(600, 300);
            setResizable(false);
            setModal(true);
            JPanel panel = new JPanel();
            panel.setLayout( null );
            
            use = new JButton("使用");
            use.setFont(new Font("HGS行書体", Font.BOLD, 30));
            use.setBackground(Color.WHITE);
            use.setBounds(350, 200, 200, 50);
            
            cancel = new JButton("きゃんせる");
            cancel.setFont(new Font("HGS行書体", Font.BOLD, 30));
            cancel.setBackground(Color.RED);
            cancel.setBounds(50, 200, 200, 50);
            
            cardContext = new JTextArea();
            cardContext.setPreferredSize(new Dimension(200, 100));
            cardContext.setText(GameInfo.getText());
            cardContext.setFont(new Font("HGS行書体", Font.BOLD, 25));
            cardContext.setEditable(false);
            cardContext.setLineWrap(true);
            cardContext.setBounds(100, 70, 400, 100);
            
            cardContextName = new JLabel("えふぇくと");
            cardContextName.setFont(new Font("HGS行書体", Font.BOLD, 30));
            cardContextName.setBounds(200,0 ,350, 100);
            
            panel.add(use);
            panel.add(cancel);
            panel.add(cardContext);
            panel.add(cardContextName);
         
            add(panel);
            
            use.addActionListener(this);
            cancel.addActionListener(this);
            
            addWindowListener(new WindowAdapter(){
            	public void windowClosing(WindowEvent e) {
                    setModal(false);
                    cancel();
            		setVisible(false);
            	}
            });
            
            setVisible(true);
        }
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == use){
                //sendtext;
            	MessageAppPl sendMessage = new MessageAppPl();
            	sendMessage.type = 2;
            	sendMessage.color = GameInfo.getMyColor();
            	sendMessage.cardID = this.cardID;
            	sendMessage.effectStage = 2;
            	sendMessage.roomNumber = GameInfo.getRoomNumber();
            	
            	GameInfo.setUsingCardID(-2);
            	GameInfo.setCardUsing(false);
            	
            	Main.pC.sendAppServer(sendMessage);
            }else if(e.getSource() == cancel){
                cancel();
            }
            setVisible(false);
        }
        
        private void cancel() {
        	Main.screenFrame.gamePanel.cardEffect(GameInfo.getMyInfo().getCardID().indexOf(GameInfo.getUsingCardID()), false);
        }
        
    }