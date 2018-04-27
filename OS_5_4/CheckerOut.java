package OS_5_4;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JTextPane;

public class CheckerOut extends Thread {
	private File checkFile, absolute;
	private boolean answer = true;
	private LinkedList<File> answers, out;
	private JTextPane textPane;
	private boolean stopFlag = false;
	
	public void setTextPane(JTextPane textPane) {
		this.textPane = textPane;
	}
	
	public void setStopFlag(boolean stopFlag) {
		this.stopFlag = stopFlag;
	}
	
	public void setAnswers(LinkedList<File> answers) {
		this.answers = answers;
	}
	
	public void setOutput(LinkedList<File> out) {
		this.out = out;
	}
	
	public void setCheckFile(File checkFile) {
		this.checkFile = checkFile;
	}
	
	public void setAbsoluteFile(File absolute) {
		this.absolute = absolute;
	}
	
	public boolean getAnswer() {
		return this.answer;
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
	
	public void run() {
		try {
			System.out.println("Hello checker");
			while(true) {
				this.answer = true;
				synchronized(this.answers) {
	//				System.out.println(this.answers);
					if(!this.answers.isEmpty())
						this.checkFile = this.answers.removeFirst();
					else if(this. stopFlag)
						break;
		//			System.out.println(this.checkFile);
				}
				if(this.checkFile != null) {
					System.out.println(this.checkFile);
					synchronized(this.out) { //необязательо
						this.absolute = getAbsoluteFile(this.checkFile.getName());
					}
					System.out.println(this.checkFile + " %% " + this.absolute);
					Scanner firstIn = new Scanner(this.checkFile);
					Scanner secondIn = new Scanner(this.absolute);
					while(firstIn.hasNextLine()) {
						String str1 = firstIn.nextLine();
						String str2 = secondIn.nextLine();
						if(!str1.equals(str2)) {
							this.answer = false;
							break;
						}
					}
					if(this.answer)
						this.textPane.setText(textPane.getText() + this.checkFile.getName() + " is correct\n");
					else 
						this.textPane.setText(textPane.getText() + this.checkFile.getName() + " is incorrect\n");
					firstIn.close();
					secondIn.close();
				}
			}
		
		} catch (Exception e) {
			this.answer = false;
			System.out.println("Exception out " + e);
		}
	}
}
