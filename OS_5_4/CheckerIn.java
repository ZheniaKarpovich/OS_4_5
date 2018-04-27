package OS_5_4;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckerIn extends Thread {
	
	private LinkedList<File> in, correctTasks;
	private Worker worker;
	private File checkFile;	
	private boolean answer = true;
	private Pattern p = null;
	private Matcher m = null;
	private String mask = "[A-Z]+";
	
	public void setInput(LinkedList<File> in) {
		this.in = in;
	}
	
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	
	public void setCorrectTasks(LinkedList<File> correctTasks) {
		this.correctTasks = correctTasks;
	}
		
	public void setCheckFile(File checkFile) {
		this.checkFile = checkFile;
	}
	
	public boolean getAnswer() {
		return this.answer;
	}
		
	public void setMask(String mask) {
		this.mask = mask;
	}
	
	public void run() {
		try {
			System.out.println(this.in);
			while(true) {
				this.answer = true;
				synchronized(this.in) {
					if(!in.isEmpty())
						this.checkFile = in.removeFirst();
					else
						break;
				}
				System.out.println(this.checkFile);
				Scanner sin = new Scanner(this.checkFile);
				int k = Integer.parseInt(sin.nextLine());
				this.p = Pattern.compile(this.mask/*, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE*/);
				if(k >= 1 && k <= 5) {
					int i = 0;
					while(sin.hasNextLine()) {
						String str = sin.nextLine();
						if(str.length() > 100) {
							this.answer = false;
							break;
						}
						this.m = this.p.matcher(str);
						boolean answ = this.m.matches();
						if(!answ) {
							this.answer = false; 
							break;						 
						}
						i++;
					}
					if(i != (2 * k)) 
						this.answer = false; 
				} else {
					this.answer = false;
				}
				if(this.answer) {
					synchronized(this.correctTasks) {
						this.correctTasks.add(this.checkFile);
					}
				}
				sin.close();
			}
			this.worker.setFlag(true);
		} catch (Exception e) {
			this.answer = false;
			System.out.println("Exception in " + e);

		}
	}
}
