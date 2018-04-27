package OS_5_4;
// проверка на равность строк
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Worker extends Thread {
	private File workerFile, answer;
	private LinkedList<File> answers, correctTasks;
	private boolean stopFlag = false;
	private CheckerOut checkerOut;
	
	public void setFlag(boolean stopFlag) {
		this.stopFlag = stopFlag;
	}
	
	public void setCheckerOut(CheckerOut checkerOut) {
		this.checkerOut = checkerOut;
	}
	
	public void setWorkerFile(File workerFile) {
		this.workerFile = workerFile;
	}
	
	public File getAnswer() {
		return this.answer;
	}
	
	public File getWorkerFile() {
		return this.workerFile;
	}
	
	public void setAnswers(LinkedList<File> answers) {
		this.answers = answers;
	}
	
	public void setCorrectTasks(LinkedList<File> correctTasks) {
		this.correctTasks = correctTasks;
	}
	
	public boolean getAnswer(String str1, String str2) {
		byte[] first = str1.getBytes();
		byte[] second = str2.getBytes();
		boolean result = true;
		for(int i = 0; i < second.length; i++) {
			if(second[i] == 65)
				second[i] = 90;
			else
				second[i] = (byte) (second[i] - 1); 
		}
		Arrays.sort(first);
		Arrays.sort(second);
		for(int i = 0; i < second.length; i++) {
			if(first[i] != second[i])
				result = false;
		}
		return result;
	}
	
	public void run() {
		try {
			if(!this.isInterrupted()) {
				while(true) {
					synchronized(this.correctTasks) {
						if(!this.correctTasks.isEmpty())
							this.workerFile = this.correctTasks.removeFirst();
						else if(this.stopFlag)
							break;
		//				System.out.println(this.workerFile);
					}
					if(this.workerFile != null) {
						System.out.println(this.workerFile);
						StringTokenizer st = new StringTokenizer(this.workerFile.getName());
						File fos = new File(st.nextToken(".") + ".out");
						FileWriter out = new FileWriter(fos);
						Scanner in = new Scanner(this.workerFile);
						int k = Integer.parseInt(in.nextLine());
						for(int i = 0; i < k; i++) {
							String str1 = in.nextLine();
							String str2 = in.nextLine();
							boolean answ = getAnswer(str1, str2);
							if(answ)
								out.write("YES\n");
							else
								out.write("NO\n");
						}
						this.answer = fos;
						synchronized(this.answers) {
							this.answers.add(this.answer);
						}
						out.close();
						in.close();
					}
				}
				this.checkerOut.setStopFlag(true);
			} else {
				this.answer = null;
			}
		} catch (Exception e) {
			this.answer = null;
			System.out.println("Exception worker " + e);
		}
	}
}
