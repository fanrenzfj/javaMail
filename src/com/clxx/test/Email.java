package com.clxx.test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.MimeUtility;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

/**
 * 使用imap协议获取未读邮件数
 *
 * @author w
 *
 */
public class Email {

    public static void main(String[] args) throws Exception {
      /*  String user = "fanrenzfj@126.com";// 邮箱的用户名
        String password = "zs1234"; // 邮箱的密码*/
        String user = "zhengshuai@suitbim.com";// 邮箱的用户名
        String password = "Zs1234"; // 邮箱的密码

        Properties prop = System.getProperties();
        prop.put("mail.store.protocol", "imap");
        prop.put("mail.imap.host", "imap.suitbim.com");

        Session session = Session.getInstance(prop);

        int total = 0;
        IMAPStore store = (IMAPStore) session.getStore("imap"); // 使用imap会话机制，连接服务器
        store.connect(user, password);
        IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX"); // 收件箱
        folder.open(Folder.READ_WRITE);
        // 获取总邮件数
        total = folder.getMessageCount();
        System.out.println("-----------------共有邮件：" + total
                + " 封--------------");
        // 得到收件箱文件夹信息，获取邮件列表
        System.out.println("未读邮件数：" + folder.getUnreadMessageCount());
        Message[] messages = folder.getMessages();
        int messageNumber = 0;
        for (Message message : messages) {
            System.out.println("发送时间：" + message.getSentDate());
            System.out.println("主题：" + message.getSubject());
            System.out.println("内容：" + message.getContent().toString());
            Flags flags = message.getFlags();
            if (flags.contains(Flags.Flag.SEEN))
                System.out.println("这是一封已读邮件");
            else {
                System.out.println("未读邮件");
            }
            System.out
                    .println("========================================================");
            System.out
                    .println("========================================================");
            //每封邮件都有一个MessageNumber，可以通过邮件的MessageNumber在收件箱里面取得该邮件
            messageNumber = message.getMessageNumber();
        }
        Message message = folder.getMessage(messageNumber);
        System.out.println(message.getContent()+message.getContentType());
        // 释放资源
        if (folder != null)
            folder.close(true);
        if (store != null)
            store.close();
    }

/*
        Multipart multipart = (Multipart) message.getContent();
        int count = multipart.getCount();    // 部件个数
        for(int i=0; i<count; i++) {
            // 单个部件     注意：单个部件有可能又为一个Multipart，层层嵌套
            BodyPart part = multipart.getBodyPart(i);
            // 单个部件类型
            String type = part.getContentType().split(";")[0];
            *//**
             * 类型众多，逐一判断，其中TEXT、HTML类型可以直接用字符串接收，其余接收为内存地址
             * 可能不全，如有没判断住的，请自己打印查看类型，在新增判断
             *//*
            if(type.equals("multipart/alternative")) {        // HTML （文本和超文本组合）
                System.out.println("超文本:" + part.getContent().toString());
            }else if(type.equals("text/plain")) {    // 纯文本
                System.out.println("纯文本:" + part.getContent().toString());
            }else if(type.equals("text/html")){    // HTML标签元素
                System.out.println("HTML元素:" + part.getContent().toString());
            }else if(type.equals("multipart/related")){    // 内嵌资源 (包涵文本和超文本组合)
                System.out.println("内嵌资源:" + part.getContent().toString());
            }else if(type.contains("application/")) {        // 应用附件 （zip、xls、docx等）
                System.out.println("应用文件：" + part.getContent().toString());
            }else if(type.contains("image/")) {            // 图片附件 （jpg、gpeg、gif等）
                System.out.println("图片文件：" + part.getContent().toString());
            }


*//*****************************************获取邮件内容方法***************************************************//*
        *//**
         * 附件下载
         * 这里针对image图片类型附件做下载操作，其他类型附件同理
         *//*
        if(type.contains("image/")) {
            // 打开附件的输入流
            DataInputStream in = new DataInputStream(part.getInputStream());
            // 一个文件输出流
            FileOutputStream out = null;
            // 获取附件名
            String fileName = part.getFileName();
            // 文件名解码
            fileName = MimeUtility.decodeText(fileName);
            // 根据附件名创建一个File文件
            File file = new File("d:/data/" + fileName);
            // 查看是否有当前文件
            Boolean b = file.exists();
            if(!b) {
                out = new FileOutputStream(file);
                int data;
                // 循环读写
                while((data=in.read()) != -1) {
                    out.write(data);
                }
                System.out.println("附件：【" + fileName + "】下载完毕，保存路径为：" + file.getPath());
            }

            // 关流
            if(in != null) {
                in.close();
            }
            if(out != null) {
                out.close();
            }
        }

        *//**
         * 获取超文本复合内容
         * 他本是又是一个Multipart容器
         * 此时邮件会分为TEXT（纯文本）正文和HTML正文（HTML标签元素）
         *//*
        if(type.equals("multipart/alternative")) {
            Multipart m = (Multipart) part.getContent();
            for (int k=0; k<m.getCount(); k++) {
                if(m.getBodyPart(k).getContentType().startsWith("text/plain")) {
                    // 处理文本正文
                    System.out.println("TEXT文本内容："+"\n" + m.getBodyPart(k).getContent().toString().trim()+"\n");
                } else {
                    // 处理 HTML 正文
                    System.out.println("HTML文本内容："+"\n" + m.getBodyPart(k).getContent()+"\n");
                }
            }
        }

    }

    *//**
     * 最后千万别忘记了关闭
     *//*
        folder.close(false); // false为不更新邮件，true为更新，一般在删除邮件后使用
        store.close();*/
}



