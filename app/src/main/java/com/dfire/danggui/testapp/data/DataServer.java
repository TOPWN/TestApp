package com.dfire.danggui.testapp.data;

import com.dfire.danggui.testapp.stickyrecyclerview.bean.Desk;
import com.dfire.danggui.testapp.stickyrecyclerview.bean.DeskSection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DangGui
 * @create 2016/12/17.
 */

public class DataServer {


    private DataServer() {
    }

    public static List<Desk> getDeskSampleData(int lenth) {
        List<Desk> list = new ArrayList<>();
        for (int i = 0; i < lenth; i++) {
            Desk desk = new Desk();
            desk.setDeskId(i);
            desk.setDeskName("A" + i);
            desk.setDeskPicUrl("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            desk.setScope("A区");
            list.add(desk);
        }
        for (int i = 0; i < lenth; i++) {
            Desk desk = new Desk();
            desk.setDeskId(i);
            desk.setDeskName("B" + i);
            desk.setScope("B区");
            desk.setDeskPicUrl("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            list.add(desk);
        }
        for (int i = 0; i < lenth; i++) {
            Desk desk = new Desk();
            desk.setDeskId(i);
            desk.setDeskName("C" + i);
            desk.setScope("C区");
            desk.setDeskPicUrl("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            list.add(desk);
        }
        for (int i = 0; i < lenth; i++) {
            Desk desk = new Desk();
            desk.setDeskId(i);
            desk.setDeskName("D" + i);
            desk.setScope("D区");
            desk.setDeskPicUrl("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            list.add(desk);
        }
        for (int i = 0; i < lenth; i++) {
            Desk desk = new Desk();
            desk.setDeskId(i);
            desk.setDeskName("E" + i);
            desk.setScope("E区");
            desk.setDeskPicUrl("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            list.add(desk);
        }
        return list;
    }

    public static List<DeskSection> getAttenDeskSampleData() {
        List<DeskSection> list = new ArrayList<>();
        list.add(new DeskSection(true, "A区",1));
        for (int i = 0; i < 10; i++) {
            Desk desk = new Desk();
            desk.setDeskId(i);
            desk.setDeskName("A" + i);
            desk.setDeskPicUrl("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            desk.setScope("A区");
            desk.setScopeId(1);
            list.add(new DeskSection(desk));
        }
        list.add(new DeskSection(true, "B区",2));
        for (int i = 0; i < 10; i++) {
            Desk desk = new Desk();
            desk.setDeskId(i);
            desk.setDeskName("B" + i);
            desk.setDeskPicUrl("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            desk.setScope("B区");
            desk.setScopeId(2);
            list.add(new DeskSection(desk));
        }
        list.add(new DeskSection(true, "C区",3));
        for (int i = 0; i < 10; i++) {
            Desk desk = new Desk();
            desk.setDeskId(i);
            desk.setDeskName("C" + i);
            desk.setDeskPicUrl("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            desk.setScope("C区");
            desk.setScopeId(3);
            list.add(new DeskSection(desk));
        }
        list.add(new DeskSection(true, "D区",4));
        for (int i = 0; i < 10; i++) {
            Desk desk = new Desk();
            desk.setDeskId(i);
            desk.setDeskName("D" + i);
            desk.setDeskPicUrl("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            desk.setScope("D区");
            desk.setScopeId(4);
            list.add(new DeskSection(desk));
        }
        list.add(new DeskSection(true, "E区",5));
        for (int i = 0; i < 10; i++) {
            Desk desk = new Desk();
            desk.setDeskId(i);
            desk.setDeskName("E" + i);
            desk.setDeskPicUrl("https://avatars1.githubusercontent.com/u/7698209?v=3&s=460");
            desk.setScope("E区");
            desk.setScopeId(5);
            list.add(new DeskSection(desk));
        }
//        list.add(new DeskSection(true, "Section 1", true));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new DeskSection(true, "Section 2", false));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new DeskSection(true, "Section 3", false));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new DeskSection(true, "Section 4", false));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new DeskSection(true, "Section 5", false));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
//        list.add(new DeskSection(new Desk(HTTPS_AVATARS1_GITHUBUSERCONTENT_COM_LINK, CYM_CHAD)));
        return list;
    }
}

