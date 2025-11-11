package studentrecord;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

// ===== Student Class =====
class Student {
    int rollNo;
    String name;
    String program;
    double cgpa;

    Student(int rollNo, String name, String program, double cgpa) {
        this.rollNo = rollNo;
        this.name = name;
        this.program = program;
        this.cgpa = cgpa;
    }
}

// ===== Stack Implementation =====
class StudentStack {
    private final int MAX = 50;
    private int top = -1;
    private Student[] stack = new Student[MAX];

    boolean isEmpty() { return top == -1; }
    boolean isFull() { return top == MAX - 1; }

    void push(Student s) {
        if (isFull()) throw new RuntimeException("Stack Overflow! Maximum limit reached.");
        stack[++top] = s;
    }

    Student pop() {
        if (isEmpty()) throw new RuntimeException("Stack Underflow! No student to remove.");
        return stack[top--];
    }

    Student peek() {
        if (isEmpty()) throw new RuntimeException("No student found in stack.");
        return stack[top];
    }

    Student[] getAll() {
        Student[] data = new Student[top + 1];
        System.arraycopy(stack, 0, data, 0, top + 1);
        return data;
    }

    void clear() {
        top = -1;
    }
}

// ===== GUI Class =====
public class StudentRecordSystemDark extends JFrame {
    private JTextField txtRollNo, txtName, txtProgram, txtCgpa;
    private JLabel lblStatus;
    private JTable table;
    private DefaultTableModel model;
    private StudentStack stack = new StudentStack();

    public StudentRecordSystemDark() {
        // === Frame Setup ===
        setTitle("Student Record Management System (Stack) — Mehran University");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // === Colors ===
        Color bgColor = new Color(25, 25, 25);
        Color panelColor = new Color(45, 45, 45);
        Color textColor = new Color(220, 220, 220);
        Color accent = new Color(0, 153, 255);

        // === Header ===
        JLabel header = new JLabel("Student Record Management System (Using Stack)", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(accent);
        header.setOpaque(true);
        header.setBackground(bgColor);
        header.setBorder(BorderFactory.createEmptyBorder(1 , 1 , 1 , 1));

        // === Input Panel ===
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(accent),
                "Add Student",
                0, 0, new Font("Segoe UI", Font.BOLD, 15), accent));
        inputPanel.setPreferredSize(new Dimension(300, 280));

        inputPanel.setBackground(panelColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 10, 1, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblRoll = createLabel("Roll No:", textColor);
        JLabel lblName = createLabel("Name:", textColor);
        JLabel lblProgram = createLabel("Program:", textColor);
        JLabel lblCgpa = createLabel("CGPA:", textColor);

        txtRollNo = createTextField(panelColor, textColor);
        txtName = createTextField(panelColor, textColor);
        txtProgram = createTextField(panelColor, textColor);
        txtCgpa = createTextField(panelColor, textColor);

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(lblRoll, gbc);
        gbc.gridx = 1; inputPanel.add(txtRollNo, gbc);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(lblName, gbc);
        gbc.gridx = 1; inputPanel.add(txtName, gbc);
        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(lblProgram, gbc);
        gbc.gridx = 1; inputPanel.add(txtProgram, gbc);
        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(lblCgpa, gbc);
        gbc.gridx = 1; inputPanel.add(txtCgpa, gbc);

        lblStatus = new JLabel(" ");
        lblStatus.setForeground(accent);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        inputPanel.add(lblStatus, gbc);

        // === Button Panel ===
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(bgColor);

        JButton btnAdd = createButton("Add (Push)", accent);
        JButton btnRemove = createButton("Remove (Pop)", accent);
        JButton btnViewTop = createButton("View Top", accent);
        JButton btnDisplayAll = createButton("Display All", accent);
        JButton btnClear = createButton("Clear Stack", accent);

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnViewTop);
        buttonPanel.add(btnDisplayAll);
        buttonPanel.add(btnClear);

        // === Table ===
        model = new DefaultTableModel(new String[]{"Roll No", "Name", "Program", "CGPA"}, 0);
        table = new JTable(model);
        table.setBackground(panelColor);
        table.setForeground(textColor);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(26);
        table.getTableHeader().setBackground(accent);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(bgColor);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(accent),
                "Student Stack Records",
                0, 0, new Font("Segoe UI", Font.BOLD, 14), accent));

        // === Layout ===
        add(header, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // === Actions ===
        btnAdd.addActionListener(this::addStudent);
        btnRemove.addActionListener(this::removeStudent);
        btnViewTop.addActionListener(this::viewTopStudent);
        btnDisplayAll.addActionListener(this::displayAllStudents);
        btnClear.addActionListener(this::clearStack);
    }

    // === Helper Methods ===
    private JLabel createLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        return label;
    }

    private JTextField createTextField(Color bg, Color fg) {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(220, 28));
        field.setEditable(true);
        field.setEnabled(true);
        field.setBackground(bg);
        field.setForeground(fg);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80)),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));
        return field;
    }

    private JButton createButton(String text, Color accent) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(accent);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return btn;
    }

    // === Button Functions ===
    private void addStudent(ActionEvent e) {
        try {
            int roll = Integer.parseInt(txtRollNo.getText());
            String name = txtName.getText().trim();
            String program = txtProgram.getText().trim();
            double cgpa = Double.parseDouble(txtCgpa.getText());

            if (name.isEmpty() || program.isEmpty()) {
                lblStatus.setText("Please fill all fields!");
                return;
            }

            stack.push(new Student(roll, name, program, cgpa));
            lblStatus.setText("✅ Student added successfully!");
            clearInputs();
            displayAllStudents(null);

        } catch (NumberFormatException ex) {
            lblStatus.setText("❌ Invalid input! Check Roll No or CGPA.");
        } catch (RuntimeException ex) {
            lblStatus.setText("⚠ " + ex.getMessage());
        }
    }

    private void removeStudent(ActionEvent e) {
        try {
            Student removed = stack.pop();
            lblStatus.setText("Removed: " + removed.name);
            displayAllStudents(null);
        } catch (RuntimeException ex) {
            lblStatus.setText(ex.getMessage());
        }
    }

    private void viewTopStudent(ActionEvent e) {
        try {
            Student top = stack.peek();
            lblStatus.setText("Top Student → " + top.name + " (CGPA: " + top.cgpa + ")");
        } catch (RuntimeException ex) {
            lblStatus.setText(ex.getMessage());
        }
    }

    private void displayAllStudents(ActionEvent e) {
        model.setRowCount(0);
        Student[] data = stack.getAll();
        for (int i = data.length - 1; i >= 0; i--) {
            model.addRow(new Object[]{data[i].rollNo, data[i].name, data[i].program, data[i].cgpa});
        }
    }

    private void clearStack(ActionEvent e) {
        stack.clear();
        model.setRowCount(0);
        lblStatus.setText("Stack cleared successfully!");
    }

    private void clearInputs() {
        txtRollNo.setText("");
        txtName.setText("");
        txtProgram.setText("");
        txtCgpa.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentRecordSystemDark().setVisible(true));
    }
}
