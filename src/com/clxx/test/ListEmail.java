package com.clxx.test;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.imap.SortTerm;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ListEmail {
    public static void main(String[] args) throws Exception {
        //上传git啊
        // 邮箱的用户名
        String user = "zhengshuai@suitbim.com";
        //如果是网易邮箱 需要开启IMAP服务 并且设置连接口令，此就是登录口令。 其他邮箱不需要 直接是密码
        String password = "Zs1234";
        Properties prop = System.getProperties();
        //选择电子邮件连接的协议
        prop.put("mail.store.protocol", "imap");
        //设置要连接的服务器
        prop.put("mail.imap.host", "imap.suitbim.com");
        //得到连接会话对象
        Session session = Session.getInstance(prop);
        int total = 0;
        // 使用imap会话机制，连接服务器
        IMAPStore store = (IMAPStore) session.getStore("imap");
        store.connect(user, password);
        // 收件箱
        IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
        folder.open(Folder.READ_WRITE);
        // 获取总邮件数
        total = folder.getMessageCount();
        System.out.println("-----------------共有邮件：" + total
                + " 封--------------");
        // 得到收件箱文件夹信息，获取邮件列表
        System.out.println("未读邮件数：" + folder.getUnreadMessageCount());
        /*获取指定索引的邮件
        Message message = folder.getMessages(2);
        获取开始和结束的邮件包含start和end
        Message[] messages = folder.getMessages(1,11);*/
        //获取收件箱所有邮件
        Message [] messages=folder.getMessages();
        //数组转集合
        List<Message> messageList=Arrays.asList(messages);
        //元素反转 倒序排列
        Collections.reverse(messageList);
        Integer count = 0;
        for (Message message : messageList) {
            //获取邮件的标记状态
            Flags flags = message.getFlags();
            //判断是否为未读邮件
            if (!flags.contains(Flags.Flag.SEEN)){
                System.out.println("发送时间：" + getDate(message.getSentDate()));
                System.out.println("主题：" + message.getSubject());
                System.out.println("内容：" + message.getContent().toString());
                System.out.println("未读邮件"+(++count));
            }
        }
    }
    public static String getDate(Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd");
       return simpleDateFormat.format(date);
    }
}
