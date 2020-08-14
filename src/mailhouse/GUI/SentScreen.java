package mailhouse.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import mailhouse.client.Client;
import mailhouse.domain.Mail;
import mailhouse.domain.SubMail;

public class SentScreen extends JPanel {
	public static JLabel baseSent;
	private JTable table = new JTable();
	public Object[][] data = new Object[0][5];
	public String[] columnNames = { "mail_id", "to", "subject", "date"};
	private DefaultTableModel model = new DefaultTableModel(data,columnNames) {

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
};
	Font fLabel=new Font("Comic Sans MS",Font.BOLD,MailHome.screenSize.width/50);
	private Client c;
	public SentScreen(Client c){
//	public InboxScreen(Client c){
		this.c=c;
		baseSent = new JLabel();
		baseSent.setSize(MailHome.screenSize.width, MailHome.screenSize.height);
		
		BufferedImage imag = null;
		try {
		    imag = ImageIO.read(new File(MailHome.back_grnd));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Image dimg = imag.getScaledInstance(baseSent.getWidth(), baseSent.getHeight(),
		        Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		baseSent.setIcon(imageIcon);
		imageIcon = new ImageIcon(dimg);
	//homeFrame.add(Homebase);
		
		//baseInbox.setIcon(MailHome.imageIcon);
		this.add(baseSent);
		
		JPanel mailListPanel = new JPanel();
		mailListPanel.setBounds((int) (MailHome.screenSize.width/(3.5)), (int)(MailHome.screenSize.height/(10)),(int) (MailHome.screenSize.width/(1.25)), (MailHome.screenSize.height));
		Dimension d = new Dimension((int) (MailHome.screenSize.width/(1.5)), MailHome.screenSize.height/(1));
		
		mailListPanel.setPreferredSize(d);
		baseSent.add(mailListPanel);
		//Assign to model
		//table.setBounds(100,100,800,800);
		table.setBounds(MailHome.screenSize.width/(3), (int)(MailHome.screenSize.height/(15)), MailHome.screenSize.width, (MailHome.screenSize.height));
		table = new JTable(data,columnNames);
		table.setFont(new Font(Font.SANS_SERIF , Font.PLAIN, 20));
		table.setRowHeight(70);
		table.setShowHorizontalLines(true);
		JTableHeader header = table.getTableHeader();
	      header.setFont(fLabel);
	      //table.setForeground(Color.WHITE);
	      //table.setFont(fLabel);
	 
//		table.setSize(MailHome.screenSize.width, MailHome.screenSize.height);
	//	table.setRowHeight((int) (MailHome.screenSize.height/(4)));
		//table.setColumnW
		refreshTable();

		JScrollPane jp = new JScrollPane(table);
		jp.setOpaque(false);
		jp.getViewport().setOpaque(false);
		jp.setPreferredSize(mailListPanel.getPreferredSize());
		
		jp.setOpaque(false);
		//	jp.setSize(MailHome.screenSize.width, MailHome.screenSize.height);
		mailListPanel.setOpaque(false);
		mailListPanel.add(jp);

	//	this.setupListeneres();
		
		//Adding listener to table
		table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
               tableMouseClicked(evt);
            }
        });
		
		
	}
	
	//Table row click event handler
	 private void tableMouseClicked(java.awt.event.MouseEvent evt) {                                     
	        
	        // get the model from the jtable
	       DefaultTableModel model = (DefaultTableModel)table.getModel();

	        // get the selected row index
	       int selectedRowIndex = table.getSelectedRow();
	       int mailid = (Integer)model.getValueAt(selectedRowIndex,0);
	       Mail mail = new Mail();
	       mail.setMailId(mailid);
	       mail = this.c.getMail(mail);
	       final SeeMailScreen is = new SeeMailScreen(this.c);
   			MailHome.parent.add(is,"5");
   			MailHome.c1.show(MailHome.parent, "5");
	        // set the selected row data
//	       jTextFieldID.setText(model.getValueAt(selectedRowIndex, 0).toString());
//	       jTextFieldFN.setText(model.getValueAt(selectedRowIndex, 1).toString());
//	       jTextFieldLN.setText(model.getValueAt(selectedRowIndex, 2).toString());
//	       jTextFieldAGE.setText(model.getValueAt(selectedRowIndex, 3).toString());
	        
	    }                                   

	
	public void refreshTable() {
		fillmodel();
		
		for (int i = 0; i < data.length; i++) {
			System.out.println(data[i][0]+" : "+data[i][1]+ " : "+data[i][2]);
			model.addRow(data[i]);
		}
		table.setModel(model);


	}
	
	public void fillmodel(){
	
		List<SubMail> mList = c.getSentMails(c.user);
		int i = 0;
		data = new Object[mList.size()][5];
		for (SubMail sm : mList) {
			if (sm != null) {
				//data[i][0] = i + 1;
				data[i][0] = sm.getMailId();
				data[i][1] = sm.getFromUser().getEmail();
				data[i][2] = sm.getSubject();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String datetime = sm.getDateTime().format(formatter);
				data[i][3] = datetime;
				} 
			i++;
		}
	}

}
