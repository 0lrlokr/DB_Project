package kau;

import javafx.scene.control.ComboBox;
import java.util.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


// UI 작성
public class Company extends JFrame implements ActionListener{

    // 1. 멤버변수 선언
    public Connection conn;
    public Statement state;
    public ResultSet result;

    JSeparator horizontalSeparator = new JSeparator(SwingConstants.HORIZONTAL);


    private JComboBox<String> Category;
    private JComboBox<String> Dept;
    // 이름, 주민번호, 생일, 주소, 연봉(기호는 select되게), 상사 검색
    private JTextField textField;
    private String[] SalaryOption = {">", "<","="};
    private String[] SexOption = {"전체", "F", "M"};
    private String[] DepartMentOption = {"D1","D2","D3"};


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

    private JButton Search_Button = new JButton("검색");
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




    public Company(){
        JPanel ComboBoxPanel = new JPanel();

        // 첫 번째 JComboBox(검색 전체 범위)
        String[] category = {"전체","이름","주민번호", "생일", "성별", "주소", "연봉", "상사 이름", "부서"};

        Category = new JComboBox<>(category);
        ComboBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ComboBoxPanel.add(new JLabel("검색 범위"));
        ComboBoxPanel.add(Category);


        //두 번째 JComboBox(선택지 -> 새로운 선택지)
        Dept = new JComboBox<>();

        ComboBoxPanel.add(Dept);

        //입력 필드
        textField = new JTextField(15);
        ComboBoxPanel.add(textField);

        Category.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) Category.getSelectedItem();
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
        CheckBoxPanel.add(Search_Button);

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

        //Insert Panel
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


        Search_Button.addActionListener(this);
        Delete_Btn.addActionListener(this);
        Update_Btn.addActionListener(this);
        Insert_Btn.addActionListener(this);


        setSize(1200, 800);
        setTitle("Information Retrival System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }


    // JComboBox이용해서 Toggle 버튼 > 카테고리에 따라 다른 선택지
    private void updateOptionComboBoxModel(String selectedCategory){
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        if(selectedCategory.equals("성별")){
            for(String option : SexOption){
                model.addElement(option);
            }
            textField.setVisible(false);
        }else if(selectedCategory.equals("부서별")){
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
    //-------------------


    // Query문 작성
    public void actionPerformed(ActionEvent e){


    }



}