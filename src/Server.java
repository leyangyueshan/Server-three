import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame {
	private JTextField jtf = new JTextField();
	private JTextArea jta = new JTextArea();
	// private DataOutputStream toClient;
	// private DataInputStream fromClient;
	private String c1 = null, c2 = null;
	private DataInputStream fromClient1;
	private DataOutputStream toClient1;
	private DataInputStream fromClient2;
	private DataOutputStream toClient2;

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		// setLayout(new BorderLayout());
		// add(new JScrollPane(jta), BorderLayout.CENTER);
		// setTitle("Server");
		// setSize(500, 300);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setVisible(true);
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel("Enter ServerText:"), BorderLayout.WEST);
		p.add(jtf, BorderLayout.CENTER);
		jtf.setHorizontalAlignment(JTextField.RIGHT);
		setLayout(new BorderLayout());
		add(p, BorderLayout.NORTH);
		add(new JScrollPane(jta), BorderLayout.CENTER);
		// jtf.addActionListener(new TextFieldListener());
		setTitle("Srever");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		try {
			ServerSocket serverSocket = new ServerSocket(8000);
			ServerSocket serverSocket1 = new ServerSocket(8001);
			jta.append("Server started at" + new Date() + "\n");
			Socket client1 = serverSocket.accept();
			jta.append("client1's ip address:"
					+ client1.getInetAddress().getHostAddress() + "\n");
			Socket client2 = serverSocket1.accept();
			jta.append("client2's ip address:"
					+ client2.getInetAddress().getHostAddress() + "\n");
			HandleASession task = new HandleASession(client1, client2);
			new Thread(task).start();
			// Socket socket = serverSocket.accept();
			// fromClient = new DataInputStream(socket.getInputStream());
			// toClient = new DataOutputStream(socket.getOutputStream());
			// fromClient1=new DataInputStream(client1.getInputStream());
			// toClient1 = new DataOutputStream(client1.getOutputStream());
			// fromClient2=new DataInputStream(client2.getInputStream());
			// toClient2= new DataOutputStream(client2.getOutputStream());

			// while (true) {
			// c1 =fromClient1.readUTF();
			// if(!c1.isEmpty()){
			// jta.append("client1 says:"+c1+ "\n");
			// toClient2.flush();
			// toClient2.writeUTF(c1);
			// }
			// c2 =fromClient2.readUTF();
			// if(!c2.isEmpty()){
			// jta.append( "client1 says:"+c2 + "\n");
			// toClient1.flush();
			// toClient1.writeUTF(c2);
			// }
			// }

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	class HandleASession implements Runnable {
		private Socket client1;
		private Socket client2;

		public HandleASession(Socket client1, Socket client2) {
			this.client1 = client1;
			this.client2 = client2;
		}
		@Override
		public void run() {
			try {
				fromClient1 = new DataInputStream(client1.getInputStream());
				toClient1 = new DataOutputStream(client1.getOutputStream());
				fromClient2 = new DataInputStream(client2.getInputStream());
				toClient2 = new DataOutputStream(client2.getOutputStream());
				while (true) {
					c1 = fromClient1.readUTF();
					if (!c1.isEmpty()) {
						jta.append("client1 says:" + c1 + "\n");
						toClient2.flush();
						toClient2.writeUTF(c1);
					}
					c2 = fromClient2.readUTF();
					if (!c2.isEmpty()) {
						jta.append("client1 says:" + c2 + "\n");
						toClient1.flush();
						toClient1.writeUTF(c2);
					}
				}
			} catch (IOException ex) {
				System.out.println(ex);

			}

		}

		// private class TextFieldListener implements ActionListener {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// String a = jtf.getText().trim();
		// //double radius =Double.parseDouble(jtf.getText().trim());
		//
		// try {
		// toClient1.flush();
		// toClient1.writeUTF(a);
		// //String area = fromClient.readUTF();
		// jta.append(a + "\n");
		// //jta.append(area+"\n");
		//
		// } catch (IOException e1) {
		// // TODO 自动生成的 catch 块
		// e1.printStackTrace();
		// }
		//
		// }
		//
		// }
	}
}