package architect;

import architect.impl.*;

/**
 * @author:ben.gu
 * @Date:2020/6/24 10:41 下午
 */
public class Week3Work2 {

    public static void main(String[] args) {
        Painter painter = new PainterImpl();
        WinForm winForm = new WinForm("WINDOW窗口");
        winForm.addChild(new Picture("LOGO 图片"));
        winForm.addChild(new Button("登录"));
        winForm.addChild(new Button("注册LOGO 图片"));
        Frame frame1 = new Frame("FRAME1");
        winForm.addChild(frame1);
        frame1.addChild(new Lable("用户名"));
        frame1.addChild(new TextBox("文本框"));
        frame1.addChild(new Lable("密码"));
        frame1.addChild(new PasswordBox("密码框"));
        frame1.addChild(new CheckBox("复选框"));
        frame1.addChild(new TextBox("记住用户名"));
        frame1.addChild(new TextBox("忘记密码"));

        winForm.print(painter);
    }

}
