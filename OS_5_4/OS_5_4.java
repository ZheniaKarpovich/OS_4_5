package OS_5_4;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;

public class OS_5_4 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	
	private CheckerIn checkerIn;
	private CheckerOut checkerOut;
	private Worker worker;
	
	private LinkedList<File> in, out, correctTasks, answers;
	private boolean checker1 = false, checker2 = false, stopFlag = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OS_5_4 frame = new OS_5_4();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OS_5_4() {
		
		this.in = new LinkedList<File>();
		this.out = new LinkedList<File>();
		this.correctTasks = new LinkedList<File>();
		this.answers = new LinkedList<File>();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 563);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 46, 336, 34);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(10, 147, 336, 34);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel = new JLabel("Open input file \"[fileName].in\"");
		lblNewLabel.setBounds(10, 11, 336, 24);
		contentPane.add(lblNewLabel);
		
		JLabel lblOpenOutputFile = new JLabel("Open output file \"[fileName].out\"");
		lblOpenOutputFile.setBounds(10, 112, 336, 24);
		contentPane.add(lblOpenOutputFile);
		
		JButton btnOpen = new JButton("OPEN");
		btnOpen.setBounds(356, 147, 68, 34);
		contentPane.add(btnOpen);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(10, 91, 336, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblS = new JLabel("");
		lblS.setBounds(10, 192, 336, 14);
		contentPane.add(lblS);
		
		JButton btnNewButton_1 = new JButton("START");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(10, 217, 169, 34);
		contentPane.add(btnNewButton_1);
		
		JButton btnStopCurrentThread = new JButton("STOP CURRENT THREAD");
		btnStopCurrentThread.setEnabled(false);
		btnStopCurrentThread.setBounds(255, 217, 169, 34);
		contentPane.add(btnStopCurrentThread);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 262, 414, 251);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JButton btnNewButton = new JButton("OPEN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mntmFileOpenActionPerformed(arg0);
			}
			private void mntmFileOpenActionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				checker1 = false;
				JFileChooser file = new JFileChooser();
				 file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				 file.setMultiSelectionEnabled(true);
				 if ( file.showOpenDialog(btnNewButton)== JFileChooser.APPROVE_OPTION) {
					 	File[] f = file.getSelectedFiles();
					 	for(File i: f) {
	 			            textField.setText(textField.getText() + " " + i.getAbsolutePath());
				            if(i.isDirectory()) {
				            	File[] filesInDir = i.listFiles();
				            	for (File j : filesInDir) {
				            		StringTokenizer st = new StringTokenizer(j.getName());
						            st.nextToken(".");
						            if(st.nextToken(".").equals("in")) {
						            	lblNewLabel_1.setText("");
						            	in.add(j);
						            	//занести в список
						            	//разблокировать кнопку мб)
						            }
				            	}
				            } else {
				            	StringTokenizer st = new StringTokenizer(i.getName());
				            	System.out.println(st.nextToken("."));
					            if(st.nextToken(".").equals("in")) {
					            	lblNewLabel_1.setText("");
					            	in.add(i);
					            	//занести в список
					            	//разблокировать кнопку мб)
					           }
				            }
					 }
					 if(in.isEmpty() /*kist is empty*/) {
					 	lblNewLabel_1.setText("Incorrect type in input file");
			            lblNewLabel_1.setForeground(new Color(255, 0 , 0));
					 } else {
						//разблокировать кнопку мб)
						 checker1 = true;
						 if(checker1 && checker2) {
							 btnNewButton_1.setEnabled(true);
							 stopFlag = false;
						 }
					 }
			     }
			}
		});
		btnNewButton.setBounds(356, 46, 68, 34);
		contentPane.add(btnNewButton);
		
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mntmFileOpenActionPerformed(arg0);
			}
			private void mntmFileOpenActionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("!!");
				 checker2 = false;
				 JFileChooser file = new JFileChooser();
				 file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				 file.setMultiSelectionEnabled(true);
				 if ( file.showOpenDialog(btnNewButton)== JFileChooser.APPROVE_OPTION) {
					 	File[] f = file.getSelectedFiles();
					 	for(File i: f) {
					 		textField_1.setText(textField_1.getText() + " " + i.getAbsolutePath());
				            if(i.isDirectory()) {
				            	File[] filesInDir = i.listFiles();
				            	for (File j : filesInDir) {
				            		StringTokenizer st = new StringTokenizer(j.getName());
						            st.nextToken(".");
						            if(st.nextToken(".").equals("out")) {
						            	lblNewLabel_1.setText("");
						            	out.add(j);
						            	//занести в список
						            	//разблокировать кнопку мб)
						            }
				            	}
				            } else {
				            	StringTokenizer st = new StringTokenizer(i.getName());
					            st.nextToken(".");
					            if(st.nextToken(".").equals("out")) {
					            	lblS.setText("");
					            	out.add(i);
					            	//занести в список
					            	//разблокировать кнопку мб)
					           }
				            }
					 }
					 if(out.isEmpty() /*kist is empty*/) {
						 lblS.setText("Incorrect type in output file");
						 lblS.setForeground(new Color(255, 0 , 0));
					 } else {
						//разблокировать кнопку мб)
						 checker2 = true;						 
						 if(checker1 && checker2) {
							 btnNewButton_1.setEnabled(true);
							 stopFlag = false;
						 }
					 }
			     }
			}
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
			
			private void tasksChecker() {
				for(File i: in) {
					StringTokenizer st = new StringTokenizer(i.getName());
		            String str = st.nextToken(".");
		            boolean flag = false;
					for(File j: out) {
						st = new StringTokenizer(j.getName());
			            String tmp = st.nextToken(".");  
			            if(str.equals(tmp)) {
			            	flag = true;
			            	break;
			            }
					}
					if(!flag) {
						in.remove(i);
						textPane.setText(textPane.getText() + i.getName() + "doesn`t have appropriate output file\n");
					}
				}
			}
			
			private File getAbsoluteFile(String name) {
				File result = null;
				for(int i = 0; i < out.size(); i++) {
					File tmp = out.get(i);
					if(tmp.getName().equals(name))
						result = tmp;
				}
				return result;
			}
			
			public void actionPerformed(ActionEvent e) {
				btnStopCurrentThread.setEnabled(true);
				btnStopCurrentThread.setForeground(new Color(255, 0, 0));				
				if(!stopFlag)
					this.tasksChecker();
				else if(!in.isEmpty()) {
					in.removeAll(in); //?????????????
					if(!correctTasks.isEmpty())
						correctTasks.removeAll(correctTasks); 
					if(!out.isEmpty())
						out.removeAll(out); 
					if(!answers.isEmpty())
						answers.removeAll(answers);
					textField_1.setText("");
					textField.setText("");
					btnNewButton_1.setEnabled(false);
					btnStopCurrentThread.setEnabled(false);
				}
				stopFlag = false;
				
				checkerOut = new CheckerOut();
				checkerOut.setAnswers(answers);
				checkerOut.setOutput(out);
				checkerOut.setTextPane(textPane);
				
				worker = new Worker();
				worker.setCorrectTasks(correctTasks);
				worker.setAnswers(answers);
				worker.setCheckerOut(checkerOut);
				
				checkerIn = new CheckerIn();
				checkerIn.setInput(in);
				checkerIn.setCorrectTasks(correctTasks);
				checkerIn.setWorker(worker);
				
				checkerIn.start();
				
				worker.start();
				
				checkerOut.start();
				
		//		boolean flag1 = true, flag2 = true, flag3 = true, flag4 = true;
				// запуск потоков
				// сделать булевы переменные которыые будут все это заканчивать
			/*	while(flag1 || flag2 || flag3) {	
					if(!in.isEmpty()) {
						if(!checkerIn.isAlive()) {
							File tmp = in.removeFirst();
							checkerIn.setCheckFile(tmp);
							checkerIn.start();
							try {
								checkerIn.join();
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
							boolean flag = checkerIn.getAnswer();
							if(flag) {
								synchronized(correctTasks) {
									correctTasks.add(tmp);
								}
							}
						}
					} else {
						flag1 = false;
					}
			//		if(true) { //переработать
						synchronized(correctTasks) {
							if(!correctTasks.isEmpty()) {
								if(!worker.isAlive()) {
									File tmp = correctTasks.removeFirst();
									worker.setWorkerFile(tmp);
									worker.start();
									try {
										worker.join();
									} catch (InterruptedException e1) {
										e1.printStackTrace();
									}
									File answer = worker.getAnswer();
									if(answer != null) {
										synchronized(answers) {
											answers.add(answer);
										}
									}
								}
							} else {
								flag2 = false;
							}
		//				}
					}
		//			if(true) { //переработать
						synchronized(answers) {
							if(!answers.isEmpty()) {
								if(!checkerOut.isAlive()) {
									File tmp = answers.removeFirst();
									//поиск файла
									File absl = getAbsoluteFile(tmp.getName());
									checkerOut.setCheckFile(tmp);
									checkerOut.setAbsoluteFile(absl);
									checkerOut.start();
									try {
										checkerOut.join();
									} catch (InterruptedException e1) {
										e1.printStackTrace();
									}
									boolean flag = checkerOut.getAnswer();
									if(flag)
										textPane.setText(textPane.getText() + tmp.getName() + " is correct\n");
									else 
										textPane.setText(textPane.getText() + tmp.getName() + " is incorrect\n");
									}
							} else {
								flag3 = false;
							}
		//				}
					}
				}*/
				textField_1.setText("");
				textField.setText("");
				btnNewButton_1.setEnabled(false);
				btnStopCurrentThread.setEnabled(false);	
			}
		});
		
		btnStopCurrentThread.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(worker.isAlive()) {
					File tmp = worker.getWorkerFile();
					synchronized(correctTasks) {
						correctTasks.addFirst(tmp);
					}
					worker.interrupt();//????????????????
					stopFlag = true;
				}
			}
		});
	}
}
