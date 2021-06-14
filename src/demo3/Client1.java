package demo3;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class Client1 {
    private JFrame clientFrame;
    private JLabel IPLabel;
    private JLabel PortLabel;
    private JLabel sayLabel;
    private JLabel nicknameLabel;
    private JTextField IPText;
    private JTextField PortText;
    private JTextField nicknameText;
    private JTextField sayText;
    private JButton connectButton;
    private JButton nicknameButton;
    private JButton sayButton;
    private JPanel jPanelNorth;
    private JPanel jPanelSouth0;
    private JPanel jPanelSouth1;
    private JPanel jPanelSouth2;
    private JTextArea clientTextArea;
    private JScrollPane scroller;
    private BufferedReader reader;
    private PrintWriter writer;
    private String nickname;

    public static void main(String args[]) {
        Client aClient = new Client();
        aClient.startUp();
    }

    // ��ʼ�����
    public Client1() {
        nickname = "�ͻ���";

        clientFrame = new JFrame();
        jPanelNorth = new JPanel();
        IPLabel = new JLabel("������IP", JLabel.LEFT);
        IPText = new JTextField(10);
        PortLabel = new JLabel("�������˿�", JLabel.LEFT);
        PortText = new JTextField(10);
        connectButton = new JButton("����");
        clientTextArea = new JTextArea();
        scroller = new JScrollPane(clientTextArea);
        jPanelSouth0 = new JPanel();
        jPanelSouth1 = new JPanel();
        jPanelSouth2 = new JPanel();
        nicknameLabel = new JLabel("�ǳ�", JLabel.LEFT);
        nicknameText = new JTextField(nickname, 30);
        nicknameButton = new JButton("ȷ��");
        sayLabel = new JLabel("��Ϣ", JLabel.LEFT);
        sayText = new JTextField(30);
        sayButton = new JButton("����");
    }

    // ����GUI
    private void buildGUI() {
        // ���ڵ�����
        clientFrame.setTitle("�ͻ���");
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.setSize(550, 550);

        // ���������
        jPanelNorth.add(IPLabel);
        jPanelNorth.add(IPText);
        jPanelNorth.add(PortLabel);
        jPanelNorth.add(PortText);
        jPanelNorth.add(connectButton);
        clientFrame.getContentPane().add(BorderLayout.NORTH, jPanelNorth);

        // �м�����
        clientTextArea.setFocusable(false);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        clientFrame.getContentPane().add(BorderLayout.CENTER, scroller);

        // ���������
        jPanelSouth1.add(nicknameLabel);
        jPanelSouth1.add(nicknameText);
        jPanelSouth1.add(nicknameButton);
        jPanelSouth2.add(sayLabel);
        jPanelSouth2.add(sayText);
        jPanelSouth2.add(sayButton);
        jPanelSouth0.setLayout(new BoxLayout(jPanelSouth0, BoxLayout.Y_AXIS));
        jPanelSouth0.add(jPanelSouth1);
        jPanelSouth0.add(jPanelSouth2);
        clientFrame.getContentPane().add(BorderLayout.SOUTH, jPanelSouth0);

        // ���ô��ڿɼ�
        clientFrame.setVisible(true);
    }

    // �ͻ�������
    public void startUp() {
        buildGUI();

        // ���շ�������Ϣ���߳�
        Runnable incomingReader = new Runnable() {
            @Override
            public void run() {
                String message;
                try {
                    while ((message = reader.readLine()) != null) {
                        clientTextArea.append(message + "\n");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        // ����Connect��ť��ʵ�ַ�����������
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aServerIP = IPText.getText();
                String aServerPort = PortText.getText();

                if (aServerIP.equals("") || aServerPort.equals("")) {
                    JOptionPane.showMessageDialog(clientFrame, "������ ������ IP�Ͷ˿ڣ�");
                } else {
                    try {
                        @SuppressWarnings("resource")
                        Socket clientSocket = new Socket(aServerIP, Integer.parseInt(aServerPort));
                        InputStreamReader streamReader = new InputStreamReader(clientSocket.getInputStream());
                        reader = new BufferedReader(streamReader);
                        writer = new PrintWriter(clientSocket.getOutputStream());

                        clientTextArea.append("������������...\n");

                        Thread readerThread = new Thread(incomingReader);
                        readerThread.start();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(clientFrame, "���Ӳ��Ϸ�����!\n��ȷ�� IP �� �˿� ������ȷ��");
                    }
                }
            }
        });

        // ����nickname�������ǳ�
        ActionListener nicknameListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aText = nicknameText.getText();
                if (!aText.equals("")) {
                    nickname = aText;
                }
            }
        };
        nicknameButton.addActionListener(nicknameListener);
        nicknameText.addActionListener(nicknameListener);
        nicknameText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                String aText = nicknameText.getText();
                if (!aText.equals("")) {
                    nickname = aText;
                }
            }
        });

        // ������Ϣ��������
        ActionListener SayListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aText = sayText.getText();
                if (aText.equals("")) {
                    JOptionPane.showMessageDialog(clientFrame, "���ݲ���Ϊ�գ�");
                } else {
                    try {
                        writer.println(nickname + "��" + aText);
                        writer.flush();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    sayText.setText("");
                }
            }
        };
        sayButton.addActionListener(SayListener);
        sayText.addActionListener(SayListener);

    }

}

