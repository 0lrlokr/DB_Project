package kau;

import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


// UI 작성
public class Company extends JFrame implements ActionListener{
    public Connection conn;
    public Statement state;
    public ResultSet result;

    private JComboBox Category;
    private JComboBox Dept;

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
    private JTextField setSalary = new JTextField(10);
    private JButton Update_Button = new JButton("UPDATE");
    private JButton Delete_Button = new JButton("선택한 데이터 삭제");
    int count = 0 ;



    public Company(){
        JPanel ComboBoxPanel = new JPanel();
        String[] category = {"전체","이름", "성별", "주소", "연봉", "상사", "부서별"};
        String[] dept = {"Reseatch", "Administration", "Headquarters"};
        Category = new JComboBox(category);
        Dept = new JComboBox(dept);

        ComboBoxPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ComboBoxPanel.add(new JLabel("검색 범위"));
        ComboBoxPanel.add(Category);
        ComboBoxPanel.add(Dept);


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
        //Emplabel.setFont(new Font("Dialog", Font.BOLD, 16));
        ShowSelectedPanel.add(ShowSelectedEmp);



        JPanel UpdatePanel = new JPanel();
        UpdatePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        UpdatePanel.add(Setlabel);
        UpdatePanel.add(setSalary);
        UpdatePanel.add(Update_Button);

        JPanel DeletePanel = new JPanel();
        DeletePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        DeletePanel.add(Delete_Button);

        JPanel Top = new JPanel();
        Top.setLayout(new BoxLayout(Top, BoxLayout.Y_AXIS));
        Top.add(ComboBoxPanel);
        Top.add(CheckBoxPanel);


        JPanel Halfway = new JPanel();
        Halfway.setLayout(new BoxLayout(Halfway, BoxLayout.X_AXIS));
        Halfway.add(ShowSelectedEmp);

        JPanel Bottom = new JPanel();
        Bottom.setLayout(new BoxLayout(Bottom, BoxLayout.X_AXIS));
        Bottom.add(UpdatePanel);
        Bottom.add(DeletePanel);

        JPanel ShowVertical = new JPanel();
        ShowVertical.setLayout(new BoxLayout(ShowVertical, BoxLayout.Y_AXIS));
        ShowVertical.add(Halfway);
        ShowVertical.add(Bottom);


        add(Top, BorderLayout.NORTH);
        add(ShowVertical, BorderLayout.SOUTH);

        Search_Button.addActionListener(this);
        Delete_Button.addActionListener(this);
        Update_Button.addActionListener(this);

        setTitle("Information Retrival System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 600);
        setLocationRelativeTo(null);
        setVisible(true);

    }


    // Query문 작성
    public void actionPerformed(ActionEvent e){

    }

}