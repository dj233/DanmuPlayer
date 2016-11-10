package danmu.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import danmu.dao.DanMu;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "tb_danmu".
*/
public class DanMuDao extends AbstractDao<DanMu, Void> {

    public static final String TABLENAME = "tb_danmu";

    /**
     * Properties of entity DanMu.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property DanMuId = new Property(0, int.class, "danMuId", false, "danmu_id");
        public final static Property DanMuText = new Property(1, String.class, "danMuText", false, "danmu_text");
        public final static Property DanMuTick = new Property(2, long.class, "danMuTick", false, "danmu_tick");
    };


    public DanMuDao(DaoConfig config) {
        super(config);
    }
    
    public DanMuDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"tb_danmu\" (" + //
                "\"danmu_id\" INTEGER NOT NULL ," + // 0: danMuId
                "\"danmu_text\" TEXT NOT NULL ," + // 1: danMuText
                "\"danmu_tick\" INTEGER NOT NULL );"); // 2: danMuTick
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"tb_danmu\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, DanMu entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getDanMuId());
        stmt.bindString(2, entity.getDanMuText());
        stmt.bindLong(3, entity.getDanMuTick());
    }

    /** @inheritdoc */
    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    /** @inheritdoc */
    @Override
    public DanMu readEntity(Cursor cursor, int offset) {
        DanMu entity = new DanMu( //
            cursor.getInt(offset + 0), // danMuId
            cursor.getString(offset + 1), // danMuText
            cursor.getLong(offset + 2) // danMuTick
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, DanMu entity, int offset) {
        entity.setDanMuId(cursor.getInt(offset + 0));
        entity.setDanMuText(cursor.getString(offset + 1));
        entity.setDanMuTick(cursor.getLong(offset + 2));
     }
    
    /** @inheritdoc */
    @Override
    protected Void updateKeyAfterInsert(DanMu entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    /** @inheritdoc */
    @Override
    public Void getKey(DanMu entity) {
        return null;
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
