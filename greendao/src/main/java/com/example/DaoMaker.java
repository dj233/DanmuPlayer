package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DaoMaker {

    public static void main(String[] args) throws Exception{

        Schema schema = new Schema(1, "danmu.dao");
        schema.setDefaultJavaPackageDao("danmu.dao");

        addNodes(schema);

        new DaoGenerator().generateAll(schema,"E:\\dev\\git-bash\\DanmuPlayer\\danmu\\src\\main\\java-dao");
    }

    private static void addNodes(Schema schema){
        addChannelNode(schema);
    }

    private static void addChannelNode(Schema schema){
        Entity weatherIcon = schema.addEntity("DanMu");
        weatherIcon.setTableName("tb_danmu");
        weatherIcon.addIntProperty("danMuId").columnName("danmu_id").notNull();
        weatherIcon.addStringProperty("danMuText").columnName("danmu_text").notNull();
        weatherIcon.addIntProperty("danMuTick").columnName("danmu_tick").notNull();
    }
}
