package demo3;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

public class Server {
    private JFrame serverFrame;
    private JLabel portLabel;
    private JLabel sayLabel;
    private JLabel nicknameLabel;
    private JTextField portText;
    private JTextField sayText;
    private JTextField nicknameText;
    private JButton startButton;
    private JButton sayButton;
    private JButton nicknameButton;
    private JPanel jPanelNorth;
    private JPanel jPanelSouth0;
    private JPanel jPanelSouth1;
    private JPanel jPanelSouth2;
    private JScrollPane scroller;
    private JTextArea serverTextArea;
    private ArrayList<PrintWriter> clientOutputStreams;
    private String nickname;

    public static void main(String[] args) {
        Server aServer = new Server();
        aServer.startUp();
    }

    // ��ʼ�����
    public Server() {
        nickname = "������";

        serverFrame = new JFrame();
        jPanelNorth = new JPanel();
        portLabel = new JLabel("�˿�", JLabel.LEFT);
        portText = new JTextField(30);
        startButton = new JButton("��ʼ");
        serverTextArea = new JTextArea();
        scroller = new JScrollPane(serverTextArea);
        nicknameLabel = new JLabel("�ǳ�", JLabel.LEFT);
        nicknameText = new JTextField(nickname, 30);
        nicknameButton = new JButton("ȷ��");
        jPanelSouth0 = new JPanel();
        jPanelSouth1 = new JPanel();
        jPanelSouth2 = new JPanel();
        sayLabel = new JLabel("��Ϣ", JLabel.LEFT);
        sayText = new JTextField(30);
        sayButton = new JButton("����");
    }

    // ����GUI
    private void buildGUI() {
        // ���ڵ�����
        serverFrame.setTitle("������");
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverFrame.setSize(550, 550);

        // ���������
        jPanelNorth.add(portLabel);
        jPanelNorth.add(portText);
        jPanelNorth.add(startButton);
        serverFrame.getContentPane().add(BorderLayout.NORTH, jPanelNorth);

        // �м�����
        serverTextArea.setFocusable(false);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        serverFrame.getContentPane().add(BorderLayout.CENTER, scroller);

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
        serverFrame.getContentPane().add(BorderLayout.SOUTH, jPanelSouth0);

        // ���ô��ڿɼ�
        serverFrame.setVisible(true);
    }

    // ����������
    public void startUp() {
        buildGUI();

        // ����Start��ť�������˿�
        ActionListener startListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientOutputStreams = new ArrayList<PrintWriter>();
                String aPort = portText.getText();

                if (aPort.equals("")) {
                    JOptionPane.showMessageDialog(serverFrame, "��������ȷ�Ķ˿ںţ�");
                } else {
                    try {
                        // �ȴ��ͻ������ӵ��߳�
                        Runnable serverRunnable = new Runnable() {
                            @Override
                            public void run() {
                                ServerSocket serverSocket;
                                try {
                                    serverSocket = new ServerSocket(Integer.parseInt(aPort));
                                    serverTextArea.append("���ڵȴ��ͻ�������...\n");
                                    while (true) {
                                        Socket clientSocket = serverSocket.accept();
                                        serverTextArea.append("�ͻ���������...\n");

                                        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                                        clientOutputStreams.add(writer);

                                        Thread t = new Thread(new ClientHandler(clientSocket));
                                        t.start();
                                    }
                                } catch (NumberFormatException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        Thread serverThread = new Thread(serverRunnable);
                        serverThread.start();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        startButton.addActionListener(startListener);
        portText.addActionListener(startListener);

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

        // ����Say��ť��������Ϣ
        ActionListener SayListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aText = sayText.getText();
                if (!aText.equals("")) {
                    aText = nickname + "��" + aText;
                    sendToEveryClient(aText);
                    serverTextArea.append(aText + "\n");
                    sayText.setText("");
                } else {
                    JOptionPane.showMessageDialog(serverFrame, "���ݲ���Ϊ�գ�");
                }
            }
        };
        sayButton.addActionListener(SayListener);
        sayText.addActionListener(SayListener);
    }

    // ��ͻ��˵��߳�
    public class ClientHandler implements Runnable {
        BufferedReader bReader;
        Socket aSocket;

        public ClientHandler(Socket clientSocket) {
            try {
                aSocket = clientSocket;
                InputStreamReader isReader = new InputStreamReader(aSocket.getInputStream());
                bReader = new BufferedReader(isReader);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = bReader.readLine()) != null) {
                    sendToEveryClient(message);
                    serverTextArea.append(message + "\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // ������Ϣ�����пͻ��˵ķ���
    private void sendToEveryClient(String message) {
        Iterator<PrintWriter> it = clientOutputStreams.iterator();
        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}