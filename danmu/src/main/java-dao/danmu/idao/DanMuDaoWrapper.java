package danmu.idao;

import java.util.List;
import danmu.dao.DanMu;
import danmu.dao.DanMuDao;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Fischer on 2016/9/19.
 */
public class DanMuDaoWrapper {

    private DanMuDao mDao;

    public DanMuDaoWrapper(DanMuDao dao) {
        this.mDao = dao;
    }

    public void insertAll(List<DanMu> danMuList){
        mDao.deleteAll();
        mDao.insertInTx(danMuList);
    }

    public void add(DanMu danMu){
        mDao.insert(danMu);
    }

    public List<DanMu> listAll(){
        return mDao.loadAll();
    }

    public List<DanMu> listOrderByTick(){
        return mDao.queryBuilder().orderAsc(DanMuDao.Properties.DanMuTick).list();
    }

    /**
     * @return
     */
    public Observable<List<DanMu>> observAll(){
        return Observable.create(new Observable.OnSubscribe<List<DanMu>>() {
            @Override
            public void call(Subscriber<? super List<DanMu>> subscriber) {
                subscriber.onNext(listAll());
                subscriber.onCompleted();
            }
        });
    }

    /**
     * @return
     */
    public Observable<List<DanMu>> observOrderByIndex(){
        return Observable.create(new Observable.OnSubscribe<List<DanMu>>() {
            @Override
            public void call(Subscriber<? super List<DanMu>> subscriber) {
                subscriber.onNext(listOrderByTick());
                subscriber.onCompleted();
            }
        });
    }

}
