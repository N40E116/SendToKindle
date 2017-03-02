package email;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EmailFrame extends JFrame {
	
	private static final long serialVersionUID = -4060711742299578672L;

	private static Vector<String> filelist = new Vector<String>();
	
	
    public static final int BOARD_WIDTH = 540;
    public static final int BOARD_HEIGHT = 350;
    public static final int X_LOCATION = 100;
    public static final int Y_LOCATION = 50;
    
    
    //把节目分为上中三个Panel
    JPanel northPanel = new JPanel();    
    JPanel centerPanel = new JPanel();
    JPanel southPanel = new JPanel();   
       
    //输入
    JTextField pathField = new JTextField(30);
    JLabel pathLabel = new JLabel("文件名: ", JLabel.RIGHT);   
    JButton chosepathButton = new JButton("选择文件");
    //输出
    JTextArea fileListArea = new JTextArea(10, 40);
    JLabel fileListLabel = new JLabel("已选文件");    
    //按钮
    JButton startButton = new JButton("开始");
    JButton cancelButton = new JButton("取消");    
    
    public void launchFrame() throws Exception{        
        //界面初始化    	
    	this.setTitle("Send To Kindle");
        this.setLocation(X_LOCATION, Y_LOCATION);
        this.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new FlowLayout()); 
        northPanel.setSize(20, BOARD_WIDTH);
        this.add(northPanel);
        centerPanel.setSize(300, BOARD_WIDTH);
        this.add(centerPanel);
        southPanel.setSize(20, BOARD_WIDTH);
        this.add(southPanel);     
        
        
        northPanel.add(pathLabel);
        northPanel.add(pathField);
        northPanel.add(chosepathButton);
        centerPanel.setLayout(new FlowLayout());
        centerPanel.add(fileListLabel);
        centerPanel.add(new JScrollPane(fileListArea));
        southPanel.setLayout(new FlowLayout());
        southPanel.add(startButton);
        southPanel.add(cancelButton);

        
        chosepathButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		JFileChooser jfc = new JFileChooser(); 
        		jfc.setMultiSelectionEnabled(true);
        		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );  
        		jfc.showDialog(new JLabel(), "选择");  
        		
        		
//        		File file=jfc.getSelectedFile();  
//        		String path = file.getAbsolutePath();
//        		pathField.setText(path);
//        		filelist.add(path);
        		
        		File[] files = jfc.getSelectedFiles();
        		if(files.length > 0){
        			for(File file :files){
        				filelist.add(file.getAbsolutePath());
        			} 
        			pathField.setText(files[files.length - 1].getParent());
        		}
        			
        		
                fileListArea.setText("");
                if(filelist.size() != 0){
	                for (Iterator<String> iterator = filelist.iterator(); iterator
							.hasNext();) {
						fileListArea.append(iterator.next() + "\r\n");					
					}                
//	                filelist.clear();
                }else{
                	fileListArea.append("未选择文件");	
                }
        	}
        });
    
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

            	southPanel.setCursor(new Cursor (Cursor.WAIT_CURSOR));
//            	SimpleMailSender.sendToKindle(path);
            	SimpleMailSender.sendToKindle(filelist.toArray(new String[filelist.size()]));
            	southPanel.setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
            	
            	filelist.clear();
            	fileListArea.append("\r\n发送成功");
            }
        });   
        
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileListArea.setText("");
                filelist.clear();
            }
        });         
    }
    


    
    public static void main(String[] args)throws Exception {
          new EmailFrame().launchFrame();
    }
    
}
