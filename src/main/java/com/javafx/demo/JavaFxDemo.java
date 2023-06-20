package com.javafx.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

public class JavaFxDemo extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        // md5 base64 加密解密按钮
        Button bt5en = new Button("md5加密");
        Button bt5de = new Button("md5解密");
        Button bt64en = new Button("base64加密");
        Button bt64de = new Button("base64解密");

        // 处理前文本框(多行文本框)
        TextArea textAreaOld = new TextArea();
        textAreaOld.setPrefHeight(200);
        textAreaOld.setPrefWidth(200);
        textAreaOld.setFocusTraversable(false); // 取消焦点
        textAreaOld.setWrapText(true); // 自动换行
        textAreaOld.setPromptText("填入需要加密或解密的信息");

        // 处理后文本框(多行文本框)
        TextArea textAreaNew = new TextArea();
        textAreaNew.setPrefHeight(200);
        textAreaNew.setPrefWidth(200);
        textAreaNew.setFocusTraversable(false);
        textAreaNew.setWrapText(true);
        textAreaNew.setPromptText("此处输出加密或解密的信息");

        // bt5en 按钮监听 md5加密
        bt5en.setOnAction(encryptionMd5(textAreaOld,textAreaNew));
        // bt5de 按钮监听 md5解密
        bt5de.setOnAction(decryptMd5(textAreaOld,textAreaNew));
        // bt64de 按钮监听 base64加密
        bt64en.setOnAction(encryptionBase64(textAreaOld,textAreaNew));
        // bt64de 按钮监听 base64解密
        bt64de.setOnAction(decryptBase64(textAreaOld,textAreaNew));


        // 按钮组件
        VBox vBoxBt = new VBox();
        vBoxBt.getChildren().addAll(bt5en,bt5de,bt64en,bt64de);
        vBoxBt.setAlignment(Pos.CENTER);
        vBoxBt.setSpacing(10);

        // 处理前组件
        HBox hBoxEn = new HBox();
        hBoxEn.getChildren().add(textAreaOld);
        hBoxEn.setAlignment(Pos.CENTER);

        // 处理后组件
        HBox hBoxDe = new HBox();
        hBoxDe.getChildren().add(textAreaNew);
        hBoxEn.setAlignment(Pos.CENTER);

        // 流式布局容器
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10); // 设置水平间隔
        flowPane.setVgap(10); // 设置垂直间隔
        flowPane.setAlignment(Pos.CENTER); // 设置居中对齐
        flowPane.getChildren().addAll(hBoxEn,vBoxBt,hBoxDe);

        // 场景
        Scene scene = new Scene(flowPane);

        // 平台
        stage.setScene(scene);
        stage.setTitle("加密解密工具");
        stage.setHeight(350);
        stage.setWidth(600);
        stage.show();
    }

    // md5 解密
    public EventHandler encryptionMd5(TextArea textAreaOld, TextArea textAreaNew){
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String text = textAreaOld.getText();
                if (text != null) {
                    try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.update(text.getBytes());
                        byte[] digest = md.digest();
                        StringBuilder sb = new StringBuilder();
                        for (byte b : digest) {
                            sb.append(String.format("%02x", b & 0xff));
                        }
                        textAreaNew.setText(sb.toString());
//                        int i = 1/0;
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("error");
                        alert.setHeaderText("md5加密失败");
                        alert.setContentText("请检查输入的信息");
                        alert.showAndWait();
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        return eventHandler;
    }

    // md5 解密
    public EventHandler decryptMd5(TextArea textAreaOld,TextArea textAreaNew) {
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("md5解密失败");
                alert.setContentText("md5算法无法逆向解密，但因值加密后密文固定。\n" +
                        "因此可以通过网站查询已知密文对应的明文\n" +
                        "https://www.cmd5.com 此网站提供该功能");
                alert.showAndWait();
            }
        };
        return eventHandler;
    }

    // Base64 加密
    public EventHandler encryptionBase64(TextArea textAreaOld,TextArea textAreaNew){
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String text = textAreaOld.getText();
                    String encodedString  = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
                    textAreaNew.setText(encodedString);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("error");
                    alert.setHeaderText("Base64加密失败");
                    alert.setContentText("请检查输入的信息");
                    alert.showAndWait();
                    throw new RuntimeException(e);
                }
            }
        };
        return eventHandler;
    }

    // Base64 解密
    public EventHandler decryptBase64(TextArea textAreaOld,TextArea textAreaNew){
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String text = textAreaOld.getText();
                    String decodedString = new String(Base64.getDecoder().decode(text), StandardCharsets.UTF_8);
                    textAreaNew.setText(decodedString);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("error");
                    alert.setHeaderText("Base64解密失败");
                    alert.setContentText("请检查输入的信息");
                    alert.showAndWait();
                    throw new RuntimeException(e);
                }

            }
        };
        return eventHandler;
    }
}
