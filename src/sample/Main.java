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

import java.io.IOException;

// 57478个词组

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("中文切词测试");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        TextArea inputArea = new TextArea();
        inputArea.setPromptText("在此输入需要切词的句子...");

        TextArea outputArea = new TextArea();
        outputArea.setPromptText("切词完成后的词汇出现在这,如果未出现则表明词库中没有...");
        // 给writeArea设置回车键相应事件
        inputArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // 监听control键
                if(event.getCode() == KeyCode.ALT){
                    String text = inputArea.getText();
                    try {
                        outputArea.clear();
                        ChineseParticiple chineseParticiple = new ChineseParticiple(text, outputArea);
                        int len = chineseParticiple.acceptString(text);
                        while (len != 0) {
                            String nowString = text.substring(0, len);
                            len = chineseParticiple.acceptString(nowString);
                        }

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
