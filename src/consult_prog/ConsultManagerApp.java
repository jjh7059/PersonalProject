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
	
	private String[] consultantArr = {"ȫ�浿", "�Ӳ���"};
	private String[] con_statArr = {"��㿹��", "���Ϸ�"};
	private String[] searchArr = {"��ü�˻�", "�ڵ�","����", "�̸�", "��㳯¥", "���Ϸ�", "��㿹��"};
	private JButton insertBtn, updateBtn, deleteBtn, cancelBtn, searchBtn, manageBtn;
	private JTable table;
	
	@SuppressWarnings("rawtypes")
	JComboBox consultantCB, con_statCB, searchCB;
	JTextField codeTF, nameTF, con_dateTF, searchTF;

	int state = 0;
	
	//< ���� ������ >
	class SubFrame extends JFrame implements ActionListener {
		private static final long serialVersionUID = 1L;
		
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public SubFrame() {
			setTitle("<�Է�>");
			setSize(500, 300);
			Dimension dim = getToolkit().getScreenSize();
			setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2
					- getHeight() / 2);
			
			JPanel center = new JPanel();
			getContentPane().add(center, BorderLayout.CENTER);
			center.setLayout(new GridLayout(7, 1));
			
			JPanel pcode = new JPanel();
			pcode.add(new JLabel("��    ��"));
			pcode.add(codeTF = new JTextField(10));
			
			JPanel pconsultant = new JPanel();
			pconsultant.add(new JLabel("�� �� ��"));
			pconsultant.add(consultantCB = new JComboBox(consultantArr));
			
			JPanel pname = new JPanel();
			pname.add(new JLabel("��    ��"));
			pname.add(nameTF = new JTextField(10));
			
			JPanel pcon_date = new JPanel();
			pcon_date.add(new JLabel("��㳯¥"));
			pcon_date.add(con_dateTF = new JTextField(10));
			
			JPanel pcon_stat = new JPanel();
			pcon_stat.add(new JLabel("������"));
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
			insertBtn.setFont(new Font("����", Font.BOLD, 12));
			subBottom.add(insertBtn);
			
			updateBtn = new JButton("UPDATE");
			updateBtn.setFont(new Font("����", Font.BOLD, 12));
			subBottom.add(updateBtn);
			
			deleteBtn = new JButton("DELETE");
			deleteBtn.setFont(new Font("����", Font.BOLD, 12));
			subBottom.add(deleteBtn);
			
			cancelBtn = new JButton("CANCEL");
			cancelBtn.setFont(new Font("����", Font.BOLD, 12));
			subBottom.add(cancelBtn);
			
			cancelBtn.addActionListener(this);
			insertBtn.addActionListener(this);
			updateBtn.addActionListener(this);
			deleteBtn.addActionListener(this);
			
			setVisible(true);
		}
		
		//���� �����ӿ� ��ġ�� �� ��ư�� ���� ���� ���
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
		
		//���� ��ư�� �ѹ� ������ ��
		public void updatebtnOneClick() {
			nameTF.setEnabled(false);
			con_dateTF.setEnabled(false);
			insertBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			state++;
		}
		
		//���� ��ư�� �ѹ� ������ ��
		public void deletebtnOneClick() {
			nameTF.setEnabled(false);
			con_dateTF.setEnabled(false);
			insertBtn.setEnabled(false);
			updateBtn.setEnabled(false);
			state++;
		}
	}
	
	//< ���� ������ >
	public ConsultManagerApp() {
		setTitle("��� ���� ���α׷�");
		
		JPanel bottom = new JPanel();
		getContentPane().add(bottom, BorderLayout.SOUTH);
		//bottom.setLayout(new GridLayout(1, 5));
		bottom.setLayout(new FlowLayout());
		
		manageBtn = new JButton("��   ��");
		manageBtn.setFont(new Font("����", Font.BOLD, 10));
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
				if(cb.getSelectedItem() == "���Ϸ�" || cb.getSelectedItem() == "��㿹��") {
					searchTF.setEditable(false);
				} else {
					searchTF.setEditable(true);
				}
			}
		});
		
		searchTF = new JTextField(10);
		north.add(searchTF);
		searchTF.setEditable(false);
		
		searchBtn = new JButton("Ű���� �˻�");
		searchBtn.setFont(new Font("����", Font.BOLD, 10));
		north.add(searchBtn);
		
		Object[] title = {"�ڵ�", "����", "�̸�", "��㳯¥", "������"};
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
	
	//��� ������� JTable ���۳�Ʈ�� ��� �޼ҵ�
	public void displayAllConsult() {
		List<ConsultDTO> consultList = ConsultDAO.getDAO().selectAllConsultList();
		
		if(consultList.isEmpty()) {
			JOptionPane.showMessageDialog(this, "����� ��������� �����ϴ�.");
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
	//JTextField�� �Էµ� �Է°��� ���� CONSULT ���̺� ���� �޼ҵ�
	public void addConsult() {
		String code = codeTF.getText();
		
		if(code.equals("")) {
			JOptionPane.showMessageDialog(this, "�ڵ带 �Է��� �ּ���.");
			codeTF.requestFocus();
			return;
		}
		
		String codeReg="^[A-Z]\\d{3}$";
		if(!Pattern.matches(codeReg, code)) {
			JOptionPane.showMessageDialog(this, "�ڵ�� ���빮�� 1�ڸ��� ����3�ڸ��θ� �Է��� �ּ���.");
			codeTF.requestFocus();
			return;
		}
		
		//JComboBox.getSelectedItem() : �޺��ڽ� ���ڿ� �� �������� �޼ҵ�
		String consultant = consultantCB.getSelectedItem().toString();
		
		String name = nameTF.getText();
		
		if(name.equals("")) {
			JOptionPane.showMessageDialog(this, "�̸��� �Է��� �ּ���.");
			nameTF.requestFocus();
			return;
		}
		
		String nameReg="[��-�R]{2,7}";
		if(!Pattern.matches(nameReg, name)) {
			JOptionPane.showMessageDialog(this, "�̸��� �ѱ۸� �Է��� �ּ���.");
			nameTF.requestFocus();
			return;
		}
		
		String con_date = con_dateTF.getText();
		
		if(con_date.equals("")) {
			JOptionPane.showMessageDialog(this, "��㳯¥�� �ݵ�� �Է��� �ּ���.");
			con_dateTF.requestFocus();
			return;
		}
		
		String con_dateReg="(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
		if(!Pattern.matches(con_dateReg, con_date)) {
			JOptionPane.showMessageDialog(this, "��¥�� ���Ŀ� �°� �Է��� �ּ���.");
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
		
		JOptionPane.showMessageDialog(this, rows + "���� �л������� ���� �Ͽ����ϴ�.");
		
		displayAllConsult();

	}
	
	//< DELETE >
	//�ڵ� JTextField�� �Էµ� �ڵ尪�� ���� �ش� �ڵ��� ��������� �����ϴ� �޼ҵ�
	public void removeConsult() {
		String code = codeTF.getText();
		
		if(code.equals("")) {
			JOptionPane.showMessageDialog(this, "�ڵ带 �Է��� �ּ���.");
			codeTF.requestFocus();
			return;
		}
		
		String codeReg="^[A-Z]\\d{3}$";
		if(!Pattern.matches(codeReg, code)) {
			JOptionPane.showMessageDialog(this, "�ڵ�� ���빮�� 1�ڸ��� ����3�ڸ��θ� �Է��� �ּ���.");
			codeTF.requestFocus();
			return;
		}
		
		int rows = ConsultDAO.getDAO().deleteConsult(code);
		
		if(rows > 0) {
			JOptionPane.showMessageDialog(this, rows + "���� ��������� ���� �Ͽ����ϴ�.");
			displayAllConsult();
		} else {
			JOptionPane.showMessageDialog(this, "�����ϰ��� �ϴ� �ڵ��� ��������� �������� �ʽ��ϴ�.");
		}
		
		
	}
	
	//�̺�Ʈ �ڵ鷯 �޼ҵ忡�� [UPDATE]���¿��� [����]��ư�� ���� ��� ȣ��
	public void searchCodeConsult() {
		String code = codeTF.getText();
		
		if(code.equals("")) {
			JOptionPane.showMessageDialog(this, "�ڵ带 �Է��� �ּ���.");
			codeTF.requestFocus();
			return;
		}
		
		String codeReg="^[A-Z]\\d{3}$";
		if(!Pattern.matches(codeReg, code)) {
			JOptionPane.showMessageDialog(this, "�ڵ�� ���빮�� 1�ڸ��� ����3�ڸ��θ� �Է��� �ּ���.");
			codeTF.requestFocus();
			return;
		}
		
		ConsultDTO consult = ConsultDAO.getDAO().selectCodeConsult(code);
		if(consult==null) {
			JOptionPane.showMessageDialog(this, "�����ϰ��� �ϴ� �ڵ��� ��������� �������� �ʽ��ϴ�.");
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
			JOptionPane.showMessageDialog(this, "�̸��� �Է��� �ּ���.");
			nameTF.requestFocus();
			return;
		}
		
		String nameReg="[��-�R]{2,7}";
		if(!Pattern.matches(nameReg, name)) {
			JOptionPane.showMessageDialog(this, "�̸��� �ѱ۸� �Է��� �ּ���.");
			nameTF.requestFocus();
			return;
		}
		
		String con_date = con_dateTF.getText();
		
		if(con_date.equals("")) {
			JOptionPane.showMessageDialog(this, "��㳯¥�� �ݵ�� �Է��� �ּ���.");
			con_dateTF.requestFocus();
			return;
		}
		
		String con_dateReg="(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
		if(!Pattern.matches(con_dateReg, con_date)) {
			JOptionPane.showMessageDialog(this, "��¥�� ���Ŀ� �°� �Է��� �ּ���.");
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
		
		JOptionPane.showMessageDialog(this, rows + "���� ��������� ���� �Ͽ����ϴ�.");
		
		displayAllConsult();

	}
	
	//�̸� JTextField�� �Էµ� �ڵ尪�� ���� �ش� �̸��� ��������� �˻��ϴ� �޼ҵ�
	public void searchNameConsult() {
		String name = nameTF.getText();
		
		if(name.equals("")) {
			JOptionPane.showMessageDialog(this, "�̸��� �Է��� �ּ���.");
			nameTF.requestFocus();
			return;
		}
		
		String nameReg="[��-�R]{2,7}";
		if(!Pattern.matches(nameReg, name)) {
			JOptionPane.showMessageDialog(this, "�̸��� �ѱ۸� �Է��� �ּ���.");
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
		
		if(type.equals("�ڵ�") || type.equals("�̸�") || type.equals("����") || type.equals("��㳯¥")) {
			if(val.equals("")) {
				JOptionPane.showMessageDialog(this, "�˻�� �Է��� �ּ���.");
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

