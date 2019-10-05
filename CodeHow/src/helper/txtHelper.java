package helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.tartarus.snowball.ext.PorterStemmer;


public class txtHelper {
	//���շ��������ĵ��ʷ���
    private  String toLine(String word){
        Pattern humpPattern = Pattern.compile("[A-Z][a-z]");
        Matcher matcher = humpPattern.matcher(word);
        StringBuffer str = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(str, " "+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(str);
        return str.toString();
    }
    
    //�ʸ���ȡ
  	private  String stemTerm (String word) {
  		//String word="helping";
  	    PorterStemmer stem = new PorterStemmer();
  	    stem.setCurrent(word);
  	    stem.stem();
  	    return stem.getCurrent();
  		//System.out.println(result);  
  	}
  	
  	//ɾ��ͣ�ô�
  	private String deleteStopWord (String word) {
  		String[] stopword= {"a","an","and","are","as","at","be","but","by","for","if","in","into","is","it","no","not","of","on","or","such","that","the","their","then","there","these","they","this","to","was","will","with","how"};
  		for(String sword:stopword) {
  			if(word.equals(sword)) 
  				return " ";
  		}
		return word;
  	}
  	
    //���ַ������д���
	public  String prepareline(String strLine) {
  		String[] words = strLine.split("\\W");//���ճ���ĸ��÷��ŷָ�
        for (int i = 0; i < words.length; i++) { 
			words[i]=this.toLine(words[i]);
        	words[i]=this.deleteStopWord(words[i]);
        	words[i]=this.stemTerm(words[i]);
        	//System.out.println(words[i]+"+++++++");
        }
        strLine="";
        for(int i=0;i<words.length;i++) {
       	 strLine=strLine.concat(words[i]+" ");
        }
  		return strLine;
  	}
  	
	//����TXT�ļ������ı����зִʵȴ���
  	public  void ReadWriteFile(String readpath,String writepath ) {
  		File DateDir = new File(readpath);
        for (File f : DateDir.listFiles()) {
             //�ļ���
             String fileName = f.getName();
             String file_Name=fileName.substring(0, fileName.lastIndexOf("."));
             try (FileReader reader = new FileReader(readpath+"\\".concat(fileName));
                     BufferedReader getcontent = new BufferedReader(reader) // ����һ�����������ļ�����ת�ɼ�����ܶ���������
             ) {
            	 file_Name=prepareline(file_Name);
            	 /* д��Txt�ļ� */  
                 File writeTXT = new File(writepath+"\\"+file_Name+".txt");
                 // ���û����Ҫ����һ���µ�.txt�ļ�  
                 writeTXT.createNewFile(); // �������ļ�  
                 BufferedWriter out = new BufferedWriter(new FileWriter(writeTXT)); 
                 String line;
                 while ((line = getcontent.readLine()) != null) {
                     // һ�ζ���һ������
                 	line=prepareline(line);
                    out.write(line);    
                    out.newLine(); 
                 } 
                 out.flush(); // �ѻ���������ѹ���ļ�  
                 out.close(); // ���ǵùر��ļ�  
             } catch (IOException e) {
                 e.printStackTrace();
             }
           }
      }
  	
  	@Test
    public  void APIMain() {
    	String readpath = ".\\DATA\\API"; 
    	String writepath = ".\\DATA\\API_result";
        ReadWriteFile(readpath,writepath);
    	System.out.println("�ı����������");
    }
  	

}