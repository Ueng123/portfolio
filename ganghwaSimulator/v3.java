package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class v3 {

    public static boolean percent(int p) {
        return (Math.random()*100 <= p);
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
                case 5: result[0] += 5; break;
                case 6: result[1] += 5; break;
            }
        }

        return result;
    }

    // 1,2(str+10%,+20%) 3,4(dex+10%,20%) 5,6(int+10%,20%) 7,8(luk+10%,20%) 9,10(akt+15%,30%) 11,12(md+15%,30%)
    public static int[] readPotentialOption(int[] options) {

        int[] result = new int[6];
        for (int i = 0; i < 6; i++) {
            result[i] = 0;
        }

        for (int option : options) {
            switch (option) {
                case 0 : break;
                case 1 : result[2] += 10; break;
                case 2 : result[2] += 20; break;
                case 3 : result[3] += 10; break;
                case 4 : result[3] += 20; break;
                case 5 : result[4] += 10; break;
                case 6 : result[4] += 20; break;
                case 7 : result[5] += 10; break;
                case 8 : result[5] += 20; break;
                case 9 : result[0] += 15; break;
                case 10: result[0] += 30; break;
                case 11: result[1] += 15; break;
                case 12: result[1] += 30; break;
            }
        }

        return result;
    }

    public static String[] getPOstring(int[] pO) {
        String[] result = new String[3];

        // 1,2(str+10%,+20%) 3,4(dex+10%,20%) 5,6(int+10%,20%) 7,8(luk+10%,20%) 9,10(akt+15%,30%) 11,12(md+15%,30%)
        int i = 0;
        for (int option : pO) {
            String txt = "";

            switch (option) {
                case 0: txt = "-"; break;
                case 1: txt = "STR +10%"; break;
                case 2: txt = "STR +20%"; break;
                case 3: txt = "DEX +10%"; break;
                case 4: txt = "DEX +20%"; break;
                case 5: txt = "INT +10%"; break;
                case 6: txt = "INT +20%"; break;
                case 7: txt = "LUK +10%"; break;
                case 8: txt = "LUK +20%"; break;
                case 9 : txt = "공격력 +15%"; break;
                case 10: txt = "공격력 +30%"; break;
                case 11: txt = "마력 +15%"; break;
                case 12: txt = "마력 +30%"; break;
            }

            result[i++] = txt;
        }

        return result;
    }

    public static String[] getWeaponString(String wpNM, int ADH, int APH, int[] dS, int[] sS, int[] aO, int[] pO) {
        // 공 올 힘 덱 인 럭

        String[] statName = {"공격력", "마력", "STR", "DEX", "INT", "LUK"};

        int[] aS = readAdditionalOption(aO);
        int[] pP  = readPotentialOption (pO);
        int[] pS = new int[6]; int j = 0;
        for (int percent : pP) { pS[j] = (int)((dS[j] + sS[j] + aS[j]) * ((double)percent/100.0));
            System.out.printf("pS[%d] = (%d + %d + %d) * (%d/100) = %d(%d * %d)\n", j, dS[j], sS[j], aS[j], percent, pS[j], (dS[j] + sS[j] + aS[j]),(percent/100)); j++;
        }

        String[] pOS = getPOstring(pO);

        String[] result = new String[13];
        result[0]  = "   "+wpNM+"("+"+"+ADH+"공 +"+APH+"마)";
        result[1]  = "   ====================";
        for (int i = 0; i < 6; i++) {result[i+2] = "   "+statName[i]+" : "+(dS[i]+sS[i]+aS[i]+pS[i])+"("+dS[i]+"+"+sS[i]+"+"+aS[i]+"+"+pS[i]+")";}
        result[8]  = "   ----------------------------------------";
        for (int i = 0; i < 3; i++) {result[i+9] = "   "+pOS[i];}
        result[12] = "   ====================";
        return result;
    }

    public static int[] rollCube () {
        int[] result = new int[3];
        for (int i = 0; i < 3; i++) {
            result[i] = (int)(Math.random()*11)+1;
        }
        return result;
    }
    public static int[] rollFlame () {
        int[] result = new int[5];
        for (int i = 0; i < 5; i++) {
            result[i] = (int)(Math.random()*5)+1;
        }
        return result;
    }

    public static void addLogMessage(String message) {
        LocalTime now = LocalTime.now();
        Collections.reverse(logMessages);
        if (message == "") logMessages.add("");
        else logMessages.add("  ["+now.getMinute()+":"+now.getSecond()+"] "+message);
        Collections.reverse(logMessages);
    }

    public static void LoadLog() {
        //1
        //2
        //3
        //4
        //5
        //6
        //7
        //8
        //9
        //10
        //11
        //12
        //13
        //14
        //15
        //16
        //17
        //18
        //==
        //남은돈

        Collections.reverse(logLabels);
        for (int i = 0; i<20; i++) {
            if (i >= 18) {logLabels.get(i).setText((new String[] {"========================================", "사용한 돈 = "+usedMoney+"원"})[i-18]); continue;}
            logLabels.get(i).setText(logMessages.get(i));
        }
        Collections.reverse(logLabels);
    }

    static int ADhammerN = 0;
    static int APhammerN = 0;

    // 능력 이름   | 공, 마, 힘, 덱, 인, 럭
    // 스탯 초깃값 | 10, 1 , 4,  4,  4,  4
    static int[] dStats = new int[6];

    // 공, 마, 힘, 덱, 인, 럭
    static int[] sStats = new int[6];

    // 1(str+4) 2(dex+4) 3(int+4) 4(luk+4) 5(atk+5) 6(md+5)
    static int[] aOptions = new int[5];

    // 1,2(str+10%,+20%) 3,4(dex+10%,20%) 5,6(int+10%,20%) 7,8(luk+10%,20%) 9,10(akt+15%,30%) 11,12(ast+15%,30%)
    static int[] pOptions = new int[3];

    static JLabel[] weaponLabels  = {
            new JLabel(),
            new JLabel(),
            new JLabel(),
            new JLabel(),
            new JLabel(),
            new JLabel(),
            new JLabel(),
            new JLabel(),
            new JLabel(),
            new JLabel(),
            new JLabel(),
            new JLabel(),
            new JLabel()
    };

    static List<JLabel> logLabels  = new ArrayList<JLabel>();

    static List<String> logMessages = new ArrayList<String>();

    static int usedMoney = 0;

    public static void main(String[] args) {

        int x = 1920/2;
        int y = 1080/2;

        dStats[0] = 10;
        dStats[1] = 1;
        dStats[2] = 4;
        dStats[3] = 4;
        dStats[4] = 4;
        dStats[5] = 4;

        for (int i = 0; i < 6; i++) {sStats[i] = 0;}
        for (int i = 0; i < 3; i++) {pOptions[i] = 0;}
        for (int i = 0; i < 5; i++) {aOptions[i] = 0;}

        for (int i = 0; i < 20; i++) {addLogMessage("");}

        int Wwidth = 300;
        int Wheight = 450;

        int Uwidth = 500;
        int Uheight = 450;


        JFrame weaponStatus = new JFrame();
        weaponStatus.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        weaponStatus.setSize(Wwidth, Wheight);
        weaponStatus.setResizable(false);
        weaponStatus.setLocation(x-Wwidth-Uwidth/2, y-Wheight/2);
        weaponStatus.setTitle("정보");
        weaponStatus.setLayout(new GridLayout(13, 1, 0, 0));

        JFrame upgradeButtons = new JFrame();
        upgradeButtons.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        upgradeButtons.setSize(Uwidth, Uheight);
        upgradeButtons.setResizable(false);
        upgradeButtons.setLocation(x-Uwidth/2, y-Uheight/2);
        upgradeButtons.setTitle("강화");
        upgradeButtons.setLayout(new GridLayout(4, 2, 0, 40));

        JFrame upgradeLog = new JFrame();
        upgradeLog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        upgradeLog.setSize(Wwidth, Wheight);
        upgradeLog.setResizable(false);
        upgradeLog.setLocation(x+Uwidth/2, y-Wheight/2);
        upgradeLog.setTitle("기록");
        upgradeLog.setLayout(new GridLayout(20, 0, 0, 5));

        JFrame setName = new JFrame();
        setName.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setName.setSize(200, 150);
        setName.setResizable(false);
        setName.setLocation(x-80, y-75);
        setName.setTitle("");
        setName.setLayout(new FlowLayout());

        // setName
        JLabel alert1 = new JLabel("무기 이름을 입력하세요");
        JTextField weaponNameInput = new JTextField("검", 10);
        JButton ConfirmInputButton = new JButton("입력 완료");

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

        String[] hammersNames = {
                "평범한 망치",
                "빛나는 망치",
                "신비한 망치",
                "평범한 마석",
                "빛나는 마석",
                "신비한 마석"
        };
        String[] scrollsNames = {
                "강력한 두루마리",
                "마법의 두루마리",
                "힘의 두루마리",
                "민첩의 두루마리",
                "지능의 두루마리",
                "행운의 두루마리"
        };
        String[] cubesNames = {
                "블루 큐브",
                "레드 큐브",
                "블랙 큐브"
        };
        String[] flamesNames = {
                "강력한 불꽃",
                "장인의 불꽃",
                "명장의 불꽃"
        };

        JComboBox<String> hammerBox = new JComboBox<String>(hammers);
        JComboBox<String> scrollBox = new JComboBox<String>(scrolls);
        JComboBox<String> cubeBox   = new JComboBox<String>(cubes  );
        JComboBox<String> flameBox  = new JComboBox<String>(flames );

        JButton hammerButton = new JButton("망치 사용하기");
        JButton scrollButton = new JButton("주문서 사용하기");
        JButton cubeButton = new JButton("큐브 사용하기");
        JButton flameButton = new JButton("불꽃 사용하기");

        // updateLogs

        for (int i = 0; i < 20; i++) {
            JLabel label = new JLabel("");
            upgradeLog.add(label);
            logLabels.add(label);
        }

        ConfirmInputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String[] weaponStrings = getWeaponString(weaponNameInput.getText(), ADhammerN, APhammerN, dStats, sStats, aOptions, pOptions);
                int i=0;
                for (JLabel labels : weaponLabels) {
                    labels.setText(weaponStrings[i++]);
                    weaponStatus.add(labels);
                }

                weaponStatus.setTitle(weaponNameInput.getText()+"의 정보");
                weaponStatus.setVisible(true);

                upgradeButtons.add(hammerBox); upgradeButtons.add(hammerButton);
                upgradeButtons.add(scrollBox); upgradeButtons.add(scrollButton);
                upgradeButtons.add(cubeBox); upgradeButtons.add(cubeButton);
                upgradeButtons.add(flameBox); upgradeButtons.add(flameButton);

                upgradeButtons.setTitle(weaponNameInput.getText()+" 강화하기");
                upgradeButtons.setVisible(true);

                addLogMessage("환영합니다!");

                LoadLog();

                upgradeLog.setVisible(true);

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
                int idx = hammerBox.getSelectedIndex();System.out.println(idx);
                int sidx = (idx>=3) ? 1 : 0;
                String[] hName = {"망치", "마석"};
                String[] sName = {"망치", "마석"};
                int[] sNum = {1, 3, 5, 1, 3, 5};
                int[] sPer = {90, 40, 25, 90, 40, 25};
                int[] money = {10, 20, 40, 10, 20, 40};

                addLogMessage("["+hName[sidx]+"] " + hammersNames[idx] + "를 사용했습니다.");
                usedMoney += money[idx];
                if (percent(sPer[idx])) {
                    if (sidx == 1) {APhammerN+=sNum[idx];} else {ADhammerN+=sNum[idx];}
                    dStats[sidx]+=sNum[idx];
                    addLogMessage("["+hName[sidx]+"] 강화에 성공하였습니다. ("+sName[sidx]+" +"+sNum[idx]+")");
                } else {
                    addLogMessage("["+hName[sidx]+"] 강화에 실패하였습니다.");
                }

                System.out.println(idx);
                String[] weaponStrings = getWeaponString(weaponNameInput.getText(), ADhammerN, APhammerN, dStats, sStats, aOptions, pOptions);
                int i=0;
                for (JLabel labels : weaponLabels) {
                    System.out.println(labels.getText());
                    labels.setText(weaponStrings[i++]);
                }

                LoadLog();
            }
        });

        scrollButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                    "강력한 두루마리(공격력+2, 60%, 30원)",
                    "마법의 두루마리(마력+2, 60%, 30원)",
                    "힘의 두루마리(STR+2, 70&, 30원)",
                    "민첩의 두루마리(DEX+2, 70&, 30원)",
                    "지능의 두루마리(INT+2, 70&, 30원)",
                    "행운의 두루마리(LUK+2, 70&, 30원)",
                 */
                int idx = scrollBox.getSelectedIndex();System.out.println(idx);
                int sidx = (idx<2) ? 0 : 1;
                int[] sPer = {60, 70};
                String[] statNames = {"공격력", "마력", "STR", "DEX", "INT", "LUK"};

                usedMoney += 30;

                addLogMessage("[주문서] "+scrollsNames[idx]+"를 사용했습니다.");
                if (percent(sPer[sidx])) {
                    sStats[idx]+=2;
                    addLogMessage("[주문서] 주문서의 힘이 무기에 깃들었습니다. ("+statNames[idx]+"+2)");
                } else {
                    addLogMessage("주문서의 힘이 가루가 되어 사라졌습니다.");
                }

                String[] weaponStrings = getWeaponString(weaponNameInput.getText(), ADhammerN, APhammerN, dStats, sStats, aOptions, pOptions);
                int i=0;
                for (JLabel labels : weaponLabels) {
                    labels.setText(weaponStrings[i++]);
                }

                LoadLog();
            }
        });

        cubeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                    "블루 큐브(성공률 50%, 100원)",
                    "레드 큐브(성공률 75%, 200원)",
                    "블랙 큐브(성공률 100%, 400원)"
                 */
                int idx = cubeBox.getSelectedIndex();System.out.println(idx);
                int[] pers = {50, 75, 100};
                int[] money = {100, 200, 400};

                addLogMessage("[큐브] "+cubesNames[idx]+"를 사용했습니다.");
                usedMoney += money[idx];
                if (percent(pers[idx])) {
                    pOptions = rollCube();
                    addLogMessage("[큐브] 잠재능력 변경에 성공하였습니다.");
                } else {
                    addLogMessage("[큐브] 잠재능력 변경에 실패하였습니다.");
                }

                String[] weaponStrings = getWeaponString(weaponNameInput.getText(), ADhammerN, APhammerN, dStats, sStats, aOptions, pOptions);
                int i=0;
                for (JLabel labels : weaponLabels) {
                    labels.setText(weaponStrings[i++]);
                }

                LoadLog();
            }
        });

        flameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*
                    "강력한 불꽃(성공률 50%, 100원)",
                    "장인의 불꽃(성공률 75%, 200원)",
                    "명장의 불꽃(성공률 100%, 300원)"
                 */
                int idx = flameBox.getSelectedIndex();System.out.println(idx);
                int[] pers = {50, 75, 100};
                int[] money = {100, 200, 300};

                addLogMessage("[불꽃] "+flamesNames[idx]+"를 사용했습니다.");
                usedMoney += money[idx];
                if (percent(pers[idx])) {
                    aOptions = rollFlame();
                    addLogMessage("[불꽃] 추가옵션 변경에 성공하였습니다.");
                } else {
                    addLogMessage("[불꽃] 추가옵션 변경에 실패하였습니다.");
                }

                String[] weaponStrings = getWeaponString(weaponNameInput.getText(), ADhammerN, APhammerN, dStats, sStats, aOptions, pOptions);
                int i=0;
                for (JLabel labels : weaponLabels) {
                    labels.setText(weaponStrings[i++]);
                }

                LoadLog();
            }
        });

        // START PROGRAM
        setName.add(alert1);
        setName.add(weaponNameInput);
        setName.add(ConfirmInputButton);

        setName.setVisible(true);

    }
}
