package presentation;

import business.Utilities;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import entities.Employee;
import entities.Task;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MainWindow extends JFrame {

    private Utilities handler;

    private JScrollPane scrollPane1;
    private JPanel panel1;
    private JButton addEmployeeButton;
    private JButton addTaskButton;
    private JButton assignTaskToEmployeeButton;
    private JButton modifyTaskStatusButton;
    private JButton employeeRankingButton;
    private JButton employeeStatusButton;
    private JButton deleteEmployeeButton;
    private JButton deleteTaskButton;

    private JTabbedPane tabbedPane1;
    private JScrollPane assignedTasksJScrollPane;
    private JScrollPane employeesJScrollPane;
    private JTable employeeTable;

    private JScrollPane tasksJScrollPane;
    private JTable tasksTable;
    private JTable assignedTasksTable;


    public MainWindow() {
        setTitle("Employee Task Manager");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setup();
        setVisible(true);
    }

    private void setup() {
        /// Handler connection to lower layers
        this.handler = new Utilities();
        /// Setting up the tab panels
        loadAssignedTasks();
        loadTasks();
        loadAssignedTasks();
        loadEmployeeData();
        /// Exit operation setup
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                handler.save();
                dispose();
            }
        });
        /// Setup Tab options
        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                int index = sourceTabbedPane.getSelectedIndex();

                switch (sourceTabbedPane.getTitleAt(index)) {
                    case "Assigned Tasks":
                        loadAssignedTasks();
                        break;
                    case "Employees":
                        loadEmployeeData();
                        break;
                    case "Tasks":
                        loadTasks();
                        break;
                    default:
                        break;
                }
            }
        });
        /// Button actions
        addEmployeeButton.addActionListener(e -> {
            new AddEmployeeDialog(handler).setVisible(true);
            loadEmployeeData();
        });
        deleteEmployeeButton.addActionListener(e -> {
            new RemoveEmployeeDialog(handler).setVisible(true);
            loadEmployeeData();
        });
        addTaskButton.addActionListener(e -> {
            new SelectTaskTypeDialog(handler).setVisible(true);
            loadTasks();
        });
        deleteTaskButton.addActionListener(e -> {
            new RemoveTaskDialog(handler).setVisible(true);
            loadTasks();
        });
        assignTaskToEmployeeButton.addActionListener(e -> {
            new SelectEmployeeToRecieveTasksDialog(handler).setVisible(true);
            loadAssignedTasks();
        });
    }

    private void loadTasks() {
        tasksTable.setModel(new DefaultTableModel());
        DefaultTableModel model = (DefaultTableModel) tasksTable.getModel();
        model.addColumn("UUID");
        model.addColumn("Status");
        model.addColumn("Estimated Duration");
        model.addColumn("Visual Representation");
        List<Task> list = handler.getTasks();
        for (Task t : list) {
            model.addRow(new Object[]{
                    t.getIdTask(),
                    t.getStatusTask(),
                    t.estimateDuration(),
                    t.toString()
            });
        }
    }

    private void loadEmployeeData() {
        /// employeeTable
        employeeTable.setModel(new DefaultTableModel());
        DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
        model.addColumn("UUID");
        model.addColumn("Name");
        List<Employee> list = handler.getEmployees();
        for (Employee e : list) {
            model.addRow(new Object[]{
                    e.getIdEmployee(),
                    e.getName()
            });
        }
    }

    private void loadAssignedTasks() {
        assignedTasksTable.setModel(new DefaultTableModel());
        DefaultTableModel model = (DefaultTableModel) assignedTasksTable.getModel();
        model.addColumn("Employee UUID");
        model.addColumn("Employee name");
        model.addColumn("Assigned Tasks");
        List<Employee> list = handler.getEmployees();
        for(Employee e : list){
            model.addRow(new Object[]{
                    e.getIdEmployee(),
                    e.getName(),
                    handler.getAssignedTaskListString(e)
            });
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1 = new JTabbedPane();
        panel1.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(960, 540), new Dimension(960, 540), null, 0, false));
        assignedTasksJScrollPane = new JScrollPane();
        tabbedPane1.addTab("Assigned Tasks", assignedTasksJScrollPane);
        assignedTasksTable = new JTable();
        assignedTasksJScrollPane.setViewportView(assignedTasksTable);
        employeesJScrollPane = new JScrollPane();
        tabbedPane1.addTab("Employees", employeesJScrollPane);
        employeeTable = new JTable();
        employeeTable.setEnabled(false);
        employeesJScrollPane.setViewportView(employeeTable);
        tasksJScrollPane = new JScrollPane();
        tabbedPane1.addTab("Tasks", tasksJScrollPane);
        tasksTable = new JTable();
        tasksJScrollPane.setViewportView(tasksTable);
        scrollPane1 = new JScrollPane();
        scrollPane1.setHorizontalScrollBarPolicy(31);
        panel1.add(scrollPane1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(200, -1), new Dimension(200, -1), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(8, 2, new Insets(20, 0, 20, 0), -1, -1));
        scrollPane1.setViewportView(panel2);
        addEmployeeButton = new JButton();
        addEmployeeButton.setText("Add Employee");
        panel2.add(addEmployeeButton, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addTaskButton = new JButton();
        addTaskButton.setText("Add Task");
        panel2.add(addTaskButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        assignTaskToEmployeeButton = new JButton();
        assignTaskToEmployeeButton.setText("Assign Task to Employee");
        panel2.add(assignTaskToEmployeeButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modifyTaskStatusButton = new JButton();
        modifyTaskStatusButton.setText("Modify Assigned Task Status");
        panel2.add(modifyTaskStatusButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        employeeRankingButton = new JButton();
        employeeRankingButton.setText("Display employee ranking");
        panel2.add(employeeRankingButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        employeeStatusButton = new JButton();
        employeeStatusButton.setText("Display Employee Status");
        panel2.add(employeeStatusButton, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteEmployeeButton = new JButton();
        deleteEmployeeButton.setText("Remove Employee");
        panel2.add(deleteEmployeeButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteTaskButton = new JButton();
        deleteTaskButton.setText("Remove Task");
        panel2.add(deleteTaskButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
