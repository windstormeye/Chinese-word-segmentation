package sample;

import com.sun.xml.internal.fastinfoset.util.StringArray;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by pjpjpj on 2017/5/21.
 */
public class ChineseParticiple {
    public String beginString;
    public String finalString;
    StringArray stringArr;
    TextArea outputArea;

    public ChineseParticiple(String beginString, TextArea outputArea) throws IOException {
        this.beginString = beginString;
        this.outputArea = outputArea;
        participleManage();
    }

    public String getBeginString() {
        return beginString;
    }

    public void setBeginString(String beginString) {
        this.beginString = beginString;
    }

    public String getFinalString() {
        return finalString;
    }

    public void setFinalString(String finalString) {
        this.finalString = finalString;
    }

    private void participleManage() throws IOException {
        System.out.println(beginString);

        stringArr = new StringArray();

        String text=null;
        FileReader fileReader=null;
        BufferedReader bufferedReader=null;
        try{
            // 词典文件路径
            fileReader=new FileReader("/Users/incloud/Desktop/中文字典匹配.txt");
            bufferedReader=new BufferedReader(fileReader);
            try{
                String read=null;
                while((read=bufferedReader.readLine())!=null){
                    text=text+read + " ";
                    // 把读到的每一个字符串都存到数组中
                    stringArr.add(read);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(fileReader!=null){
                fileReader.close();
            }
        }
    }

    // 接受用户输入字符串
    public int acceptString(String accString) {
        // 循环取
        for (int i = 0; i < accString.length(); i ++) {
            String nowString = accString.substring(i);
            // 调用匹配词典方法
            if (dealString(nowString)) {
                int len = nowString.length();
                outputArea.appendText(nowString + ",");
                return accString.length() - len;
            }
        }
        return 0;
    }

    // 匹配词典方法
    private boolean dealString(String dealString) {
        for (int i = 0; i < stringArr.getSize(); i ++){
            String tempString = stringArr.get(i);
            // 如果匹配成功,返回真
            if (dealString.equals(tempString)) {
                return true;
            }
        }
        return false;
    }
}
