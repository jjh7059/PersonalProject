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
	
	private String[] consultantArr = {"홍길동", "임꺽정"};
	private String[] con_statArr = {"상담예약", "상담완료"};
	private String[] searchArr = {"전체검색", "코드","상담사", "이름", "상담날짜", "상담완료", "상담예약"};
	private JButton insertBtn, updateBtn, deleteBtn, cancelBtn, searchBtn, manageBtn;
	private JTable table;
	
	@SuppressWarnings("rawtypes")
	JComboBox consultantCB, con_statCB, searchCB;
	JTextField codeTF, nameTF, con_dateTF, searchTF;

	int state = 0;
	
	//< 서브 프레임 >
	class SubFrame extends JFrame implements ActionListener {
		private static final long serialVersionUID = 1L;
		
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public SubFrame() {
			setTitle("<입력>");
			setSize(500, 300);
			Dimension dim = getToolkit().getScreenSize();
			setLocation(dim.width / 2 - getWidth() / 2, dim.height / 2
					- getHeight() / 2);
			
			JPanel center = new JPanel();
			getContentPane().add(center, BorderLayout.CENTER);
			center.setLayout(new GridLayout(7, 1));
			
			JPanel pcode = new JPanel();
			pcode.add(new JLabel("코    드"));
			pcode.add(codeTF = new JTextField(10));
			
			JPanel pconsultant = new JPanel();
			pconsultant.add(new JLabel("상 담 사"));
			pconsultant.add(consultantCB = new JComboBox(consultantArr));
			
			JPanel pname = new JPanel();
			pname.add(new JLabel("이    름"));
			pname.add(nameTF = new JTextField(10));
			
			JPanel pcon_date = new JPanel();
			pcon_date.add(new JLabel("상담날짜"));
			pcon_date.add(con_dateTF = new JTextField(10));
			
			JPanel pcon_stat = new JPanel();
			pcon_stat.add(new JLabel("상담상태"));
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
			insertBtn.setFont(new Font("굴림", Font.BOLD, 12));
			subBottom.add(insertBtn);
			
			updateBtn = new JButton("UPDATE");
			updateBtn.setFont(new Font("굴림", Font.BOLD, 12));
			subBottom.add(updateBtn);
			
			deleteBtn = new JButton("DELETE");
			deleteBtn.setFont(new Font("굴림", Font.BOLD, 12));
			subBottom.add(deleteBtn);
			
			cancelBtn = new JButton("CANCEL");
			cancelBtn.setFont(new Font("굴림", Font.BOLD, 12));
			subBottom.add(cancelBtn);
			
			cancelBtn.addActionListener(this);
			insertBtn.addActionListener(this);
			updateBtn.addActionListener(this);
			deleteBtn.addActionListener(this);
			
			setVisible(true);
		}
		
		//서브 프레임에 배치된 각 버튼에 대한 수행 명령
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
		
		//변경 버튼을 한번 눌렀을 때
		public void updatebtnOneClick() {
			nameTF.setEnabled(false);
			con_dateTF.setEnabled(false);
			insertBtn.setEnabled(false);
			deleteBtn.setEnabled(false);
			state++;
		}
		
		//삭제 버튼을 한번 눌렀을 때
		public void deletebtnOneClick() {
			nameTF.setEnabled(false);
			con_dateTF.setEnabled(false);
			insertBtn.setEnabled(false);
			updateBtn.setEnabled(false);
			state++;
		}
	}
	
	//< 메인 프레임 >
	public ConsultManagerApp() {
		setTitle("상담 관리 프로그램");
		
		JPanel bottom = new JPanel();
		getContentPane().add(bottom, BorderLayout.SOUTH);
		//bottom.setLayout(new GridLayout(1, 5));
		bottom.setLayout(new FlowLayout());
		
		manageBtn = new JButton("관   리");
		manageBtn.setFont(new Font("굴림", Font.BOLD, 10));
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
				if(cb.getSelectedItem() == "상담완료" || cb.getSelectedItem() == "상담예약") {
					searchTF.setEditable(false);
				} else {
					searchTF.setEditable(true);
				}
			}
		});
		
		searchTF = new JTextField(10);
		north.add(searchTF);
		searchTF.setEditable(false);
		
		searchBtn = new JButton("키워드 검색");
		searchBtn.setFont(new Font("굴림", Font.BOLD, 10));
		north.add(searchBtn);
		
		Object[] title = {"코드", "상담사", "이름", "상담날짜", "상담상태"};
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
	
	//모든 상담정보 JTable 컴퍼넌트에 출력 메소드
	public void displayAllConsult() {
		List<ConsultDTO> consultList = ConsultDAO.getDAO().selectAllConsultList();
		
		if(consultList.isEmpty()) {
			JOptionPane.showMessageDialog(this, "저장된 상담정보가 없습니다.");
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
	//JTextField에 입력된 입력값을 얻어와 CONSULT 테이블에 저장 메소드
	public void addConsult() {
		String code = codeTF.getText();
		
		if(code.equals("")) {
			JOptionPane.showMessageDialog(this, "코드를 입력해 주세요.");
			codeTF.requestFocus();
			return;
		}
		
		String codeReg="^[A-Z]\\d{3}$";
		if(!Pattern.matches(codeReg, code)) {
			JOptionPane.showMessageDialog(this, "코드는 영대문자 1자리와 숫자3자리로만 입력해 주세요.");
			codeTF.requestFocus();
			return;
		}
		
		//JComboBox.getSelectedItem() : 콤보박스 문자열 값 가져오는 메소드
		String consultant = consultantCB.getSelectedItem().toString();
		
		String name = nameTF.getText();
		
		if(name.equals("")) {
			JOptionPane.showMessageDialog(this, "이름을 입력해 주세요.");
			nameTF.requestFocus();
			return;
		}
		
		String nameReg="[가-힣]{2,7}";
		if(!Pattern.matches(nameReg, name)) {
			JOptionPane.showMessageDialog(this, "이름은 한글만 입력해 주세요.");
			nameTF.requestFocus();
			return;
		}
		
		String con_date = con_dateTF.getText();
		
		if(con_date.equals("")) {
			JOptionPane.showMessageDialog(this, "상담날짜를 반드시 입력해 주세요.");
			con_dateTF.requestFocus();
			return;
		}
		
		String con_dateReg="(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
		if(!Pattern.matches(con_dateReg, con_date)) {
			JOptionPane.showMessageDialog(this, "날짜를 형식에 맞게 입력해 주세요.");
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
		
		JOptionPane.showMessageDialog(this, rows + "명의 학생정보를 저장 하였습니다.");
		
		displayAllConsult();

	}
	
	//< DELETE >
	//코드 JTextField에 입력된 코드값을 얻어와 해당 코드의 상담정보를 삭제하는 메소드
	public void removeConsult() {
		String code = codeTF.getText();
		
		if(code.equals("")) {
			JOptionPane.showMessageDialog(this, "코드를 입력해 주세요.");
			codeTF.requestFocus();
			return;
		}
		
		String codeReg="^[A-Z]\\d{3}$";
		if(!Pattern.matches(codeReg, code)) {
			JOptionPane.showMessageDialog(this, "코드는 영대문자 1자리와 숫자3자리로만 입력해 주세요.");
			codeTF.requestFocus();
			return;
		}
		
		int rows = ConsultDAO.getDAO().deleteConsult(code);
		
		if(rows > 0) {
			JOptionPane.showMessageDialog(this, rows + "명의 상담정보를 삭제 하였습니다.");
			displayAllConsult();
		} else {
			JOptionPane.showMessageDialog(this, "삭제하고자 하는 코드의 상담정보가 존재하지 않습니다.");
		}
		
		
	}
	
	//이벤트 핸들러 메소드에서 [UPDATE]상태에서 [변경]버튼을 누른 경우 호출
	public void searchCodeConsult() {
		String code = codeTF.getText();
		
		if(code.equals("")) {
			JOptionPane.showMessageDialog(this, "코드를 입력해 주세요.");
			codeTF.requestFocus();
			return;
		}
		
		String codeReg="^[A-Z]\\d{3}$";
		if(!Pattern.matches(codeReg, code)) {
			JOptionPane.showMessageDialog(this, "코드는 영대문자 1자리와 숫자3자리로만 입력해 주세요.");
			codeTF.requestFocus();
			return;
		}
		
		ConsultDTO consult = ConsultDAO.getDAO().selectCodeConsult(code);
		if(consult==null) {
			JOptionPane.showMessageDialog(this, "변경하고자 하는 코드의 상담정보가 존재하지 않습니다.");
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
			JOptionPane.showMessageDialog(this, "이름을 입력해 주세요.");
			nameTF.requestFocus();
			return;
		}
		
		String nameReg="[가-힣]{2,7}";
		if(!Pattern.matches(nameReg, name)) {
			JOptionPane.showMessageDialog(this, "이름은 한글만 입력해 주세요.");
			nameTF.requestFocus();
			return;
		}
		
		String con_date = con_dateTF.getText();
		
		if(con_date.equals("")) {
			JOptionPane.showMessageDialog(this, "상담날짜를 반드시 입력해 주세요.");
			con_dateTF.requestFocus();
			return;
		}
		
		String con_dateReg="(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";
		if(!Pattern.matches(con_dateReg, con_date)) {
			JOptionPane.showMessageDialog(this, "날짜를 형식에 맞게 입력해 주세요.");
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
		
		JOptionPane.showMessageDialog(this, rows + "명의 상담정보를 변경 하였습니다.");
		
		displayAllConsult();

	}
	
	//이름 JTextField에 입력된 코드값을 얻어와 해당 이름의 상담정보를 검색하는 메소드
	public void searchNameConsult() {
		String name = nameTF.getText();
		
		if(name.equals("")) {
			JOptionPane.showMessageDialog(this, "이름을 입력해 주세요.");
			nameTF.requestFocus();
			return;
		}
		
		String nameReg="[가-힣]{2,7}";
		if(!Pattern.matches(nameReg, name)) {
			JOptionPane.showMessageDialog(this, "이름은 한글만 입력해 주세요.");
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
		
		if(type.equals("코드") || type.equals("이름") || type.equals("상담사") || type.equals("상담날짜")) {
			if(val.equals("")) {
				JOptionPane.showMessageDialog(this, "검색어를 입력해 주세요.");
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

