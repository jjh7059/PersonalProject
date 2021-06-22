package consult_prog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ConsultManagerApp extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private String[] consultantArr = {"È«±æµ¿", "ÀÓ²©Á¤"};
	private String[] con_statArr = {"»ó´ã¿¹¾à", "»ó´ã¿Ï·á"};
	private String[] searchArr = {"ÀüÃ¼°Ë»ö", "ÄÚµå","»ó´ã»ç", "ÀÌ¸§", "»ó´ã³¯Â¥", "»ó´ã¿Ï·á", "»ó´ã¿¹¾à"};
	private JButton insertBtn, updateBtn, deleteBtn, cancelBtn, searchBtn, manageBtn;
	private JTable table;
	
	@SuppressWarnings("rawtypes")
	JComboBox consultantCB, con_statCB, searchCB;
	JTextField codeTF, nameTF, con_dateTF, searchTF;

	int state = 0;
	
	//< ¼­ºê ÇÁ·¹ÀÓ >
	class SubFrame extends JFrame implements ActionListener {
		private static final long serialVersionUID = 1L;
		
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public SubFrame() {
			setTitle("<ÀÔ·Â>");
			setSize(500, 300);
			Dimension dim = getToolkit().getScreenSize();
			setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2
					- getHeight() / 2);
			
			JPanel center = new JPanel();
			getContentPane().add(center, BorderLayout.CENTER);
			center.setLayout(new GridLayout(7, 1));
			
			JPanel pcode = new JPanel();
			pcode.add(new JLabel("ÄÚ    µå"));
			pcode.add(codeTF = new JTextField(10));
			
			JPanel pconsultant = new JPanel();
			pconsultant.add(new JLabel("»ó ´ã »ç"));
			pconsultant.add(consultantCB = new JComboBox(consultantArr));
			
			JPanel pname = new JPanel();
			pname.add(new JLabel("ÀÌ    ¸§"));
			pname.add(nameTF = new JTextField(10));
			
			JPanel pcon_date = new JPanel();
			pcon_date.add(new JLabel("»ó´ã³¯Â¥"));
			pcon_date.add(con_dateTF = new JTextField(10));
			
			JPanel pcon_stat = new JPanel();
			pcon_stat.add(new JLabel("»ó´ã»óÅÂ"));
			pcon_stat.add(con_statCB = new JComboBox(con_statArr));
			
			center.add(pcode);
			center.add(pconsultant);
			center.add(pname);
			center.add(pcon_date);
			center.add(pcon_stat);
			center.setAlignmentX(LEFT_ALIGNMENT);
			
			JPanel subBottom = new JPanel();
			getContentPane().add(subBottom, BorderLayout.SOUTH);
			subBottom.setLayout(new FlowLayout());
			
			insertBtn = new JButton("ADD");
			insertBtn.setFont(new Font("±¼¸²", Font.BOLD, 12));
			subBottom.add(insertBtn);
			
			updateBtn = new JButton("UPDATE");
			updateBtn.setFont(new Font("±¼¸²", Font.BOLD, 12));
			subBottom.add(updateBtn);
			
			deleteBtn = new JButton("DELETE");
			deleteBtn.setFont(new Font("±¼¸²", Font.BOLD, 12));
			subBottom.add(deleteBtn);
			
			cancelBtn = new JButton("CANCEL");
			cancelBtn.setFont(new Font("±¼¸²", Font.BOLD, 12));
			subBottom.add(cancelBtn);
			
			cancelBtn.addActionListener(this);
			insertBtn.addActionListener(this);
			updateBtn.addActionListener(this);
			deleteBtn.addActionListener(this);
			
			setVisible(true);
		}
		
		//¼­ºê ÇÁ·¹ÀÓ¿¡ ¹èÄ¡µÈ °¢ ¹öÆ°¿¡ ´ëÇÑ ¼öÇà ¸í·É
		@Override
		public void actionPerformed(ActionEvent e) {
			Object c = e.getSource();
			if(c == insertBtn)	{
				addConsult();
				displayAllConsult();
				dispose();
			} else if(c == cancelBtn) {
				dispose();
			} else if(c == deleteBtn) {
				if(state == 0) {
					deletebtnOneClick();	
				} else if(state == 1){
					removeConsult();
					displayAllConsult();
					dispose();
				}
			} else if(c == updateBtn) {
				if(state == 0) {
					updatebtnOneClick();	
				} else if(state == 1){
					searchCodeConsult();
					state++;
				} else {
					modifyConsult();
					dispose();
					state = 0;
				}
			}
		}
		
		//º¯°æ ¹öÆ°À» ÇÑ¹ø ´­·¶À» ¶§
		public void updatebtnOneClick() {
			nameTF.setEnabled(false);
			con_dateTF.setEnabled(false);
			insertBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			state++;
		}
		
		//»èÁ¦ ¹öÆ°À» ÇÑ¹ø ´­·¶À» ¶§
		public void deletebtnOneClick() {
			nameTF.setEnabled(false);
			con_dateTF.setEnabled(false);
			insertBtn.setEnabled(false);
			updateBtn.setEnabled(false);
			state++;
		}
	}
	
	//< ¸ÞÀÎ ÇÁ·¹ÀÓ >
	public ConsultManagerApp() {
		setTitle("»ó´ã °ü¸® ÇÁ·Î±×·¥");
		
		JPanel bottom = new JPanel();
		getContentPane().add(bottom, BorderLayout.SOUTH);
		//bottom.setLayout(new GridLayout(1, 5));
		bottom.setLayout(new FlowLayout());
		
		manageBtn = new JButton("°ü   ¸®");
		manageBtn.setFont(new Font("±¼¸²", Font.BOLD, 10));
		bottom.add(manageBtn);
		
		JPanel north = new JPanel();
		getContentPane().add(north, BorderLayout.NORTH);
		north.setLayout(new FlowLayout());
		
		searchCB =  new JComboBox<String>(searchArr);
		north.add(searchCB);
		
		searchCB.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<?> cb = (JComboBox<?>)e.getSource();
				if(cb.getSelectedItem() == "»ó´ã¿Ï·á" || cb.getSelectedItem() == "»ó´ã¿¹¾à") {
					searchTF.setEditable(false);
				} else {
					searchTF.setEditable(true);
				}
			}
		});
		
		searchTF = new JTextField(10);
		north.add(searchTF);
		searchTF.setEditable(false);
		
		searchBtn = new JButton("Å°¿öµå °Ë»ö");
		searchBtn.setFont(new Font("±¼¸²", Font.BOLD, 10));
		north.add(searchBtn);
		
		Object[] title = {"ÄÚµå", "»ó´ã»ç", "ÀÌ¸§", "»ó´ã³¯Â¥", "»ó´ã»óÅÂ"};
		table = new JTable(new DefaultTableModel(title, 0));
		table.setEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		
		searchBtn.addActionListener(new ButtonEventHandler());
		manageBtn.addActionListener(new ButtonEventHandler());

		displayAllConsult();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 700, 490);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ConsultManagerApp();
	}
	
	public class ButtonEventHandler implements ActionListener {
		SubFrame sf;
		@Override
		public void actionPerformed(ActionEvent ev) {
			Component c = (Component) ev.getSource();
			if(c == manageBtn) {
				sf = new SubFrame();
			} else if(c == searchBtn) {
				keywordSearch();
				searchTF.setText("");
			}
			
		}
	}
	
	//¸ðµç »ó´ãÁ¤º¸ JTable ÄÄÆÛ³ÍÆ®¿¡ Ãâ·Â ¸Þ¼Òµå
	public void displayAllConsult() {
		List<ConsultDTO> consultList = ConsultDAO.getDAO().selectAllConsultList();
		
		if(consultList.isEmpty()) {
			JOptionPane.showMessageDialog(this, "ÀúÀåµÈ »ó´ãÁ¤º¸°¡ ¾ø½À´Ï´Ù.");
			return;
		}
		
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		
		for(int i = model.getRowCount(); i > 0; i--) {
			model.removeRow(0);
		}
		
		for(ConsultDTO consult:consultList) {
			Vector<Object> rowData = new Vector<Object>();
			rowData.add(consult.getCode());
			rowData.add(consult.getConsultant());
			rowData.add(consult.getName());
			rowData.add(consult.getCon_date());
			rowData.add(consult.getCon_stat());
			model.addRow(rowData);
		}
	}
	
	//< INSERT >
	//JTextField¿¡ ÀÔ·ÂµÈ ÀÔ·Â°ªÀ» ¾ò¾î¿Í CONSULT Å×ÀÌºí¿¡ ÀúÀå ¸Þ¼Òµå
	public void addConsult() {
		String code = codeTF.getText();
		
		if(code.equals("")) {
			JOptionPane.showMessageDialog(this, "ÄÚµå¸¦ ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			codeTF.requestFocus();
			return;
		}
		
		String codeReg="^[A-Z]\\d{3}$";
		if(!Pattern.matches(codeReg, code)) {
			JOptionPane.showMessageDialog(this, "ÄÚµå´Â ¿µ´ë¹®ÀÚ 1ÀÚ¸®¿Í ¼ýÀÚ3ÀÚ¸®·Î¸¸ ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			codeTF.requestFocus();
			return;
		}
		
		//JComboBox.getSelectedItem() : ÄÞº¸¹Ú½º ¹®ÀÚ¿­ °ª °¡Á®¿À´Â ¸Þ¼Òµå
		String consultant = consultantCB.getSelectedItem().toString();
		
		String name = nameTF.getText();
		
		if(name.equals("")) {
			JOptionPane.showMessageDialog(this, "ÀÌ¸§À» ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			nameTF.requestFocus();
			return;
		}
		
		String nameReg="[°¡-ÆR]{2,7}";
		if(!Pattern.matches(nameReg, name)) {
			JOptionPane.showMessageDialog(this, "ÀÌ¸§Àº ÇÑ±Û¸¸ ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			nameTF.requestFocus();
			return;
		}
		
		String con_date = con_dateTF.getText();
		
		if(con_date.equals("")) {
			JOptionPane.showMessageDialog(this, "»ó´ã³¯Â¥¸¦ ¹Ýµå½Ã ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			con_dateTF.requestFocus();
			return;
		}
		
		String con_dateReg="(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
		if(!Pattern.matches(con_dateReg, con_date)) {
			JOptionPane.showMessageDialog(this, "³¯Â¥¸¦ Çü½Ä¿¡ ¸Â°Ô ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			con_dateTF.requestFocus();
			return;
		}
		
		String con_stat = con_statCB.getSelectedItem().toString();
		
		ConsultDTO consult = new ConsultDTO();
		consult.setCode(code);
		consult.setConsultant(consultant);
		consult.setName(name);
		consult.setCon_date(con_date);
		consult.setCon_stat(con_stat);
		
		int rows = ConsultDAO.getDAO().insertConsult(consult);
		
		JOptionPane.showMessageDialog(this, rows + "¸íÀÇ ÇÐ»ýÁ¤º¸¸¦ ÀúÀå ÇÏ¿´½À´Ï´Ù.");
		
		displayAllConsult();

	}
	
	//< DELETE >
	//ÄÚµå JTextField¿¡ ÀÔ·ÂµÈ ÄÚµå°ªÀ» ¾ò¾î¿Í ÇØ´ç ÄÚµåÀÇ »ó´ãÁ¤º¸¸¦ »èÁ¦ÇÏ´Â ¸Þ¼Òµå
	public void removeConsult() {
		String code = codeTF.getText();
		
		if(code.equals("")) {
			JOptionPane.showMessageDialog(this, "ÄÚµå¸¦ ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			codeTF.requestFocus();
			return;
		}
		
		String codeReg="^[A-Z]\\d{3}$";
		if(!Pattern.matches(codeReg, code)) {
			JOptionPane.showMessageDialog(this, "ÄÚµå´Â ¿µ´ë¹®ÀÚ 1ÀÚ¸®¿Í ¼ýÀÚ3ÀÚ¸®·Î¸¸ ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			codeTF.requestFocus();
			return;
		}
		
		int rows = ConsultDAO.getDAO().deleteConsult(code);
		
		if(rows > 0) {
			JOptionPane.showMessageDialog(this, rows + "¸íÀÇ »ó´ãÁ¤º¸¸¦ »èÁ¦ ÇÏ¿´½À´Ï´Ù.");
			displayAllConsult();
		} else {
			JOptionPane.showMessageDialog(this, "»èÁ¦ÇÏ°íÀÚ ÇÏ´Â ÄÚµåÀÇ »ó´ãÁ¤º¸°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
		}
		
		
	}
	
	//ÀÌº¥Æ® ÇÚµé·¯ ¸Þ¼Òµå¿¡¼­ [UPDATE]»óÅÂ¿¡¼­ [º¯°æ]¹öÆ°À» ´©¸¥ °æ¿ì È£Ãâ
	public void searchCodeConsult() {
		String code = codeTF.getText();
		
		if(code.equals("")) {
			JOptionPane.showMessageDialog(this, "ÄÚµå¸¦ ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			codeTF.requestFocus();
			return;
		}
		
		String codeReg="^[A-Z]\\d{3}$";
		if(!Pattern.matches(codeReg, code)) {
			JOptionPane.showMessageDialog(this, "ÄÚµå´Â ¿µ´ë¹®ÀÚ 1ÀÚ¸®¿Í ¼ýÀÚ3ÀÚ¸®·Î¸¸ ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			codeTF.requestFocus();
			return;
		}
		
		ConsultDTO consult = ConsultDAO.getDAO().selectCodeConsult(code);
		if(consult==null) {
			JOptionPane.showMessageDialog(this, "º¯°æÇÏ°íÀÚ ÇÏ´Â ÄÚµåÀÇ »ó´ãÁ¤º¸°¡ Á¸ÀçÇÏÁö ¾Ê½À´Ï´Ù.");
			codeTF.requestFocus();
			codeTF.setText("");
			return;
		}
		
		codeTF.setText(consult.getCode());
		nameTF.setText(consult.getName());
		nameTF.setEnabled(true);
		con_dateTF.setText(consult.getCon_date());
		con_dateTF.setEnabled(true);
		
	}
	
	public void modifyConsult() {
		String code = codeTF.getText();
		
		String consultant = consultantCB.getSelectedItem().toString();
		
		String name = nameTF.getText();
		
		if(name.equals("")) {
			JOptionPane.showMessageDialog(this, "ÀÌ¸§À» ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			nameTF.requestFocus();
			return;
		}
		
		String nameReg="[°¡-ÆR]{2,7}";
		if(!Pattern.matches(nameReg, name)) {
			JOptionPane.showMessageDialog(this, "ÀÌ¸§Àº ÇÑ±Û¸¸ ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			nameTF.requestFocus();
			return;
		}
		
		String con_date = con_dateTF.getText();
		
		if(con_date.equals("")) {
			JOptionPane.showMessageDialog(this, "»ó´ã³¯Â¥¸¦ ¹Ýµå½Ã ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			con_dateTF.requestFocus();
			return;
		}
		
		String con_dateReg="(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
		if(!Pattern.matches(con_dateReg, con_date)) {
			JOptionPane.showMessageDialog(this, "³¯Â¥¸¦ Çü½Ä¿¡ ¸Â°Ô ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			con_dateTF.requestFocus();
			return;
		}
		
		String con_stat = con_statCB.getSelectedItem().toString();
		
		ConsultDTO consult = new ConsultDTO();
		consult.setCode(code);
		consult.setConsultant(consultant);
		consult.setName(name);
		consult.setCon_date(con_date);
		consult.setCon_stat(con_stat);
		
		int rows = ConsultDAO.getDAO().updateConsult(consult);
		
		JOptionPane.showMessageDialog(this, rows + "¸íÀÇ »ó´ãÁ¤º¸¸¦ º¯°æ ÇÏ¿´½À´Ï´Ù.");
		
		displayAllConsult();

	}
	
	//ÀÌ¸§ JTextField¿¡ ÀÔ·ÂµÈ ÄÚµå°ªÀ» ¾ò¾î¿Í ÇØ´ç ÀÌ¸§ÀÇ »ó´ãÁ¤º¸¸¦ °Ë»öÇÏ´Â ¸Þ¼Òµå
	public void searchNameConsult() {
		String name = nameTF.getText();
		
		if(name.equals("")) {
			JOptionPane.showMessageDialog(this, "ÀÌ¸§À» ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			nameTF.requestFocus();
			return;
		}
		
		String nameReg="[°¡-ÆR]{2,7}";
		if(!Pattern.matches(nameReg, name)) {
			JOptionPane.showMessageDialog(this, "ÀÌ¸§Àº ÇÑ±Û¸¸ ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
			nameTF.requestFocus();
			return;
		}
		
		List<ConsultDTO> consultList = ConsultDAO.getDAO().selectNameConsultList(name);
		
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		
		for(int i = model.getRowCount(); i > 0; i--) {
			model.removeRow(0);
		}
		
		for(ConsultDTO consult:consultList) {
			Vector<Object> rowData = new Vector<Object>();
			rowData.add(consult.getCode());
			rowData.add(consult.getConsultant());
			rowData.add(consult.getName());
			rowData.add(consult.getCon_date());
			rowData.add(consult.getCon_stat());
			model.addRow(rowData);
		}
		

	}
	
	public void keywordSearch() {
		String type = searchCB.getSelectedItem().toString();
		String val = searchTF.getText().replace(" ", "");
		
		if(type.equals("ÄÚµå") || type.equals("ÀÌ¸§") || type.equals("»ó´ã»ç") || type.equals("»ó´ã³¯Â¥")) {
			if(val.equals("")) {
				JOptionPane.showMessageDialog(this, "°Ë»ö¾î¸¦ ÀÔ·ÂÇØ ÁÖ¼¼¿ä.");
				searchTF.requestFocus();
				return;
			}
		}
		
		List<ConsultDTO> consultList = ConsultDAO.getDAO().keywordSearchList(val,type);
		
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		
		for(int i = model.getRowCount(); i > 0; i--) {
			model.removeRow(0);
		}
		
		for(ConsultDTO consult:consultList) {
			Vector<Object> rowData = new Vector<Object>();
			rowData.add(consult.getCode());
			rowData.add(consult.getConsultant());
			rowData.add(consult.getName());
			rowData.add(consult.getCon_date());
			rowData.add(consult.getCon_stat());
			model.addRow(rowData);
		}
		
		
	}
}

