package kau;

import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


// UI 작성
public class Employee extends JFrame implements ActionListener{

    // 1. 멤버변수 선언

    // 1-1. DB 연결 변수
    public Connection connection;
    public Statement state;
    public ResultSet result;

    JSeparator horizontalSeparator = new JSeparator(SwingConstants.HORIZONTAL);


    private JComboBox<String> Attribute;
    private JComboBox<String> Dept;
    // 이름, 주민번호, 생일, 주소, 연봉(기호는 select되게), 상사 검색
    private JTextField textField;
    private String[] SalaryOption = {">=",">","=","<","<=S"};
    private String[] SexOption = {"전체", "F", "M"};
    private String[] DepartMentOption = {"Research","Administration","Headquarters"};


    // CheckBox
    private JCheckBox name = new JCheckBox("Name", true);
    private JCheckBox ssn = new JCheckBox("Ssn", true);
    private JCheckBox bdate = new JCheckBox("Bdate", true);
    private JCheckBox address = new JCheckBox("Address", true);
    private JCheckBox sex = new JCheckBox("Sex", true);
    private JCheckBox salary = new JCheckBox("Salary", true);
    private JCheckBox supervisor = new JCheckBox("Supervisor", true);
    private JCheckBox department = new JCheckBox("Department", true);
    private Vector<String> Head = new Vector<String>();

    private JTable table;
    private DefaultTableModel model;
    private static final int BOOLEAN_COLUMN = 0;
    private int NAME_COLUMN = 0;
    private int SALARY_COLUMN = 0;

    private String dShow ;

    private JButton Search_Btn = new JButton("검색");
    Container me = this;


    private JLabel totalEmp = new JLabel("인원수 : ");
    private JLabel totalCount = new JLabel();

    JPanel panel;
    JScrollPane ScPane;
    private JLabel Emplabel = new JLabel("선택한 직원");
    private JLabel ShowSelectedEmp = new JLabel();
    private JLabel Setlabel = new JLabel();
    private JTextField SetSalary = new JTextField(10);
    private JButton Update_Btn = new JButton("UPDATE");
    private JButton Delete_Btn = new JButton("선택한 데이터 삭제");
    int count = 0 ;


    //사용자 삽입 UI
    private JTextField InsertName = new JTextField(20);
    private JTextField InsertSsn = new JTextField(20);
    private JTextField InsertBdate = new JTextField(20);
    private JTextField InsertAddress = new JTextField(20);
    private JTextField InsertSex = new JTextField(20);
    private JTextField InsertSalary = new JTextField(20);
    private JTextField InsertSuper = new JTextField(20);
    private JTextField InsertDepartment = new JTextField(20);
    private JButton Insert_Btn = new JButton("데이터 삽입");




    public Employee(){
        JPanel ComboBoxPanel = new JPanel();

        // 첫 번째 JComboBox(검색 전체 범위)
        String[] attribute = {"전체","이름","주민번호", "생일", "성별", "주소", "연봉", "상사 이름", "부서"};

        Attribute = new JComboBox<>(attribute);
        ComboBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ComboBoxPanel.add(new JLabel("검색 범위"));
        ComboBoxPanel.add(Attribute);


        //두 번째 JComboBox(선택지 -> 새로운 선택지)
        Dept = new JComboBox<>();

        ComboBoxPanel.add(Dept);

        //입력 필드
        textField = new JTextField(15);
        ComboBoxPanel.add(textField);

        Attribute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) Attribute.getSelectedItem();
                updateOptionComboBoxModel(selectedCategory);
            }
        });

        //Chcek Box Panel
        JPanel CheckBoxPanel = new JPanel();
        CheckBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        CheckBoxPanel.add(name);
        CheckBoxPanel.add(ssn);
        CheckBoxPanel.add(bdate);
        CheckBoxPanel.add(address);
        CheckBoxPanel.add(sex);
        CheckBoxPanel.add(salary);
        CheckBoxPanel.add(supervisor);
        CheckBoxPanel.add(department);
        CheckBoxPanel.add(Search_Btn);

        JPanel ShowSelectedPanel = new JPanel();
        ShowSelectedPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        Emplabel.setFont(new Font("Dialog", Font.BOLD, 16));
        ShowSelectedPanel.add(ShowSelectedEmp);



        // Update Panel
        JPanel UpdatePanel = new JPanel();
        UpdatePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        UpdatePanel.add(Setlabel);
        UpdatePanel.add(SetSalary);
        UpdatePanel.add(Update_Btn);


        //Delete Panel
        JPanel DeletePanel = new JPanel();
        DeletePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        DeletePanel.add(Delete_Btn);

        //  Insert Panel
        JPanel InsertPanel = new JPanel();
        InsertPanel.setLayout(new GridLayout(9,2));
        InsertPanel.add(new JLabel("이름"));
        InsertPanel.add(InsertName);
        InsertPanel.add(new JLabel("주민번호"));
        InsertPanel.add(InsertSsn);
        InsertPanel.add(new JLabel("생일"));
        InsertPanel.add(InsertBdate);
        InsertPanel.add(new JLabel("주소"));
        InsertPanel.add(InsertAddress);
        InsertPanel.add(new JLabel("성별"));
        InsertPanel.add(InsertSex);
        InsertPanel.add(new JLabel("연봉"));
        InsertPanel.add(InsertSalary);
        InsertPanel.add(new JLabel("상사"));
        InsertPanel.add(InsertSuper);
        InsertPanel.add(new JLabel("부서"));
        InsertPanel.add(InsertDepartment);
        InsertPanel.add(new JLabel("입력"));
        InsertPanel.add(Insert_Btn);


        JPanel Top = new JPanel();
        Top.setLayout(new BoxLayout(Top, BoxLayout.Y_AXIS));
        Top.add(ComboBoxPanel);
        Top.add(CheckBoxPanel);



        JPanel Halfway = new JPanel();
        Halfway.setLayout(new BoxLayout(Halfway, BoxLayout.X_AXIS));
        Halfway.add(ShowSelectedEmp);


        JPanel Bottom = new JPanel();
        Bottom.setLayout(new GridLayout());
        Bottom.add(InsertPanel);


        JPanel Center = new JPanel();
        Center.setLayout(new BoxLayout(Center, BoxLayout.X_AXIS));
        Center.add(UpdatePanel);
        Center.add(DeletePanel);


        JPanel ShowVertical = new JPanel();
        ShowVertical.setLayout(new BoxLayout(ShowVertical, BoxLayout.Y_AXIS));
        ShowVertical.add(Halfway);
        ShowVertical.add(Center);
        ShowVertical.add(horizontalSeparator);
        ShowVertical.add(Bottom);



        add(Top, BorderLayout.NORTH);
        add(ShowVertical, BorderLayout.SOUTH);


        Search_Btn.addActionListener(this);
        Delete_Btn.addActionListener(this);
        Update_Btn.addActionListener(this);
        Insert_Btn.addActionListener(this);


        setSize(1200, 800);
        setTitle("Information Retrival System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }


    // JComboBox이용해서 Toggle 버튼 > 카테고리에 따라 다른 선택
    private void updateOptionComboBoxModel(String selectedCategory){
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        if(selectedCategory.equals("성별")){
            for(String option : SexOption){
                model.addElement(option);
            }
            textField.setVisible(false);
        }else if(selectedCategory.equals("부서")){
            for(String option: DepartMentOption){
                model.addElement(option);
            }
            textField.setVisible(false);
        }else if(selectedCategory.equals("연봉")){
            for(String option : SalaryOption){
                model.addElement(option);
                textField.setVisible(true);
            }
        } else{
            textField.setVisible(true);
        }
        Dept.setModel(model);
    }
    //------------------------------


    // JDBC연결
    public void actionPerformed(ActionEvent e) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 도라이버 연결
            String user = "root";
            String pwd = "SQLkelly2237!";
            String dbname = "company";
            String url = "jdbc:mysql://localhost:3306/" + dbname + "?serverTimezone=UTC";

            connection = DriverManager.getConnection(url, user, pwd);
            System.out.println(" DB연결 완료 ( 검색 버튼 클릭 ) ");
        } catch (ClassNotFoundException ex) {
            System.err.println("드라이버를 로드할 수 없습니다.");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.err.println("연결할 수 없습니다.");
            ex.printStackTrace();
        }

//        ------------------
        if (count == 1) {
            me.remove(panel);
            revalidate();
        }


        // 검색 기능 
        if (e.getSource() == Search_Btn) {
            if (name.isSelected() || ssn.isSelected() || bdate.isSelected() || address.isSelected() || sex.isSelected() || salary.isSelected() || supervisor.isSelected() || department.isSelected()) {
                Head.clear();
                Head.add("선택");

                String queryMsg = "select";
                if (name.isSelected()) {
                    queryMsg += " concat(e.fname,' ',e.minit,' ',e.lname, ' ') as Name";
                    Head.add("NAME");
                }
                if (ssn.isSelected()) {
                    if (!name.isSelected())
                        queryMsg += " e.ssn";
                    else
                        queryMsg += ", e.ssn";
                    Head.add("SSN");
                }
                if (bdate.isSelected()) {
                    if (!name.isSelected() && !ssn.isSelected())
                        queryMsg += " e.bdate";
                    else
                        queryMsg += ", e.bdate";
                    Head.add("BDATE");
                }
                if (address.isSelected()) {
                    if (!name.isSelected() && !ssn.isSelected() && !bdate.isSelected())
                        queryMsg += " e.address";
                    else
                        queryMsg += ", e.address";
                    Head.add("ADDRESS");
                }
                if (sex.isSelected()) {
                    if (!name.isSelected() && ssn.isSelected() && !bdate.isSelected() && !address.isSelected())
                        queryMsg += " e.sex";
                    else
                        queryMsg += ", e.sex";
                    Head.add("SEX");
                }
                if (salary.isSelected()) {
                    if (!name.isSelected() && ssn.isSelected() && !bdate.isSelected() && !address.isSelected() && !sex.isSelected())
                        queryMsg += " e.salary";
                    else
                        queryMsg += ", e.salary";
                    Head.add("SALARY");
                }
                if (supervisor.isSelected()) {
                    if (!name.isSelected() && ssn.isSelected() && !bdate.isSelected() && !address.isSelected() && !sex.isSelected() && !salary.isSelected())
                        queryMsg += " concat(s.fname,' ', s.minit, ' ', s.lname, ' ') as Supervisor";
                    else
                        queryMsg += ", concat(s.fname,' ', s.minit, ' ', s.lname, ' ') as Supervisor";
                    Head.add("SUPERVISOR");

                }
                if (department.isSelected()) {
                    if (!name.isSelected() && ssn.isSelected() && !bdate.isSelected() && !address.isSelected() && !sex.isSelected() && !salary.isSelected() && !department.isSelected())
                        queryMsg += " dname";
                    else
                        queryMsg += ", dname";
                    Head.add("DEPARTMENT");
                }

                queryMsg += " from employee e left outer join employee s on e.super_ssn=s.ssn, department where e.dno = dnumber";


                // 토글버튼 Category 클릭 시,
                if (Attribute.getSelectedItem().toString() == "성별") {
                    if (Dept.getSelectedItem().toString() == "F")
                        queryMsg += " and e.sex = \"F\";";
                    else if (Dept.getSelectedItem().toString() == "M")
                        queryMsg += " and e.sex = \"M\";";
                }else if(Attribute.getSelectedItem().toString() == "부서"){
                    if (Dept.getSelectedItem().toString() == "Headquarters")
                        queryMsg += " and dname = \"Headquarters\";";
                    else if (Dept.getSelectedItem().toString() == "Research")
                        queryMsg += " and dname = \"Research\";";
                    else if (Dept.getSelectedItem().toString() == "Administration")
                        queryMsg += " and dname = \"Administration\";";
                }else if(Attribute.getSelectedItem().toString() == "연봉"){
                    String inputValue = textField.getText();
                    if(Dept.getSelectedItem().toString() == ">="){
                        queryMsg += " and e.salary >= \""+inputValue+"\";";
                    }
                    else if (Dept.getSelectedItem().toString() == ">"){
                        queryMsg += " and e.salary > \""+inputValue+"\";";
                    }
                    else if(Dept.getSelectedItem().toString() == "="){
                        queryMsg += " and e.salary = \""+inputValue+"\";";
                    }
                    else if(Dept.getSelectedItem().toString() == "<"){
                        queryMsg += " and e.salary < \""+inputValue+"\";";
                    }
                    else if(Dept.getSelectedItem().toString() == "<="){
                        queryMsg += " and e.salary <= \""+inputValue+"\";";
                    }
                }
                // 검색어 -> 일부만 검색해도, 포함되는 row가 출력될 수 있도록
                else if(Attribute.getSelectedItem().toString() == "이름"){
                    String inputValue = textField.getText();
                    queryMsg += " and concat(e.fname,' ',e.minit,' ',e.lname) LIKE '%" + inputValue +"%'";
                }
                else if(Attribute.getSelectedItem().toString() == "주민번호"){
                    String inputValue = textField.getText();
                    queryMsg += " and e.ssn LIKE '%" + inputValue +"%'";
                }
                else if(Attribute.getSelectedItem().toString() == "생일"){
                    String inputValue = textField.getText();
                    queryMsg += " and e.bdate LIKE '%" + inputValue +"%'";
                }
                else if(Attribute.getSelectedItem().toString() == "주소"){
                    String inputValue = textField.getText();
                    queryMsg += " and e.address LIKE '%" + inputValue +"%'";
                }
                else if(Attribute.getSelectedItem().toString() == "상사 이름"){
                    String inputValue = textField.getText();
                    queryMsg += " and concat(s.fname,' ',s.minit,' ',s.lname) LIKE '%" + inputValue +"%'";
                }




                model = new DefaultTableModel(Head, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        if (column > 0) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                };

                for (int i = 0; i < Head.size(); i++) {
                    if (Head.get(i) == "NAME") {
                        NAME_COLUMN = i;
                    } else if (Head.get(i) == "SALARY") {
                        SALARY_COLUMN = i;
                    }
                }

                table = new JTable(model) {
                    @Override
                    public Class getColumnClass(int column) {
                        if (column == 0) {
                            return Boolean.class;
                        } else
                            return String.class;
                    }
                };

                ShowSelectedEmp.setText(" ");


                try {
                    count = 1;
                    state = connection.createStatement();
                    result = state.executeQuery(queryMsg);
                    ResultSetMetaData resultSetMetaData = result.getMetaData();
                    int columnCnt = resultSetMetaData.getColumnCount();
                    int rowCnt = table.getRowCount();

                    while (result.next()) {
                        Vector<Object> tuple = new Vector<Object>();
                        tuple.add(false);
                        for (int i = 1; i < columnCnt + 1; i++) {
                            tuple.add(result.getString(resultSetMetaData.getColumnName(i)));
                        }
                        model.addRow(tuple);
                        rowCnt++;
                    }
                    totalCount.setText(String.valueOf(rowCnt));

                } catch (SQLException ee) {
                    System.out.println("actionPerformed err : " + ee);
                    ee.printStackTrace();
                }
                panel = new JPanel();
                ScPane = new JScrollPane(table);
                //table.getModel().addTableModelListener(new CheckBoxModelListner());
                ScPane.setPreferredSize(new Dimension(1100, 400));
                panel.add(ScPane);
                add(panel, BorderLayout.CENTER);
                revalidate();

            } else {
                JOptionPane.showConfirmDialog(null, "검색 항목을 한 개 이상 검색하세요.");
            }

        }
    }
    // ---------- 검색 끝 ---------




    // ---------- 삭제 시작 ---------------
    // ---------- 삭제 끝 ---------------


    // ---------- 업데이트 시작 -------------
    // ---------- 업데이트 끝 --------------


    public static void main(String[] args){
        new Employee();
    }


}