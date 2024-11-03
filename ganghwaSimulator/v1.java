
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class v1 {
    
    public static int ganghwa = 0;
    public static int atkdmg = 100;
    public static int ownmeso = 100000;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        
        frame.setTitle("개쩌는 강화시뮬레이터");
        frame.setVisible(true);
        frame.setSize(500, 250);
        frame.setLayout(new FlowLayout());

        JLabel label1 = new JLabel("");
        label1.setText("검 (+"+ ganghwa +")\n | 공격력 : "+(atkdmg + ganghwa*5));

        JLabel SorF = new JLabel("보유중인 메소 : "+ownmeso+"메소");

        JRadioButton check70 = new JRadioButton("주문서 70% (공격력 + 3 | 메소 1000)");
        JRadioButton check30 = new JRadioButton("주문서 30% (공격력 + 10 | 메소 2000)");

        ButtonGroup grp = new ButtonGroup();
        grp.add(check30);
        grp.add(check70);

        // ImageIcon img1 = new ImageIcon("java1.png");
        // ImageIcon img2 = new ImageIcon("image1.png");

        // JButton Gbtn = new JButton("강화(메소 500)", img2);
        // JButton Jbtn = new JButton("주문서", img1);
        JButton Gbtn = new JButton("강화(메소 500)");
        JButton Jbtn = new JButton("주문서");

        Gbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ownmeso -= 500;
                if (ownmeso<0) {ownmeso+=500; return;}
                if (Math.random() > 0.5f) {
                    ganghwa++;
                    label1.setText("검 (+"+ ganghwa +") | 공격력 : "+(atkdmg + ganghwa*5));
                    SorF.setText("강화 성공! | "+"보유중인 메소 : "+ownmeso+"메소");
                } else {
                    if (ganghwa > 10) {ganghwa--;}
                    SorF.setText("강화 실패! | "+"보유중인 메소 : "+ownmeso+"메소");
                    label1.setText("검 (+"+ ganghwa +") | 공격력 : "+(atkdmg + ganghwa*5));
                }
            }
        });
        Jbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (check30.isSelected() && check70.isSelected() || !check30.isSelected() && !check70.isSelected()) return;

                float percent = 0;
                if (check70.isSelected() ) {percent = 0.7f; ownmeso -= 1000; if (ownmeso<0) {ownmeso+=1000; return;} }
                else {percent = 0.3f; ownmeso -= 2000; if (ownmeso<0) {ownmeso+=2000; return;} }

                if (Math.random() <= percent) {
                    if (check70.isSelected()) atkdmg += 3;
                    else atkdmg += 10;
                    SorF.setText("주문서 사용 성공! | "+"보유중인 메소 : "+ownmeso+"메소");
                    label1.setText("검 (+"+ ganghwa +") | 공격력 : "+(atkdmg + ganghwa*5));
                }
                else {
                    SorF.setText("주문서 사용 실패! | "+"보유중인 메소 : "+ownmeso+"메소");
                    label1.setText("검 (+"+ ganghwa +") | 공격력 : "+(atkdmg + ganghwa*5));
                }
            }
        });

    
        frame.add(label1);
        frame.add(Gbtn);
        frame.add(Jbtn);
        frame.add(check70);
        frame.add(check30);
        frame.add(SorF);
    }
}
