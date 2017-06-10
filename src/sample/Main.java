package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

// 57478个词组

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("丽萨聊天机器人");

        Socket socket=new Socket("localhost", 8918);


        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        TextArea inputArea = new TextArea();
        inputArea.setWrapText(true);
        inputArea.setPromptText("在此输入需要切词的句子...");

        TextArea outputArea = new TextArea();
        outputArea.setPromptText("丽萨正在思考中...");
        outputArea.setWrapText(true);
        // 给writeArea设置回车键相应事件
        inputArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // 监听control键
                if(event.getCode() == KeyCode.ALT) {
                    String text = inputArea.getText();
                    // 本机切词测试代码
                    //                    try {
                    //                        outputArea.clear();
                    //                        ChineseParticiple chineseParticiple = new ChineseParticiple(text, outputArea);

                    //                        int len = chineseParticiple.acceptString(text);
                    //                        while (len != 0) {
                    //                            String nowString = text.substring(0, len);
                    //                            len = chineseParticiple.acceptString(nowString);
                    //                        }
                    //                    } catch (IOException e) {
                    //                        e.printStackTrace();
                    //                    }

                    // 服务器切词测试
                    try {
                        //1.创建客户端Socket，指定服务器地址和端口
                        Socket socket = new Socket("localhost", 8918);
                        //2.获取输出流，向服务器端发送信息
                        OutputStream os = socket.getOutputStream();//字节输出流
                        PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
                        // 写下要发给服务器处理的
                        pw.write(text);
                        pw.flush();
                        socket.shutdownOutput();//关闭输出流
                        //3.获取输入流，并读取服务器端的响应信息
                        InputStream is = socket.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String info = null;
                        while ((info = br.readLine()) != null) {
                            outputArea.setText(info);
                        }
                        //4.关闭资源
                        br.close();
                        is.close();
                        pw.close();
                        os.close();
                        socket.close();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        vbox.getChildren().addAll(inputArea, outputArea);


        primaryStage.setScene(new Scene(vbox, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
