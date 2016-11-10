package danmu.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import danmu.dao.DanMu;

import danmu.dao.DanMuDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig danMuDaoConfig;

    private final DanMuDao danMuDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        danMuDaoConfig = daoConfigMap.get(DanMuDao.class).clone();
        danMuDaoConfig.initIdentityScope(type);

        danMuDao = new DanMuDao(danMuDaoConfig, this);

        registerDao(DanMu.class, danMuDao);
    }
    
    public void clear() {
        danMuDaoConfig.getIdentityScope().clear();
    }

    public DanMuDao getDanMuDao() {
        return danMuDao;
    }

}
