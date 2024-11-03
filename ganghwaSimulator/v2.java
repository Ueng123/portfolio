package balpyo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class v2 {

    public static boolean percent(int p) {
        return (Math.random() <= p/100);
    }

    // 1(str+4) 2(dex+4) 3(int+4) 4(luk+4) 5(atk+5) 6(md+5)
    public static int[] readAdditionalOption(int[] options) {

        int[] result = new int[6];
        for (int i = 0; i < 6; i++) {
            result[i] = 0;
        }

        for (int option : options) {
            switch (option) {
                case 1: result[2] += 4; break;
                case 2: result[3] += 4; break;
                case 3: result[4] += 4; break;
                case 4: result[5] += 4; break;
                case 5: result[1] += 5; break;
                case 6: result[2] += 5; break;
            }
        }

        return result;
    }

    // 1,2(str+3%,+6%) 3,4(dex+3%,6%) 5,6(int+3%,6%) 7,8(luk+3%,6%) 9,10(akt+5%,9%) 11,12(md+5%,9%)
    public static int[] readPotentialOption(int[] options) {

        int[] result = new int[6];
        for (int i = 0; i < 6; i++) {
            result[i] = 0;
        }

        for (int option : options) {
            switch (option) {
                case 0 : break;
                case 1 : result[2] += 3; break;
                case 2 : result[2] += 6; break;
                case 3 : result[3] += 3; break;
                case 4 : result[3] += 6; break;
                case 5 : result[4] += 3; break;
                case 6 : result[4] += 6; break;
                case 7 : result[5] += 3; break;
                case 8 : result[5] += 6; break;
                case 9 : result[1] += 5; break;
                case 10: result[1] += 9; break;
                case 11: result[2] += 5; break;
                case 12: result[2] += 9; break;
            }
        }

        return result;
    }

    public static String[] getPOstring(int[] pO) {
        String[] result = new String[3];
        
        // 1,2(str+3%,+6%) 3,4(dex+3%,6%) 5,6(int+3%,6%) 7,8(luk+3%,6%) 9,10(akt+5%,9%) 11,12(md+5%,9%)
        int i = 0;
        for (int option : pO) {
            String txt = "";

            switch (option) {
                case 0: txt = "-"; break;
                case 1: txt = "STR +3%"; break;
                case 2: txt = "STR +6%"; break;
                case 3: txt = "DEX +3%"; break;
                case 4: txt = "DEX +6%"; break;
                case 5: txt = "INT +3%"; break;
                case 6: txt = "INT +6%"; break;
                case 7: txt = "LUK +3%"; break;
                case 8: txt = "LUK +6%"; break;
                case 9 : txt = "공격력 +5%"; break;
                case 10: txt = "공격력 +9%"; break;
                case 11: txt = "마력 +5%"; break;
                case 12: txt = "마력 +9%"; break;
            }

            result[i++] = txt;
        }

        return result;
    }

    public static String[] getWeaponString(String wpNM, int[] dS, int[] sS, int[] aO, int[] pO) {
        // 공 올 힘 덱 인 럭

        String[] statName = {"공격력", "마력", "STR", "DEX", "INT", "LUK"};

        int[] aS = readAdditionalOption(aO);
        int[] pP  = readPotentialOption (pO);
        int[] pS = new int[6]; int j = 0;
        for (int percent : pP) { pS[j] = ((dS[j] + aS[j]) * percent)/100; j++; }

        String[] pOS = getPOstring(pO);

        String[] result = new String[13];
        result[0]  = wpNM;
        result[1]  = "====================";
        for (int i = 0; i < 6; i++) {result[i+2] = statName[i]+" : "+(dS[i]+sS[i]+aS[i]+pS[i])+"("+dS[i]+"+"+sS[i]+"+"+aS[i]+"+"+pS[i]+")";}
        result[8]  = "----------------------------------------";
        for (int i = 0; i < 3; i++) {result[i+9] = pOS[i];}
        result[12] = "====================";
        return result;
    }

    static int ADhammerN = 0;
    static int APhammerN = 0;

    public static void main(String[] args) {

        int x = 1920/2;
        int y = 1080/2;

        // 공격력 + 1 * hammerN
        

        // 능력 이름   | 공, 마, 힘, 덱, 인, 럭
        // 스탯 초깃값 | 10, 1 , 4,  4,  4,  4
        int[] dStats = new int[6];
        dStats[0] = 10;
        dStats[1] = 1;
        dStats[2] = 4;
        dStats[3] = 4;
        dStats[4] = 4;
        dStats[5] = 4;

        // 공, 마, 힘, 덱, 인, 럭
        int[] sStats = new int[6];
        for (int i = 0; i < 6; i++) {sStats[i] = 0;}

        // 1(str+4) 2(dex+4) 3(int+4) 4(luk+4) 5(atk+5) 6(md+5)
        int[] aOptions = new int[5];
        for (int i = 0; i < 5; i++) {aOptions[i] = 0;}

        // 1,2(str+3%,+6%) 3,4(dex+3%,6%) 5,6(int+3%,6%) 7,8(luk+3%,6%) 9,10(akt+5%,9%) 11,12(ast+5%,9%)
        int[] pOptions = new int[3];
        for (int i = 0; i < 3; i++) {pOptions[i] = 0;}

        // 무기 이름
        // === idx:1
        // 스탯 6줄
        // --- idx:8
        // 잠재 3줄
        // === idx:12

        JFrame weaponStatus = new JFrame();
        weaponStatus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        weaponStatus.setSize(300, 450);
        weaponStatus.setResizable(false);
        weaponStatus.setLocation(x-150-150, y-325);
        weaponStatus.setTitle("무기 정보");
        weaponStatus.setLayout(new GridLayout(13, 1, 0, 0));

        JFrame upgradeButtons = new JFrame();
        upgradeButtons.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        upgradeButtons.setSize(500, 450);
        upgradeButtons.setResizable(false);
        upgradeButtons.setLocation(x-150+150, y-325);
        upgradeButtons.setTitle("강화");
        upgradeButtons.setLayout(new GridLayout(4, 2, 0, 40));

        JFrame setName = new JFrame();
        setName.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setName.setSize(200, 150);
        setName.setResizable(false);
        setName.setLocation(x-80, y-75);
        setName.setTitle("");
        setName.setLayout(new FlowLayout());

        // setName
        JLabel alert1 = new JLabel("무기 이름을 입력하세요");
        JTextField weaponNameInput = new JTextField("", 10);
        JButton ConfirmInputButton = new JButton("입력 완료");

        // weapoonStatus
        JLabel[] weaponLabels  = new JLabel[13];

        // upgradeButtons
        String[] hammers = {
            "평범한 망치(공격력+1, 90%, 10원)",
            "빛나는 망치(공격력+3, 40%, 20원)",
            "신비한 망치(공격력+5, 25%, 40원)",
            "평범한 마석(마력+1, 90%, 10원)",
            "빛나는 마석(마력+3, 40%, 20원)",
            "신비한 마석(마력+5, 25%, 40원)"
        };
        String[] scrolls = {
            "강력한 두루마리(공격력+2, 60%, 30원)",
            "마법의 두루마리(마력+2, 60%, 30원)",
            "힘의 두루마리(STR+2, 70&, 30원)",
            "민첩의 두루마리(DEX+2, 70&, 30원)",
            "지능의 두루마리(INT+2, 70&, 30원)",
            "행운의 두루마리(LUK+2, 70&, 30원)",
        };
        String[] cubes = {
            "블루 큐브(성공률 50%, 100원)",
            "레드 큐브(성공률 75%, 200원)",
            "블랙 큐브(성공률 100%, 300원)"
        };
        String[] flames = {
            "강력한 불꽃(성공률 50%, 100원)",
            "장인의 불꽃(성공률 75%, 200원)",
            "명장의 불꽃(성공률 100%, 300원)"
        };

        JComboBox<String> hammerBox = new JComboBox<String>(hammers);
        JComboBox<String> scrollBox = new JComboBox<String>(scrolls);
        JComboBox<String> cubeBox   = new JComboBox<String>(cubes  );
        JComboBox<String> flameBox  = new JComboBox<String>(flames );

        JButton hammerButton = new JButton("망치 사용하기");
        JButton scrollButton = new JButton("주문서 사용하기");
        JButton cubeButton = new JButton("큐브 사용하기");
        JButton flameButton = new JButton("불꽃 사용하기");

        ConfirmInputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String[] weaponStrings = getWeaponString(weaponNameInput.getText(), dStats, sStats, aOptions, pOptions);
                int i=0;
                for (String line : weaponStrings) {
                    JLabel label = new JLabel(line);
                    weaponLabels[i] = label;
                    weaponStatus.add(label);
                }

                weaponStatus.setTitle(weaponNameInput.getText()+"의 정보");
                weaponStatus.setVisible(true);

                upgradeButtons.add(hammerBox); upgradeButtons.add(hammerButton);
                upgradeButtons.add(scrollBox); upgradeButtons.add(scrollButton);
                upgradeButtons.add(cubeBox); upgradeButtons.add(cubeButton);
                upgradeButtons.add(flameBox); upgradeButtons.add(flameButton);

                upgradeButtons.setTitle(weaponNameInput.getText()+" 강화하기");
                upgradeButtons.setVisible(true);

                setName.setVisible(false);
            }
        });

        hammerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                    "평범한 망치(공격력+1, 90%, 10원)",
                    "빛나는 망치(공격력+3, 40%, 20원)",
                    "신비한 망치(공격력+5, 25%, 40원)",
                    "평범한 마석(마력+1, 90%, 10원)",
                    "빛나는 마석(마력+3, 40%, 20원)",
                    "신비한 마석(마력+5, 25%, 40원)"
                 */
                int idx = hammerBox.getSelectedIndex();
                switch (idx) {
                    case 0: if (percent(90)) {ADhammerN+=1;}; break;
                    case 3: if (percent(90)) {APhammerN+=1;}; break;
                }
            }
        });

        // START PROGRAM
        setName.add(alert1);
        setName.add(weaponNameInput);
        setName.add(ConfirmInputButton);

        setName.setVisible(true);

    }
}
