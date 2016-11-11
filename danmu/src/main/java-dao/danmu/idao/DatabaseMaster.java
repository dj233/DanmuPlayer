package danmu.idao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import danmu.dao.DaoMaster;
import danmu.dao.DaoSession;

/**
 * Created by Fischer on 2016/9/19.
 */
public class DatabaseMaster {

    private DanMuDaoWrapper danMuDaoWrapper;
    private boolean inited;

    private static DatabaseMaster mInstance;

    public static DatabaseMaster instc(){
        synchronized (DatabaseMaster.class){
            if(null == mInstance){
                mInstance = new DatabaseMaster();
            }
            return mInstance;
        }
    }

    private DatabaseMaster(){
    }

    public void init(Context context){
        if(inited){
            return ;
        }
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context,"weather.db",null);
        DaoMaster master = new DaoMaster(openHelper.getWritableDatabase());
        DaoSession session = master.newSession();
        danMuDaoWrapper = new DanMuDaoWrapper(session.getDanMuDao());

        inited = true;
    }

    private class DataBaseOpenHelper extends DaoMaster.OpenHelper {
        public DataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DaoMaster.dropAllTables(db, true);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DaoMaster.dropAllTables(db, true);
            onCreate(db);
        }

    }

    public DanMuDaoWrapper getDanMuDaoWrapper() {
        return danMuDaoWrapper;
    }

    public boolean isInited() {
        return inited;
    }
}
